package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInCloseWindow extends Packet {

    private final int windowId;

    public WrappedPacketPlayInCloseWindow(JsonObject object) {
        this.windowId = object.get("windowId").getAsInt();
    }
}
