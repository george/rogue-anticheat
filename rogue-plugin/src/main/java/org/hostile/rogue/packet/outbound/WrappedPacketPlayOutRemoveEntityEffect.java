package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayOutRemoveEntityEffect extends WrappedPacket {

    private final int entityId;
    private final int effectId;

    public WrappedPacketPlayOutRemoveEntityEffect(PacketContainer packetContainer) {
        this.entityId = packetContainer.getIntegers().read(0);
        this.effectId = packetContainer.getIntegers().read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("entityId", entityId)
                .addProperty("effectId", effectId);
    }

    @Override
    public String getName() {
        return "out_remove_entity_effect";
    }
}
