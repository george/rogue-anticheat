package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInCustomPayload extends WrappedPacket {

    private final String payload;

    public WrappedPacketPlayInCustomPayload(PacketContainer packetContainer) {
        this.payload = packetContainer.getStrings().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("payload", payload);
    }

    @Override
    public String getName() {
        return "in_custom_payload";
    }
}
