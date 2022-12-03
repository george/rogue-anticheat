package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInHeldItemSlot extends Packet {

    private final int slot;

    public WrappedPacketPlayInHeldItemSlot(JsonObject object) {
        this.slot = object.get("slot").getAsInt();
    }
}
