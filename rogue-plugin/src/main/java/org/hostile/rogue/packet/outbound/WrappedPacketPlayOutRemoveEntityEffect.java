package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayOutRemoveEntityEffect extends WrappedPacket {

    private final int entityId;
    private final byte effectId;

    public WrappedPacketPlayOutRemoveEntityEffect(PacketContainer packetContainer) {
        this.entityId = packetContainer.getIntegers().read(0);
        this.effectId = packetContainer.getBytes().read(0);
    }
}
