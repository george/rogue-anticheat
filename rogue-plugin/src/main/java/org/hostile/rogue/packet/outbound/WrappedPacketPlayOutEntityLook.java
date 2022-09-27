package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;

public class WrappedPacketPlayOutEntityLook extends WrappedPacketPlayOutEntity {

    public WrappedPacketPlayOutEntityLook(PacketContainer packetContainer) {
        super(packetContainer);
    }

    @Override
    public String getName() {
        return "out_entity_look";
    }
}
