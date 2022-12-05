package org.hostile.anticheat.tracker.impl.movement;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.check.type.impl.VelocityCheck;
import org.hostile.anticheat.check.type.impl.rotation.RotationCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.event.RotationEvent;
import org.hostile.anticheat.event.VelocityEvent;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.location.Vector;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutAbilities;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutEntityVelocity;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutPosition;
import org.hostile.anticheat.tracker.Tracker;
import org.hostile.anticheat.util.EvictingList;
import org.hostile.anticheat.util.MathUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class MovementTracker extends Tracker {

    private Abilities abilities = new Abilities();

    private final Queue<Double> sensitivitySamplesYaw = new EvictingList<>(50);
    private final Queue<Double> sensitivitySamplesPitch = new EvictingList<>(50);

    private final Queue<Vector> pendingFirstTickVelocities = new ConcurrentLinkedQueue<>();

    private final Queue<Velocity> activeVelocities = new ConcurrentLinkedQueue<>();
    private final Queue<Abilities> pendingAbilities = new ConcurrentLinkedQueue<>();
    private final Queue<Velocity> pendingVelocities = new ConcurrentLinkedQueue<>();

    private final Queue<Vector> pendingTeleports = new ConcurrentLinkedQueue<>();

    private CustomLocation previousLocation = new CustomLocation(0, 0, 0, 0F, 0F, true);
    private CustomLocation currentLocation = new CustomLocation(0, 0, 0, 0F, 0F, true);

    private boolean smallMove;
    private boolean teleporting;
    private boolean inVehicle;

    private double walkSpeed = 0.2;

    private String gamemode = "SURVIVAL";

    public MovementTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying packet = (WrappedPacketPlayInFlying) event.getPacket();

            smallMove = false;
            teleporting = false;

            JsonObject jsonObject = event.getJsonObject();

            gamemode = jsonObject.get("gamemode").getAsString();
            walkSpeed = jsonObject.get("walkSpeed").getAsDouble();
            inVehicle = jsonObject.get("inVehicle").getAsBoolean();

            double x = currentLocation.getX();
            double y = currentLocation.getY();
            double z = currentLocation.getZ();

            float yaw = currentLocation.getYaw();
            float pitch = currentLocation.getPitch();

            if (packet.isMoving()) {
                x = packet.getX();
                y = packet.getY();
                z = packet.getZ();
            } else {
                smallMove = true;
            }

            if (packet.isRotating()) {
                yaw = packet.getYaw();
                pitch = packet.getPitch();

                float deltaYaw = Math.abs(yaw - currentLocation.getYaw());
                float deltaPitch = Math.abs(pitch - currentLocation.getPitch());

                float lastDeltaYaw = Math.abs(currentLocation.getYaw() - previousLocation.getYaw());
                float lastDeltaPitch = Math.abs(currentLocation.getPitch() - previousLocation.getPitch());

                float accelerationYaw = Math.abs(deltaYaw - lastDeltaYaw);
                float accelerationPitch = Math.abs(deltaPitch - lastDeltaPitch);

                double gcd = MathUtil.gcd(deltaPitch, lastDeltaPitch);

                float gcdX = (float) MathUtil.gcd(deltaPitch, lastDeltaPitch);
                float gcdY = (float) MathUtil.gcd(deltaYaw, lastDeltaYaw);

                double sensitivityModX = Math.cbrt(0.8333 * gcdX);
                double sensTwoX = (1.666 * sensitivityModX) - 0.3333;

                double sensitivityModY = Math.cbrt(0.8333 * gcdY);
                double sensTwoY = (1.666 * sensitivityModY) - 0.3333;

                sensitivitySamplesPitch.add(sensTwoX * 200);
                sensitivitySamplesYaw.add(sensTwoY * 200);

                double sensitivityX = MathUtil.getMode(sensitivitySamplesPitch);
                double sensitivityY = MathUtil.getMode(sensitivitySamplesYaw);

                RotationEvent rotationEvent = new RotationEvent(
                        yaw, pitch, deltaYaw, deltaPitch, lastDeltaYaw, lastDeltaPitch, accelerationYaw,
                        accelerationPitch, gcd, sensitivityX, sensitivityY
                );

                data.getChecks(check -> check instanceof RotationCheck)
                        .forEach(check -> ((RotationCheck) check).handle(rotationEvent));
            }

            CustomLocation location = new CustomLocation(x, y, z, yaw, pitch, packet.isOnGround());

            previousLocation = currentLocation;
            currentLocation = location;

            for(Vector teleport : pendingTeleports) {
                if (teleport.getX() == x && teleport.getY() == y && teleport.getZ() == z) {
                    pendingTeleports.remove(teleport);
                    teleporting = true;

                    break;
                }
            }

            if (!teleporting && (Math.abs(x - previousLocation.getX()) > 8) ||
                    (Math.abs(y - previousLocation.getY()) > 8) ||
                    (Math.abs(z - previousLocation.getZ()) > 8)) {
                if (previousLocation.getX() != 0 || previousLocation.getY() != 0 || previousLocation.getZ() != 0) {
                    //TODO: Handle large movements
                }
            } else if (!canFly() && !inVehicle && packet.isMoving()) {
                PositionUpdateEvent positionUpdateEvent = new PositionUpdateEvent(location, previousLocation);

                data.getChecks(check -> check instanceof PositionUpdateCheck)
                        .forEach(check -> ((PositionUpdateCheck) check).handle(positionUpdateEvent));
            }

            pendingFirstTickVelocities.removeIf(velocity -> {
                VelocityEvent velocityEvent = new VelocityEvent(location, previousLocation, velocity);

                data.getChecks(check -> check instanceof VelocityCheck)
                        .forEach(check -> ((VelocityCheck) check).handle(velocityEvent));
                return true;
            });

            activeVelocities.removeIf(velocity -> velocity.getCompletedTick() >= data.getTicksExisted());
        } else if (event.getPacket() instanceof WrappedPacketPlayOutPosition) {
            WrappedPacketPlayOutPosition packet = (WrappedPacketPlayOutPosition) event.getPacket();

            pendingTeleports.add(new Vector(
                    packet.getX(),
                    packet.getY(),
                    packet.getZ()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutEntityVelocity) {
            WrappedPacketPlayOutEntityVelocity packet = (WrappedPacketPlayOutEntityVelocity) event.getPacket();

            if (packet.getEntityId() != data.getEntityId()) {
                return;
            }
    
            pendingVelocities.add(new Velocity(
                    packet.getVelocityX() / 8000D,
                    packet.getVelocityY() / 8000D,
                    packet.getVelocityZ() / 8000D,
                    data.getPingTracker().getLastTransaction()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutAbilities) {
            WrappedPacketPlayOutAbilities packet = (WrappedPacketPlayOutAbilities) event.getPacket();

            pendingAbilities.add(new Abilities(
                    packet.isCanFly(),
                    packet.isFlying(),
                    data.getPingTracker().getLastTransaction()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            pendingVelocities.removeIf((velocity) -> {
                if (transactionId == velocity.getTransactionId()) {
                    velocity.setCompletedTick(data.getTicksExisted() + (int)
                            ((Math.hypot(velocity.getVelocityX(), velocity.getVelocityZ()) / 2 + 2) * 15)
                    );

                    activeVelocities.add(velocity);
                    pendingFirstTickVelocities.add(
                            new Vector(velocity.getVelocityX(), velocity.getVelocityY(), velocity.getVelocityZ())
                    );

                    return true;
                }

                return false;
            });

            pendingAbilities.removeIf((pendingAbilities) -> {
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
