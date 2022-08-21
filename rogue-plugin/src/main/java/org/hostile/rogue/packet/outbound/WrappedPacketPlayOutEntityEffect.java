package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayOutEntityEffect extends WrappedPacket {

    private final int entityId;
    private final byte effectId;
    private final byte amplifier;
    private final int duration;

    public WrappedPacketPlayOutEntityEffect(PacketContainer packetContainer) {
        StructureModifier<Integer> integers = packetContainer.getIntegers();
        StructureModifier<Byte> bytes = packetContainer.getBytes();

        this.entityId = integers.read(0);
        this.effectId = bytes.read(0);
        this.amplifier = bytes.read(1);
        this.duration = integers.read(1);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("entityId", entityId)
                .addProperty("effectId", effectId)
                .addProperty("amplifier", amplifier)
                .addProperty("duration", duration);
    }
}
