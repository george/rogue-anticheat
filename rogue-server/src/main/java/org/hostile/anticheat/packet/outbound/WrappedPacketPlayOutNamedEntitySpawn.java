package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutNamedEntitySpawn extends Packet {

    private final int entityId;
    private final int posX;
    private final int posY;
    private final int posZ;

    public WrappedPacketPlayOutNamedEntitySpawn(JsonObject object) {
        this.entityId = object.get("entityId").getAsInt();
        this.posX = object.get("posX").getAsInt();
        this.posY = object.get("posY").getAsInt();
        this.posZ = object.get("posZ").getAsInt();
    }
}
