package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInSetCreativeSlot extends WrappedPacket {

    private final int slot;
    private final ItemStack itemStack;

    public WrappedPacketPlayInSetCreativeSlot(PacketContainer packetContainer) {
        this.slot = packetContainer.getIntegers().read(0);
        this.itemStack = packetContainer.getItemModifier().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("slot", slot)
                .addProperty("material", Material.getMaterial(itemStack.getTypeId()).name());
    }

    @Override
    public String getName() {
        return "in_creative_slot";
    }
}
