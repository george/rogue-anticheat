package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInKeepAlive extends Packet {

    private final int key;

    public WrappedPacketPlayInKeepAlive(JsonObject object) {
        this.key = object.get("key").getAsInt();
    }
}
