package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInEnchantItem extends Packet {

    private final int windowId;
    private final int button;

    public WrappedPacketPlayInEnchantItem(JsonObject object) {
        this.windowId = object.get("windowId").getAsInt();
        this.button = object.get("button").getAsInt();
    }
}
