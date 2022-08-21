package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInWindowClick extends WrappedPacket {

    private final int windowId;
    private final int slotId;
    private final int usedButton;
    private final short actionNumber;
    private final ItemStack clickedItem;
    private final int mode;

    public WrappedPacketPlayInWindowClick(PacketContainer packetContainer) {
        StructureModifier<Integer> integers = packetContainer.getIntegers();

        this.windowId = integers.read(0);
        this.slotId = integers.read(1);
        this.usedButton = integers.read(2);
        this.actionNumber = packetContainer.getShorts().read(0);
        this.clickedItem = packetContainer.getItemModifier().read(0);
        this.mode = integers.read(3);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("windowId", windowId)
                .addProperty("slotId", slotId)
                .addProperty("usedButton", usedButton)
                .addProperty("actionNumber", actionNumber)
                .addProperty("clickedItem", Material.getMaterial(clickedItem.getTypeId()).name())
                .addProperty("mode", mode);
    }

    @Override
    public String getName() {
        return "in_window_click";
    }
}
