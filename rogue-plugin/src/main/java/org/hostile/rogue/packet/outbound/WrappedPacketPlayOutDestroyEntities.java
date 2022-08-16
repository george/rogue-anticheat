package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayOutDestroyEntities extends WrappedPacket {

    private final int[] entities;

    public WrappedPacketPlayOutDestroyEntities(PacketContainer packetContainer) {
        this.entities = packetContainer.getIntegerArrays().read(0);
    }
}
