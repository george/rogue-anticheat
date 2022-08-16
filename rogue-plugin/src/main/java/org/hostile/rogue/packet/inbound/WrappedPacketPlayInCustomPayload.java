package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInCustomPayload extends WrappedPacket {

    private final String payload;

    public WrappedPacketPlayInCustomPayload(PacketContainer packetContainer) {
        this.payload = packetContainer.getStrings().read(0);
    }
}
