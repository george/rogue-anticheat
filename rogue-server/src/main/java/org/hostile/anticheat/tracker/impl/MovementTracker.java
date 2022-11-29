package org.hostile.anticheat.tracker.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.check.type.impl.VelocityCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.event.VelocityEvent;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.location.Vector;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutAbilities;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutEntityVelocity;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutPosition;
import org.hostile.anticheat.tracker.Tracker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class MovementTracker extends Tracker {

    private Abilities abilities = new Abilities();

    private final Queue<Velocity> activeVelocities = new ConcurrentLinkedQueue<>();

    private final Queue<Abilities> pendingAbilities = new ConcurrentLinkedQueue<>();
    private final Queue<Velocity> pendingVelocities = new ConcurrentLinkedQueue<>();
    private final Queue<Vector> pendingTeleports = new ConcurrentLinkedQueue<>();

    private CustomLocation previousLocation = new CustomLocation(0, 0, 0, 0F, 0F, true);
    private CustomLocation currentLocation = new CustomLocation(0, 0, 0, 0F, 0F, true);

    private boolean smallMove;
    private boolean teleporting;

    private double walkSpeed = 0.2;
    private String gamemode = "SURVIVAL";

    public MovementTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying packet = (WrappedPacketPlayInFlying) event.getPacket();

            this.smallMove = false;
            this.teleporting = false;

            if (!packet.isMoving()) {
                this.smallMove = true;
                return;
            }

            this.gamemode = event.getJsonObject().get("gamemode").getAsString();
            this.walkSpeed = event.getJsonObject().get("walkSpeed").getAsDouble();

            double x = this.currentLocation.getX();
            double y = this.currentLocation.getY();
            double z = this.currentLocation.getZ();

            float yaw = this.currentLocation.getYaw();
            float pitch = this.currentLocation.getPitch();

            if (packet.isMoving()) {
                x = packet.getX();
                y = packet.getY();
                z = packet.getZ();
            }

            if (packet.isRotating()) {
                yaw = packet.getYaw();
                pitch = packet.getPitch();
            }

            CustomLocation location = new CustomLocation(x, y, z, yaw, pitch, packet.isOnGround());

            for(Vector teleport : this.pendingTeleports) {
                if (teleport.getX() == x && teleport.getY() == y && teleport.getZ() == z) {
                    this.pendingTeleports.remove(teleport);
                    this.teleporting = true;

                    break;
                }
            }

            if (!teleporting && (Math.abs(x - previousLocation.getX()) > 8) ||
                    (Math.abs(y - previousLocation.getY()) > 8) ||
                    (Math.abs(z - previousLocation.getZ()) > 8)) {
                if (previousLocation.getX() != 0 || previousLocation.getY() != 0 || previousLocation.getZ() != 0) {
                    //TODO: Handle large movements
                }
            } else if (!canFly()) {
                PositionUpdateEvent positionUpdateEvent = new PositionUpdateEvent(
                        location, this.currentLocation
                );

                data.getChecks().stream()
                        .filter(check -> check instanceof PositionUpdateCheck)
                        .forEach(check -> ((PositionUpdateCheck) check).handle(positionUpdateEvent));
            }

            this.activeVelocities.stream()
                    .filter(velocity -> velocity.getStartTick() == data.getTicksExisted())
                    .forEach(velocity -> {
                        VelocityEvent velocityEvent = new VelocityEvent(currentLocation, location, velocity.toVector());

                        data.getChecks().stream()
                                .filter(check -> check instanceof VelocityCheck)
                                .forEach(check -> ((VelocityCheck) check).handle(velocityEvent));
                    });

            this.activeVelocities.removeIf(velocity -> velocity.getCompletedTick() >= data.getTicksExisted());
            this.previousLocation = this.currentLocation;
            this.currentLocation = location;

        } else if (event.getPacket() instanceof WrappedPacketPlayOutPosition) {
            WrappedPacketPlayOutPosition packet = (WrappedPacketPlayOutPosition) event.getPacket();

            this.pendingTeleports.add(new Vector(
                    packet.getX(),
                    packet.getY(),
                    packet.getZ()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutEntityVelocity) {
            WrappedPacketPlayOutEntityVelocity packet = (WrappedPacketPlayOutEntityVelocity) event.getPacket();

            this.pendingVelocities.add(new Velocity(
                    packet.getVelocityX() / 8000D,
                    packet.getVelocityY() / 8000D,
                    packet.getVelocityZ() / 8000D,
                    this.data.getPingTracker().getLastTransaction()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutAbilities) {
            WrappedPacketPlayOutAbilities packet = (WrappedPacketPlayOutAbilities) event.getPacket();

            this.pendingAbilities.add(new Abilities(
                    packet.isCanFly(),
                    packet.isFlying(),
                    this.data.getPingTracker().getLastTransaction()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            this.pendingVelocities.removeIf((velocity) -> {
                if (transactionId == velocity.getTransactionId()) {
                    velocity.setStartTick(data.getTicksExisted() + 1);
                    velocity.setCompletedTick(data.getTicksExisted() + (int)
                            ((Math.hypot(velocity.getVelocityX(), velocity.getVelocityZ()) / 2 + 2) * 15)
                    );

                    this.activeVelocities.add(velocity);

                    return true;
                }

                return false;
            });

            this.pendingAbilities.removeIf((pendingAbilities) -> {
                if (transactionId == pendingAbilities.getTransactionId()) {
                    this.abilities = pendingAbilities;
                    return true;
                }

                return false;
            });
        }
    }

    public boolean canFly() {
        return (abilities.isCanFly() && abilities.isFlying()) || gamemode.equalsIgnoreCase("CREATIVE");
    }

    public double getVelocityXZ() {
        return activeVelocities.stream()
                .mapToDouble(velocity -> Math.hypot(velocity.getVelocityX(), velocity.getVelocityZ()))
                .sum();
    }

    public double getVelocityY() {
        return activeVelocities.stream()
                .mapToDouble(Velocity::getVelocityY)
                .sum();
    }

    @Getter @AllArgsConstructor
    private static class Abilities {

        private final boolean canFly;
        private final boolean isFlying;
        private final short transactionId;

        public Abilities() {
            this.canFly = false;
            this.isFlying = false;

            this.transactionId = 0;
        }
    }

    @Getter
    public static class Velocity {

        private final double velocityX;
        private final double velocityY;
        private final double velocityZ;
        private final short transactionId;

        @Setter private int completedTick;
        @Setter private int startTick;

        public Velocity(double velocityX, double velocityY, double velocityZ, short transactionId) {
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
            this.transactionId = transactionId;
        }

        public Vector toVector() {
            return new Vector(velocityX, velocityY, velocityZ);
        }
    }
}
