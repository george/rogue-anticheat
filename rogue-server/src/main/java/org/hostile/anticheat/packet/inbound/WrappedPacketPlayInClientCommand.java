package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInClientCommand extends Packet {

    private final ClientCommandType clientCommand;

    public WrappedPacketPlayInClientCommand(JsonObject object) {
        this.clientCommand = ClientCommandType.of(object.get("clientCommand").getAsString());
    }

    @Getter @AllArgsConstructor
    public enum ClientCommandType {

        PERFORM_RESPAWN("PERFORM_RESPAWN"),
        REQUEST_STATS("REQUEST_STATS"),
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT");

        private final String name;

        public static ClientCommandType of(String input) {
            return Arrays.stream(values())
                    .filter(type -> type.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }
}
