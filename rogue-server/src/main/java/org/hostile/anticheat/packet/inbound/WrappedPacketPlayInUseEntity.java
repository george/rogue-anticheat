package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.location.BlockPosition;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInUseEntity extends Packet {

    private final int entityId;
    private final EntityUseAction action;
    private final BlockPosition hitVec;

    public WrappedPacketPlayInUseEntity(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.action = EntityUseAction.of(object.get("action").getAsString());
        this.hitVec = new BlockPosition(object.get("vec").getAsJsonObject());
    }

    @Getter @AllArgsConstructor
    public enum EntityUseAction {

        INTERACT("INTERACT"),
        ATTACK("ATTACK"),
        INTERACT_AT("INTERACT_AT");

        private final String name;

        public static EntityUseAction of(String input) {
            return Arrays.stream(values())
                    .filter(action -> action.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }
}
