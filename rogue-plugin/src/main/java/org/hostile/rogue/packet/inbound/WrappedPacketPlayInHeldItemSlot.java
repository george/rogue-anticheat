package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInHeldItemSlot extends WrappedPacket {

    private final int slot;

    public WrappedPacketPlayInHeldItemSlot(PacketContainer packetContainer) {
        this.slot = packetContainer.getIntegers().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("slot", slot);
    }

    @Override
    public String getName() {
        return "in_held_item_slot";
    }
}
