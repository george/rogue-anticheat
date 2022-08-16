package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInResourcePackStatus extends WrappedPacket {

    private final EnumWrappers.ResourcePackStatus resourcePackStatus;

    public WrappedPacketPlayInResourcePackStatus(PacketContainer packetContainer) {
        this.resourcePackStatus = packetContainer.getResourcePackStatus().read(0);
    }
}
