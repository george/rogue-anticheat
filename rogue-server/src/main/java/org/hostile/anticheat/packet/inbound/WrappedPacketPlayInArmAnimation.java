package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInArmAnimation extends Packet {

    private final long timestamp;

    public WrappedPacketPlayInArmAnimation(JsonObject data) {
        this.timestamp = data.get("timestamp").getAsLong();
    }
}