package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInSetCreativeSlot extends WrappedPacket {

    private final int slot;
    private final ItemStack itemStack;

    public WrappedPacketPlayInSetCreativeSlot(PacketContainer packetContainer) {
        this.slot = packetContainer.getIntegers().read(0);
        this.itemStack = packetContainer.getItemModifier().read(0);
    }

}
