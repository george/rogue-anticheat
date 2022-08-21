package org.hostile.rogue.packet.outbound;

import com.comphenix.protocol.events.PacketContainer;
import com.google.gson.JsonArray;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayOutDestroyEntities extends WrappedPacket {

    private final int[] entities;

    public WrappedPacketPlayOutDestroyEntities(PacketContainer packetContainer) {
        this.entities = packetContainer.getIntegerArrays().read(0);
    }

    @Override
    public JsonChain serialize() {
        JsonArray entities = new JsonArray();
        Arrays.stream(this.entities)
                .forEach(entity -> entities.add(new JsonChain()
                        .addProperty("entityId", entity)
                        .getJsonObject()
                ));

        return new JsonChain()
                .addProperty("entities", entities);
    }
}
