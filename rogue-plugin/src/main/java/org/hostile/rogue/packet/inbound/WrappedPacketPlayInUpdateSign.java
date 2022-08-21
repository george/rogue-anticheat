package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.gson.JsonArray;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInUpdateSign extends WrappedPacket {

    private final BlockPosition blockPosition;
    private final WrappedChatComponent[] chatComponents;

    public WrappedPacketPlayInUpdateSign(PacketContainer packetContainer) {
        this.blockPosition = packetContainer.getBlockPositionModifier().read(0);
        this.chatComponents = packetContainer.getChatComponentArrays().read(0);
    }

    @Override
    public JsonChain serialize() {
        JsonArray components = new JsonArray();
        Arrays.stream(chatComponents).forEach(component -> components.add(
                new JsonChain().addProperty("message", component.getJson()).getJsonObject()
        ));

        return new JsonChain()
                .addProperty("blockPosition", new JsonChain()
                        .addProperty("x", blockPosition.getX())
                        .addProperty("y", blockPosition.getY())
                        .addProperty("z", blockPosition.getZ()).getJsonObject())
                .addProperty("components", components);
    }

    @Override
    public String getName() {
        return "in_update_sign";
    }
}
