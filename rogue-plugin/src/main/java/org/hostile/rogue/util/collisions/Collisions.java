package org.hostile.rogue.util.collisions;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.hostile.rogue.util.json.JsonChain;

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

    public JsonObject serialize() {
        return new JsonChain()
                .addProperty("mathematicallyOnGround", isMathematicallyOnGround)
                .addProperty("climbing", isClimbing)
                .addProperty("cobweb", isInCobweb)
                .addProperty("lava", isInLava)
                .addProperty("water", isInWater)
                .addProperty("underBlock", isUnderBlock)
                .addProperty("collidedHorizontally", isCollidedHorizontally)
                .addProperty("collidedVertically", isCollidedVertically)
                .addProperty("frictionFactor", frictionFactor)
                .addProperty("onGround", onGround).getJsonObject();
    }
}
