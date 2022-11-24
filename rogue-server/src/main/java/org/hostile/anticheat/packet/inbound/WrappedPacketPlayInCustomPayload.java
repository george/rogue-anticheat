package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInCustomPayload extends Packet {

    private final String payload;

    public WrappedPacketPlayInCustomPayload(JsonObject object) {
        this.payload = object.get("payload").getAsString();
    }
}
