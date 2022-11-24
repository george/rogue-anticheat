package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutKeepAlive extends Packet {

    private final int key;

    public WrappedPacketPlayOutKeepAlive(JsonObject object) {
        this.key = object.get("key").getAsInt();
    }
}
