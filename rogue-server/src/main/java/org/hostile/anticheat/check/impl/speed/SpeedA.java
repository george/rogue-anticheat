package org.hostile.anticheat.check.impl.speed;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.util.Collisions;
import org.hostile.anticheat.util.PotionEffectType;

@CheckMetadata(type = "Speed", name = "A")
public class SpeedA extends PositionUpdateCheck {

    private static final double WATER_MOVEMENT_MODIFIER = 0.800000011920929D;
    private static final double LAVA_MOVEMENT_MODIFIER = 0.5D;

    private static final double JUMP_BOOST = 0.2D;
    private static final double SPEED_THRESHOLD = 1D;
    private static final double AIR_MOVE_SPEED = 0.026D;
    private static final double SPRINTING_MODIFIER = 1.3D;
    private static final double LAND_MOVEMENT_FACTOR = .16277136F;

    private static final float AIR_FRICTION = 0.91F;

    private double lastOffsetXZ;
    private float lastFriction = 0.91F;

    public SpeedA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        double offsetXZ = event.getOffsetXZ();
        double offsetY = event.getOffsetY();

        Collisions previousCollisions = collisionTracker.getPreviousCollisions();
        Collisions collisions = collisionTracker.getCollisions();

        double movementSpeed = movementTracker.getWalkSpeed() / 2;
        double jumpHeight = 0.42F + (potionTracker.getPotionLevel(PotionEffectType.JUMP_BOOST) * 0.1);

        float friction = this.lastFriction;

        if (previousCollisions.isOnGround()) {
            movementSpeed *= SPRINTING_MODIFIER;

            movementSpeed *= LAND_MOVEMENT_FACTOR / Math.pow(friction, 3);

            movementSpeed += (0.2 * potionTracker.getPotionLevel(PotionEffectType.SPEED));
            movementSpeed -= (0.15 * potionTracker.getPotionLevel(PotionEffectType.SLOWNESS));
        } else {
            movementSpeed = AIR_MOVE_SPEED;
            friction = AIR_FRICTION;
        }

        if (collisions.isCollidedVertically() || (offsetY > 0.2 && offsetY <= jumpHeight)) {
            movementSpeed += JUMP_BOOST;
        }

        movementSpeed += movementTracker.getVelocityXZ();

        if (collisions.isWater()) {
            movementSpeed *= WATER_MOVEMENT_MODIFIER;
        } else if (collisions.isLava()) {
            movementSpeed *= LAVA_MOVEMENT_MODIFIER;
        }

        if (actionTracker.isAttacking()) {
            movementSpeed *= 0.6D;
        }

        double ratio = (offsetXZ - this.lastOffsetXZ) / movementSpeed;
        debug("ratio", ratio);

        if (ratio > SPEED_THRESHOLD && !movementTracker.isTeleporting()) {
            if (offsetXZ > 0.2 && incrementBuffer(1) > 3) {
                fail("ratio", ratio, "offsetXZ", offsetXZ);
            }
        } else {
            decrementBuffer(0.05);
        }

        this.lastOffsetXZ = offsetXZ * friction;
        this.lastFriction = collisions.getFrictionFactor();
    }
}
