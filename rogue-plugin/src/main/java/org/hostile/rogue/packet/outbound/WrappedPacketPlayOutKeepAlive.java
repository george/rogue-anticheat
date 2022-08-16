package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayOutKeepAlive extends WrappedPacket {

    private final int key;

    public WrappedPacketPlayOutKeepAlive(PacketContainer packetContainer) {
        this.key = packetContainer.getIntegers().read(0);
    }
}
