package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInEntityAction extends WrappedPacket {

    private final int entityId;
    private final EnumWrappers.PlayerAction action;
    private final int auxId;

    public WrappedPacketPlayInEntityAction(PacketContainer packetContainer) {
        StructureModifier<Integer> integers = packetContainer.getIntegers();

        this.entityId = integers.read(0);
        this.action = packetContainer.getPlayerActions().read(0);
        this.auxId = integers.read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("entityId", entityId)
                .addProperty("playerAction", action.name())
                .addProperty("auxId", auxId);
    }

    @Override
    public String getName() {
        return "in_entity_action";
    }
}
