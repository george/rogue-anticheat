package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInTransaction extends WrappedPacket {

    private final int windowId;
    private final short transactionId;
    private final boolean accepted;

    public WrappedPacketPlayInTransaction(PacketContainer packetContainer) {
        this.windowId = packetContainer.getIntegers().read(0);
        this.transactionId = packetContainer.getShorts().read(0);
        this.accepted = packetContainer.getBooleans().read(0);
    }
}
