package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.location.BlockPosition;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInBlockPlace extends Packet {

    private final BlockPosition blockPosition;

    private final int face;

    private final String itemStack;

    private final float facingX;
    private final float facingY;
    private final float facingZ;

    public WrappedPacketPlayInBlockPlace(JsonObject object) {
        this.blockPosition = new BlockPosition(object.get("blockPosition").getAsJsonObject());

        this.face = object.get("face").getAsInt();
        this.itemStack = object.get("itemStack").getAsString();

        this.facingX = object.get("facingX").getAsFloat();
        this.facingY = object.get("facingY").getAsFloat();
        this.facingZ = object.get("facingZ").getAsFloat();
    }

}
