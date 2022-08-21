package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInTabComplete extends WrappedPacket {

    private final String message;
    private final BlockPosition targetPosition;

    public WrappedPacketPlayInTabComplete(PacketContainer packetContainer) {
        this.message = packetContainer.getStrings().read(0);
        this.targetPosition = packetContainer.getBlockPositionModifier().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("message", message)
                .addProperty("targetPosition", new JsonChain()
                        .addProperty("x", targetPosition.getX())
                        .addProperty("y", targetPosition.getY())
                        .addProperty("z", targetPosition.getZ())
                        .getJsonObject()
                );
    }

    @Override
    public String getName() {
        return "in_tab_complete";
    }
}