package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInSetCreativeSlot extends Packet {

    private final int slot;
    private final String itemStack;

    public WrappedPacketPlayInSetCreativeSlot(JsonObject object) {
        this.slot = object.get("slot").getAsInt();
        this.itemStack = object.get("material").getAsString();
    }
}
