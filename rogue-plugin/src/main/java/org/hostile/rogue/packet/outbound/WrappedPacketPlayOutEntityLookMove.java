package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayOutEntityLookMove extends WrappedPacketPlayOutEntity {

    public WrappedPacketPlayOutEntityLookMove(PacketContainer packetContainer) {
        super(packetContainer);
    }

    @Override
    public String getName() {
        return "out_entity_look_move";
    }
}