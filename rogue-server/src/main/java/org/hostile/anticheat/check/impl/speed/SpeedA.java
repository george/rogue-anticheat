package org.hostile.anticheat.check.impl.speed;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.util.PotionEffectType;

@CheckMetadata(type = "Speed", name = "A")
public class SpeedA extends PositionUpdateCheck {

    private double lastOffsetXZ;
    private double lastFriction = 0.91F;

    public SpeedA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        double offsetXZ = event.getOffsetXZ();
        double offsetY = event.getOffsetY();

        double movementSpeed = movementTracker.getWalkSpeed();
        double jumpHeight = 0.42F + (potionTracker.getPotionLevel(PotionEffectType.JUMP_BOOST) * 0.1);

        if (collisionTracker.getPreviousCollisions().isOnGround()) {
            movementSpeed *= 1.3;

            this.lastFriction *= 0.91F;

            movementSpeed *= 0.16277136 / Math.pow(this.lastFriction, 3);

            if (collisionTracker.getPreviousCollisions().isUnderBlock() || 0.001 < offsetY && (offsetY <= jumpHeight)) {
                movementSpeed += 0.2;
            }
        } else {
            movementSpeed = 0.026;
            this.lastFriction = 0.91F;
        }

        movementSpeed += movementTracker.getVelocityXZ();
        movementSpeed += (0.2 * potionTracker.getPotionLevel(PotionEffectType.SPEED));
        movementSpeed -= (0.15 * potionTracker.getPotionLevel(PotionEffectType.JUMP_BOOST));

        double ratio = (offsetXZ - lastOffsetXZ) / movementSpeed;

        if (ratio > 1.0 && !movementTracker.isTeleporting()) {
            if (offsetXZ > 0.2 && incrementBuffer(1) > 3) {
                debug("ratio", ratio, "offsetXZ", offsetXZ);
            }
        } else {
            decrementBuffer(0.25);
        }

        this.lastOffsetXZ = offsetXZ * this.lastFriction;
        this.lastFriction *= data.getCollisionTracker().getPreviousCollisions().getFrictionFactor();
    }
}
