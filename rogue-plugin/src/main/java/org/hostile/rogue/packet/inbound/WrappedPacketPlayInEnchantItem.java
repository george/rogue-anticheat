package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInEnchantItem extends WrappedPacket {

    private final int windowId;
    private final int button;

    public WrappedPacketPlayInEnchantItem(PacketContainer packetContainer) {
        StructureModifier<Integer> integers = packetContainer.getIntegers();

        this.windowId = integers.read(0);
        this.button = integers.read(1);
    }
}
