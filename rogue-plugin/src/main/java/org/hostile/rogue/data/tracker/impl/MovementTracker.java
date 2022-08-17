package org.hostile.rogue.data.tracker.impl;

import lombok.Getter;
import org.hostile.rogue.data.PlayerData;
import org.hostile.rogue.data.tracker.Tracker;
import org.hostile.rogue.location.CustomLocation;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.packet.inbound.WrappedPacketPlayInEntityAction;
import org.hostile.rogue.packet.inbound.WrappedPacketPlayInFlying;

@Getter
public class MovementTracker extends Tracker {

    private CustomLocation currentLocation = new CustomLocation(0, 0, 0, 0, 0, false);
    private CustomLocation previousLocation = new CustomLocation(0, 0, 0, 0, 0, false);

    private boolean sneaking;

    public MovementTracker(PlayerData data) {
        super(data);

    }

    @Override
    public void handlePacket(WrappedPacket paramPacket) {
        if (paramPacket instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying packet = (WrappedPacketPlayInFlying) paramPacket;

            boolean moving = packet.isMoving();
            boolean rotating = packet.isRotating();
            boolean ground = packet.isOnGround();

            double posX;
            double posY;
            double posZ;

            float yaw;
            float pitch;

            if (moving) {
                posX = packet.getX();
                posY = packet.getY();
                posZ = packet.getZ();
            } else {
                posX = currentLocation.getX();
                posY = currentLocation.getY();
                posZ = currentLocation.getZ();
            }

            if (rotating) {
                yaw = packet.getYaw();
                pitch = packet.getPitch();
            } else {
                yaw = currentLocation.getYaw();
                pitch = currentLocation.getPitch();
            }

            CustomLocation location = new CustomLocation(posX, posY, posZ, yaw, pitch, ground);


            this.previousLocation = currentLocation;
            this.currentLocation = location;
        } else if (paramPacket instanceof WrappedPacketPlayInEntityAction) {
            WrappedPacketPlayInEntityAction packet = (WrappedPacketPlayInEntityAction) paramPacket;

            switch (packet.getAction()) {
                case START_SNEAKING:
                    sneaking = true;
                    break;
                case STOP_SNEAKING:
                    sneaking = false;
                    break;
            }
        }
    }

    public boolean isSneaking() {
        return sneaking;
    }

}
