package org.hostile.anticheat.check.impl.reach;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInUseEntity;
import org.hostile.anticheat.util.MathUtil;
import org.hostile.anticheat.util.entity.TrackedEntity;
import org.hostile.anticheat.util.entity.TrackedPosition;
import org.hostile.anticheat.util.minecraft.AxisAlignedBB;
import org.hostile.anticheat.util.minecraft.MovingObjectPosition;
import org.hostile.anticheat.util.minecraft.Vec3;

@CheckMetadata(type = "Reach", name = "A")
public class ReachA extends PacketCheck {

    private int attackTicks = 1;
    private int lastAttackedEntity;

    public ReachA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity packet = (WrappedPacketPlayInUseEntity) event.getPacket();

            this.lastAttackedEntity = packet.getEntityId();
            this.attackTicks = 0;
        } else if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            if (++attackTicks != 1) {
                return;
            }
            CustomLocation location = movementTracker.getCurrentLocation();
            AxisAlignedBB playerBb = new AxisAlignedBB(location);

            Vec3 vec3 = playerBb.getEyePosition().toVec3();
            Vec3 vec31 = MathUtil.getVectorForRotation(location.getPitch(), location.getYaw());
            Vec3 vec32 = vec3.addVector(vec31.xCoord * 6.0D, vec31.yCoord * 6.0D, vec31.zCoord * 6.0D);
            Vec3 vec33 = null;

            TrackedEntity entity = entityTracker.getTrackedEntities().get(this.lastAttackedEntity);
            double reach = Double.MAX_VALUE;

            for (TrackedPosition position : entity.getPositions(pingTracker.getLastPing())) {
                AxisAlignedBB entityBb = new AxisAlignedBB(position.getLocation()).expand(0.01F, 0.01F, 0.01F);
                MovingObjectPosition movingObjectPosition = entityBb.calculateIntercept(vec3, vec32);

                if (entityBb.isVecInside(vec3)) {
                    reach = 0;
                } else if (movingObjectPosition != null) {
                    vec33 = movingObjectPosition.hitVec;

                    if (vec33 != null) {
                        reach = Math.min(reach, vec3.distanceTo(movingObjectPosition.hitVec));
                    }
                }
            }

            if (reach > 3.001) {
                if (incrementBuffer(1) > 3) {
                    fail("reach", reach);
                }
            } else {
                decrementBuffer(0.2);
            }
        }
    }
}
