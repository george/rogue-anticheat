package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutRemoveEntityEffect extends Packet {

    private final int entityId;
    private final byte effectId;

    public WrappedPacketPlayOutRemoveEntityEffect(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.effectId = object.get("effectId").getAsByte();
    }
}
