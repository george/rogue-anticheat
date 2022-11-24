package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutEntity extends Packet {

    private final int entityId;
    private final byte posX, posY, posZ;
    private final byte yaw, pitch;

    public WrappedPacketPlayOutEntity(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.posX = object.get("posX").getAsByte();
        this.posY = object.get("posY").getAsByte();
        this.posZ = object.get("posZ").getAsByte();
        this.yaw = object.get("yaw").getAsByte();
        this.pitch = object.get("pitch").getAsByte();
    }
}
