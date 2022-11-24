package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutPosition extends Packet {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public WrappedPacketPlayOutPosition(JsonObject object) {
        this.x = object.get("x").getAsDouble();
        this.y = object.get("y").getAsDouble();
        this.z = object.get("z").getAsDouble();
        this.yaw = object.get("yaw").getAsFloat();
        this.pitch = object.get("pitch").getAsFloat();
    }
}
