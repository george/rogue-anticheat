package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

public class WrappedPacketPlayOutHeldItemSlot extends WrappedPacket {

    private final int slot;

    public WrappedPacketPlayOutHeldItemSlot(PacketContainer packetContainer) {
        this.slot = packetContainer.getIntegers().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("slot", slot);
    }

    @Override
    public String getName() {
        return "out_held_item_slot";
    }
}
