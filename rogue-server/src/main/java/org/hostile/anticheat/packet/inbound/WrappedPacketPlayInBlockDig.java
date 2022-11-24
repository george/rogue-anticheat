package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.location.BlockPosition;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInBlockDig extends Packet {

    private final BlockPosition blockPosition;
    private final PlayerDigType playerDigType;

    public WrappedPacketPlayInBlockDig(JsonObject object) {
        this.blockPosition = new BlockPosition(object.get("blockPosition").getAsJsonObject());
        this.playerDigType = PlayerDigType.of(object.get("digType").getAsString());
    }
    
    @Getter @AllArgsConstructor
    public enum PlayerDigType {

        START_DESTROY_BLOCK("START_DESTROY_BLOCK"),
        ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK"),
        STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK"),
        DROP_ALL_ITEMS("DROP_ALL_ITEMS"),
        DROP_ITEM("DROP_ITEM"),
        RELEASE_USE_ITEM("RELEASE_USE_ITEM"),
        SWAP_HELD_ITEMS("SWAP_HELD_ITEMS");

        private final String name;

        public static PlayerDigType of(String input) {
            return Arrays.stream(values())
                    .filter(type -> type.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }
}
