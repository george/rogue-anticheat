package org.hostile.anticheat.event;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;
import org.hostile.anticheat.packet.PacketWrapper;

@Getter
public class PacketEvent {

    private final long timestamp;

    private final JsonObject jsonObject;

    private final Packet packet;

    public PacketEvent(JsonObject jsonObject) {
        this.timestamp = jsonObject.get("timestamp").getAsLong();
        this.jsonObject = jsonObject;
        this.packet = PacketWrapper.wrapPacket(jsonObject.get("type").getAsString(),
                jsonObject.get("packet").getAsJsonObject()
        );
    }
}
