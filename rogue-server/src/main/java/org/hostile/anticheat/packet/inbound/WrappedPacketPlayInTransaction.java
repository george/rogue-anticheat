package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInTransaction extends Packet {

    private final int windowId;
    private final short transactionId;
    private final boolean accepted;

    public WrappedPacketPlayInTransaction(JsonObject object) {
        this.windowId = object.get("windowId").getAsInt();
        this.transactionId = object.get("transactionId").getAsShort();
        this.accepted = object.get("accepted").getAsBoolean();
    }
}
