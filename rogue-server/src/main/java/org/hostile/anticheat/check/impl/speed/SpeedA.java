package org.hostile.anticheat.check.impl.speed;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.util.Collisions;
import org.hostile.anticheat.util.PotionEffectType;

@CheckMetadata(type = "Speed", name = "A")
public class SpeedA extends PositionUpdateCheck {

    private static final double AIR_FRICTION = 0.91F;
    private static final double AIR_MOVE_SPEED = 0.026D;

    private static final double SPRINTING_MODIFIER = 1.3D;
    private static final double LAND_MOVEMENT_FACTOR = .16277136F;

    private static final double WATER_MOVEMENT_MODIFIER = 0.800000011920929D;
    private static final double LAVA_MOVEMENT_MODIFIER = 0.5D;

    private static final double JUMP_BOOST = 0.2D;

    private static final double SPEED_THRESHOLD = 1D;

    private static final double SPEED_MODIFIER = 0.2D;
    private static final double SLOWNESS_MODIFIER = 0.15D;

    private static final double ATTACK_MODIFIER = 0.6D;

    private double lastOffsetXZ;
    private double lastFriction = 0.91F;

    public SpeedA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        double offsetXZ = event.getOffsetXZ();
        double offsetY = event.getOffsetY();

        double movementSpeed = movementTracker.getWalkSpeed() / 2D;

        if (collisionTracker.getPreviousCollisions().isOnGround()) {
            movementSpeed *= SPRINTING_MODIFIER;

            movementSpeed *= LAND_MOVEMENT_FACTOR / Math.pow(lastFriction, 3);

            if (offsetY > 0.01) { // this can be abused easily
                movementSpeed += JUMP_BOOST;
            }
        } else {
            movementSpeed = AIR_MOVE_SPEED;
            lastFriction = AIR_FRICTION;
        }

        movementSpeed += (SPEED_MODIFIER * potionTracker.getPotionLevel(PotionEffectType.SPEED));
        movementSpeed -= (SLOWNESS_MODIFIER * potionTracker.getPotionLevel(PotionEffectType.SLOWNESS));

        Collisions collisions = collisionTracker.getCollisions();

        if (collisions.isWater()) {
            movementSpeed *= WATER_MOVEMENT_MODIFIER;
        } else if (collisions.isLava()) {
            movementSpeed *= LAVA_MOVEMENT_MODIFIER;
        }

        if (actionTracker.isAttacking()) {
            movementSpeed *= ATTACK_MODIFIER;
        }

        movementSpeed += movementTracker.getVelocityXZ();

        double ratio = (offsetXZ - lastOffsetXZ) / movementSpeed;
        debug("ratio", ratio);

        if (ratio > SPEED_THRESHOLD) {
            if (incrementBuffer(1) > 3) {
                fail("ratio", ratio, "offsetY", offsetY);
            }
        } else {
            decrementBuffer(0.5);
        }

        lastOffsetXZ = offsetXZ * lastFriction;
        lastFriction = collisionTracker.getPreviousCollisions().getFrictionFactor() * AIR_FRICTION;
    }
}
