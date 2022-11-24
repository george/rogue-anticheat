package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInEntityAction extends Packet {

    private final int entityId;
    private final PlayerAction action;
    private final int auxId;

    public WrappedPacketPlayInEntityAction(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.action = PlayerAction.of(object.get("playerAction").getAsString());
        this.auxId = object.get("auxId").getAsInt();
    }

    @Getter @AllArgsConstructor
    public enum PlayerAction {

        START_SNEAKING("START_SNEAKING"),
        STOP_SNEAKING("STOP_SNEAKING"),
        STOP_SLEEPING("STOP_SLEEPING"),
        START_SPRINTING("START_SPRINTING"),
        STOP_SPRINTING("STOP_SPRINTING"),
        START_RIDING_JUMP("START_RIDING_JUMP"),
        STOP_RIDING_JUMP("STOP_RIDING_JUMP"),
        OPEN_INVENTORY("OPEN_INVENTORY"),
        START_FALL_FLYING("START_FALL_FLYING");

        private final String name;

        public static PlayerAction of(String input) {
            return Arrays.stream(values())
                    .filter(action -> action.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }

}
