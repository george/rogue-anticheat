package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayInPosition extends WrappedPacketPlayInFlying {

    public WrappedPacketPlayInPosition(PacketContainer packetContainer) {
        super(packetContainer);
    }
}
