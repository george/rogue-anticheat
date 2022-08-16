package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInTabComplete extends WrappedPacket {

    private final String message;
    private final BlockPosition targetPosition;

    public WrappedPacketPlayInTabComplete(PacketContainer packetContainer) {
        this.message = packetContainer.getStrings().read(0);
        this.targetPosition = packetContainer.getBlockPositionModifier().read(0);
    }
}