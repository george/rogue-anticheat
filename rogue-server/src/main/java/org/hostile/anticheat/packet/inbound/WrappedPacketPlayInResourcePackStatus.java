package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInResourcePackStatus extends Packet {

    private final ResourcePackStatus resourcePackStatus;

    public WrappedPacketPlayInResourcePackStatus(JsonObject object) {
        this.resourcePackStatus = ResourcePackStatus.of(object.get("resourcePackStatus").getAsString());
    }

    @Getter @AllArgsConstructor
    public enum ResourcePackStatus {

        SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED"),
        DECLINED("DECLINED"),
        FAILED_DOWNLOAD("FAILED_DOWNLOAD"),
        ACCEPTED("ACCEPTED");

        private final String name;

        public static ResourcePackStatus of(String input) {
            return Arrays.stream(values())
                    .filter(status -> status.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }
}
