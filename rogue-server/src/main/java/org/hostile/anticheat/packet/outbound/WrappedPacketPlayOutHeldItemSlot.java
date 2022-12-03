package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutHeldItemSlot extends Packet {

    private final int slot;

    public WrappedPacketPlayOutHeldItemSlot(JsonObject object) {
        this.slot = object.get("slot").getAsInt();
    }
}
