package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayOutPosition extends WrappedPacket {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public WrappedPacketPlayOutPosition(PacketContainer packetContainer) {
        StructureModifier<Double> doubles = packetContainer.getDoubles();
        StructureModifier<Float> floats = packetContainer.getFloat();

        this.x = doubles.read(0);
        this.y = doubles.read(1);
        this.z = doubles.read(2);
        this.yaw = floats.read(0);
        this.pitch = floats.read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("x", x)
                .addProperty("y", y)
                .addProperty("z", z)
                .addProperty("yaw", yaw)
                .addProperty("pitch", pitch);
    }
}
