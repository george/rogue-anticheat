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
        JsonChain jsonChain = new JsonChain()
                .addProperty("entityId", entityId)
                .addProperty("action", action.name());
        if (hitVec != null) {
            jsonChain.addProperty("vec", new JsonChain()
                    .addProperty("x", hitVec.getX())
                    .addProperty("y", hitVec.getY())
                    .addProperty("z", hitVec.getZ())
                    .getJsonObject()
            );
        } else {
            jsonChain.addProperty("vec", new JsonChain()
                    .addProperty("x", 0)
                    .addProperty("y", 0)
                    .addProperty("z", 0)
                    .getJsonObject()
            );
        }

        return jsonChain;
    }

    @Override
    public String getName() {
        return "in_use_entity";
    }
}
