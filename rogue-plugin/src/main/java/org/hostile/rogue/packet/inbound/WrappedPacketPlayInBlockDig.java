package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInBlockDig extends WrappedPacket {

    private final BlockPosition blockPosition;
    private final EnumWrappers.PlayerDigType playerDigType;

    public WrappedPacketPlayInBlockDig(PacketContainer packetContainer) {
        this.blockPosition = packetContainer.getBlockPositionModifier().read(0);
        this.playerDigType = packetContainer.getPlayerDigTypes().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("blockPosition", new JsonChain()
                        .addProperty("x", blockPosition.getX())
                        .addProperty("y", blockPosition.getY())
                        .addProperty("z", blockPosition.getZ())
                        .getJsonObject()
                ).addProperty("digType", playerDigType.name());
    }

    @Override
    public String getName() {
        return "in_dig";
    }
}
