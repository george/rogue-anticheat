package org.hostile.anticheat.check.impl.fly;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.util.Collisions;

@CheckMetadata(type = "Fly", name = "A")
public class FlyA extends PositionUpdateCheck {

    public FlyA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        Collisions collisions = collisionTracker.getCollisions();
        CustomLocation currentLocation = event.getCurrentLocation();

        boolean packetOnGround = currentLocation.isOnGround();

        boolean collisionOnGround = collisions.isOnGround();
        boolean mathematicallyOnGround = collisions.isMathematicallyOnGround();

        if (packetOnGround && !collisionOnGround) {
            if (incrementBuffer(1) > 8) {
                fail("packetOnGround", true, "collisionOnGround", false);
            }
        } else if (!packetOnGround && (collisionOnGround && mathematicallyOnGround)) {
            if (incrementBuffer(1) > 8) {
                fail("packetOnGround", false, "collisionOnGround", true, "mathematicallyOnGround", true);
            }
        } else {
            decrementBuffer(0.25);
        }
    }
}
