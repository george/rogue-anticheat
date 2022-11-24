package org.hostile.anticheat.location;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class BlockPosition {

    private final double posX;
    private final double posY;
    private final double posZ;

    public BlockPosition(JsonObject object) {
        this.posX = object.get("x").getAsDouble();
        this.posY = object.get("y").getAsDouble();
        this.posZ = object.get("z").getAsDouble();
    }
}
