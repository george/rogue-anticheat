package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInBlockPlace extends WrappedPacket {

    private final BlockPosition blockPosition;
    private final int face;
    private final ItemStack itemStack;
    private final float facingX;
    private final float facingY;
    private final float facingZ;

    public WrappedPacketPlayInBlockPlace(PacketContainer packetContainer) {
        this.blockPosition = packetContainer.getBlockPositionModifier().read(0);
        this.face = packetContainer.getIntegers().read(0);
        this.itemStack = packetContainer.getItemModifier().read(0);

        StructureModifier<Float> floats = packetContainer.getFloat();

        this.facingX = floats.read(0);
        this.facingY = floats.read(1);
        this.facingZ = floats.read(2);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("blockPosition", new JsonChain()
                        .addProperty("x", blockPosition.getX())
                        .addProperty("y", blockPosition.getY())
                        .addProperty("z", blockPosition.getZ())
                        .getJsonObject()
                )
                .addProperty("face", face)
                .addProperty("facingX", facingX)
                .addProperty("facingY", facingY)
                .addProperty("facingZ", facingZ)
                .addProperty("itemStack", Material.getMaterial(itemStack.getTypeId()).name());
    }
}
