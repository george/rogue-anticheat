package org.hostile.anticheat.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerData {

    private final List<JsonObject> violations = new ArrayList<>();

    private final UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public void handle(PacketEvent event) {

    }

    public JsonArray getViolations() {
        JsonArray array = new JsonArray();

        violations.forEach(array::add);
        violations.clear();

        return array;
    }
}