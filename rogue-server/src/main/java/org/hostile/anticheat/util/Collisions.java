package org.hostile.anticheat.util;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class Collisions {

    private final boolean climbing;
    private final boolean cobweb;

    private final boolean lava;
    private final boolean water;

    private final boolean underBlock;

    private final boolean collidedHorizontally;
    private final boolean collidedVertically;

    private final boolean mathematicallyOnGround;
    private final boolean onGround;

    private final float frictionFactor;

    public Collisions(JsonObject jsonObject) {
        this.climbing = jsonObject.get("climbing").getAsBoolean();
        this.cobweb = jsonObject.get("cobweb").getAsBoolean();

        this.lava = jsonObject.get("lava").getAsBoolean();
        this.water = jsonObject.get("water").getAsBoolean();

        this.underBlock = jsonObject.get("underBlock").getAsBoolean();

        this.collidedHorizontally = jsonObject.get("collidedHorizontally").getAsBoolean();
        this.collidedVertically = jsonObject.get("collidedVertically").getAsBoolean();

        this.mathematicallyOnGround = jsonObject.get("mathematicallyOnGround").getAsBoolean();
        this.onGround = jsonObject.get("onGround").getAsBoolean();

        this.frictionFactor = jsonObject.get("frictionFactor").getAsFloat();
    }

    public Collisions() {
        this.climbing = false;
        this.cobweb = false;

        this.lava = false;
        this.water = false;

        this.underBlock = false;

        this.collidedHorizontally = false;
        this.collidedVertically = false;

        this.mathematicallyOnGround = true;
        this.onGround = true;

        this.frictionFactor = 0.91F;
    }

    public boolean isInLiquid() {
        return this.lava || this.water;
    }
}
