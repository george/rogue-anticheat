package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WrappedPacketPlayOutDestroyEntities extends Packet {

    private final List<Integer> entities = new ArrayList<>();

    public WrappedPacketPlayOutDestroyEntities(JsonObject object) {
        object.get("entities").getAsJsonArray().forEach(element -> {
            entities.add(element.getAsJsonObject().get("entityId").getAsInt());
        });
    }
}
