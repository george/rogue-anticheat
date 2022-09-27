package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayInLook extends WrappedPacketPlayInFlying {

    public WrappedPacketPlayInLook(PacketContainer packetContainer) {
        super(packetContainer);
    }

    @Override
    public String getName() {
        return "in_look";
    }
}
