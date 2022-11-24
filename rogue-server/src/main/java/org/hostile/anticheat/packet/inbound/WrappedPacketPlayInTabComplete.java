package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.location.BlockPosition;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInTabComplete extends Packet {

    private final String message;
    private final BlockPosition targetPosition;

    public WrappedPacketPlayInTabComplete(JsonObject object) {
        this.message = object.get("message").getAsString();
        this.targetPosition = new BlockPosition(object.get("targetPosition").getAsJsonObject());
    }
}