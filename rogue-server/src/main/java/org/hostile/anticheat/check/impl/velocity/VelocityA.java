package org.hostile.anticheat.check.impl.velocity;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.movement.VelocityCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.VelocityEvent;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.util.Collisions;

@CheckMetadata(type = "Velocity", name = "A")
public class VelocityA extends VelocityCheck {

    public VelocityA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(VelocityEvent event) {
        Collisions previousCollisions = collisionTracker.getPreviousCollisions();
        Collisions currentCollisions = collisionTracker.getCollisions();

        if (previousCollisions.isCobweb() || previousCollisions.isInLiquid() || previousCollisions.isUnderBlock()) {
            return;
        }

        if (currentCollisions.isCobweb() || currentCollisions.isInLiquid() || currentCollisions.isUnderBlock()) {
            return;
        }

        if (event.getVelocity().getY() < 0.1) {
            return;
        }

        CustomLocation previous = event.getPreviousLocation();
        CustomLocation current = event.getCurrentLocation();

        double velocityY = event.getVelocity().getY();
        double offsetY = Math.abs(previous.getY() - current.getY());

        double ratio = offsetY / velocityY;
        debug("ratio", ratio);

        if (ratio < 0.9995 && !movementTracker.isTeleporting()) {
            if (++buffer > 3) {
                fail("ratio", ratio);
            }
        } else {
            decrementBuffer(0.25);
        }
    }
}
