package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayInPositionLook extends WrappedPacketPlayInFlying {

    public WrappedPacketPlayInPositionLook(PacketContainer packetContainer) {
        super(packetContainer);
    }

    @Override
    public String getName() {
        return "in_position_look";
    }
}