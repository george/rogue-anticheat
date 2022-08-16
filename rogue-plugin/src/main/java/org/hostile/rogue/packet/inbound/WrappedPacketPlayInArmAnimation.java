package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInArmAnimation extends WrappedPacket {

    private final long timestamp;

    public WrappedPacketPlayInArmAnimation(PacketContainer packetContainer) {
        this.timestamp = packetContainer.getLongs().read(0);
    }
}
