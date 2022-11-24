package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInChat extends Packet {

    private final String message;

    public WrappedPacketPlayInChat(JsonObject object) {
        this.message = object.get("message").getAsString();
    }

}
