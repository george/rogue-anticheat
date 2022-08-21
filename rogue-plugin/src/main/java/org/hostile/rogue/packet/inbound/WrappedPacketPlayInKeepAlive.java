package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInKeepAlive extends WrappedPacket {

    private final int key;

    public WrappedPacketPlayInKeepAlive(PacketContainer packetContainer) {
        this.key = packetContainer.getIntegers().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("key", key);
    }
}
