package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutEntityEffect extends Packet {

    private final int entityId;
    private final byte effectId;
    private final byte amplifier;
    private final int duration;

    public WrappedPacketPlayOutEntityEffect(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.effectId = object.get("effectId").getAsByte();
        this.amplifier = object.get("amplifier").getAsByte();
        this.duration = object.get("duration").getAsInt();
    }
}
