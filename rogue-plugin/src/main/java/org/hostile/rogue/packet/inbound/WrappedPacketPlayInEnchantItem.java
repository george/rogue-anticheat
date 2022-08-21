package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInEnchantItem extends WrappedPacket {

    private final int windowId;
    private final int button;

    public WrappedPacketPlayInEnchantItem(PacketContainer packetContainer) {
        StructureModifier<Integer> integers = packetContainer.getIntegers();

        this.windowId = integers.read(0);
        this.button = integers.read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("windowId", windowId)
                .addProperty("button", button);
    }

    @Override
    public String getName() {
        return "in_enchant_item";
    }
}
