package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInArmAnimation extends WrappedPacket {

    private final long timestamp;

    public WrappedPacketPlayInArmAnimation(PacketContainer packetContainer) {
        this.timestamp = packetContainer.getLongs().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("timestamp", timestamp);
    }
}
