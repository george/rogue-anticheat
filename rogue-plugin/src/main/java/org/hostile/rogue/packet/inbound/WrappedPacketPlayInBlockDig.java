package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInBlockDig extends WrappedPacket {

    private final BlockPosition blockPosition;
    private final EnumWrappers.PlayerDigType playerDigType;

    public WrappedPacketPlayInBlockDig(PacketContainer packetContainer) {
        this.blockPosition = packetContainer.getBlockPositionModifier().read(0);
        this.playerDigType = packetContainer.getPlayerDigTypes().read(0);
    }
}
