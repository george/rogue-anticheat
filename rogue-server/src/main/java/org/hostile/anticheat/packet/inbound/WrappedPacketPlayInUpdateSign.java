package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.location.BlockPosition;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInUpdateSign extends Packet {

    private final BlockPosition blockPosition;

    public WrappedPacketPlayInUpdateSign(JsonObject object) {
        this.blockPosition = new BlockPosition(object.get("blockPosition").getAsJsonObject());
    }
}
