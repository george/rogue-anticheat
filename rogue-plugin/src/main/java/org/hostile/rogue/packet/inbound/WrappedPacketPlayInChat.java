package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInChat extends WrappedPacket {

    private final String message;

    public WrappedPacketPlayInChat(PacketContainer packetContainer) {
        this.message = packetContainer.getStrings().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("message", message);
    }

    @Override
    public String getName() {
        return "in_chat";
    }
}
