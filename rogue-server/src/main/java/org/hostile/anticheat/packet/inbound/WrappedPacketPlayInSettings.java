package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

import java.util.Arrays;

@Getter
public class WrappedPacketPlayInSettings extends Packet {

    private final String language;
    private final ChatVisibility chatVisibility;

    private final boolean enableColors;

    private final int view;
    private final int modelPartFlags;

    public WrappedPacketPlayInSettings(JsonObject object) {
        this.language = object.get("language").getAsString();
        this.view = object.get("view").getAsInt();
        this.chatVisibility = ChatVisibility.of(object.get("chatVisibility").getAsString());
        this.enableColors = object.get("enableColors").getAsBoolean();
        this.modelPartFlags = object.get("modelPartFlags").getAsInt();
    }

    @Getter @AllArgsConstructor
    public enum ChatVisibility {

        FULL("FULL"),
        SYSTEM("SYSTEM"),
        HIDDEN("HIDDEN");

        private final String name;

        public static ChatVisibility of(String input) {
            return Arrays.stream(values())
                    .filter(visibility -> visibility.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }
    }
}
