package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInSteerVehicle extends WrappedPacket {

    private final float strafeSpeed;
    private final float forwardSpeed;
    private final boolean jumping;
    private final boolean sneaking;

    public WrappedPacketPlayInSteerVehicle(PacketContainer packetContainer) {
        StructureModifier<Float> floats = packetContainer.getFloat();
        StructureModifier<Boolean> booleans = packetContainer.getBooleans();

        this.strafeSpeed = floats.read(0);
        this.forwardSpeed = floats.read(1);
        this.jumping = booleans.read(0);
        this.sneaking = booleans.read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("strafeSpeed", strafeSpeed)
                .addProperty("forwardsSpeed", forwardSpeed)
                .addProperty("jumping", jumping)
                .addProperty("sneaking", sneaking);
    }

    @Override
    public String getName() {
        return "in_steer_vehicle";
    }
}
