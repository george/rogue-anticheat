package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayOutKeepAlive extends WrappedPacket {

    private final int key;

    public WrappedPacketPlayOutKeepAlive(PacketContainer packetContainer) {
        this.key = packetContainer.getIntegers().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("key", key);
    }
}
