package org.hostile.anticheat.check.impl.fly;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.util.Collisions;

@CheckMetadata(type = "Fly", name = "B")
public class FlyB extends PositionUpdateCheck {

    protected static final double GRAVITY = 0.08;
    protected static final double AIR_FRICTION = .9800000190734863D;

    private double lastOffsetY;

    public FlyB(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        Collisions collisions = collisionTracker.getCollisions();
        Collisions previousCollisions = collisionTracker.getPreviousCollisions();

        double offsetY = event.getOffsetY();

        if (!collisions.isInLiquid() && !collisions.isClimbing() && !collisions.isCobweb() && movementTracker.getVelocityY() == 0) {
            boolean onGround = collisions.isOnGround() || previousCollisions.isOnGround();

            double predictedOffsetY = ((lastOffsetY - GRAVITY) * AIR_FRICTION);
            double offset = Math.abs(predictedOffsetY - offsetY);

            if (!onGround && !movementTracker.isTeleporting()) {
                if (offset > 0.05 && predictedOffsetY < 0) {
                    if (incrementBuffer(1) > 10) {
                        fail("offset", offset, "predictedOffsetY", predictedOffsetY);
                    }
                } else {
                    decrementBuffer(0.25);
                }
            } else {
                decrementBuffer(0.25);
            }
        }

        lastOffsetY = offsetY;
    }
}
