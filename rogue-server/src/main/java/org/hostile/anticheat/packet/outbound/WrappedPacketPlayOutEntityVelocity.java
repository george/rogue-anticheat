package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutEntityVelocity extends Packet {

    private final int entityId;

    private final int velocityX;
    private final int velocityY;
    private final int velocityZ;

    public WrappedPacketPlayOutEntityVelocity(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.velocityX = object.get("velocityX").getAsInt();
        this.velocityY = object.get("velocityY").getAsInt();
        this.velocityZ = object.get("velocityZ").getAsInt();
    }
}
