package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.bukkit.util.Vector;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInUseEntity extends WrappedPacket {

    private final int entityId;
    private final EnumWrappers.EntityUseAction action;
    private final Vector hitVec;

    public WrappedPacketPlayInUseEntity(PacketContainer packetContainer) {
        this.entityId = packetContainer.getIntegers().read(0);
        this.action = packetContainer.getEntityUseActions().read(0);
        this.hitVec = packetContainer.getVectors().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("entityId", entityId)
                .addProperty("action", action.name())
                .addProperty("vec", new JsonChain()
                        .addProperty("x", hitVec.getX())
                        .addProperty("y", hitVec.getY())
                        .addProperty("z", hitVec.getZ())
                        .getJsonObject()
                );
    }

    @Override
    public String getName() {
        return "in_use_entity";
    }
}
