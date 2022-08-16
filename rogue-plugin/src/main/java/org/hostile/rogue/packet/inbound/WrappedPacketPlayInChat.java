package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInChat extends WrappedPacket {

    private final String length;

    public WrappedPacketPlayInChat(PacketContainer packetContainer) {
        this.length = packetContainer.getStrings().read(0);
    }
}
