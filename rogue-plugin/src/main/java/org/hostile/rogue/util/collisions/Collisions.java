package org.hostile.rogue.util.collisions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Collisions {

    private boolean isMathematicallyOnGround;
    private boolean isClimbing;
    private boolean isInCobweb;
    private boolean isInLava;
    private boolean isInWater;
    private boolean isUnderBlock;
    private boolean isCollidedHorizontally;
    private boolean isCollidedVertically;
    private float frictionFactor;
    private boolean onGround;

    public boolean isInLiquid() {
        return isInLava || isInWater;
    }

    public boolean isStrictlyOnGround() {
        return isMathematicallyOnGround && onGround;
    }
}
