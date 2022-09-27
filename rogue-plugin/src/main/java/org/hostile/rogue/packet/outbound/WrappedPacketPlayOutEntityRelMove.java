package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayOutEntityRelMove extends WrappedPacketPlayOutEntity {

    public WrappedPacketPlayOutEntityRelMove(PacketContainer packetContainer) {
        super(packetContainer);
    }

    @Override
    public String getName() {
        return "out_entity_rel_move";
    }
}
