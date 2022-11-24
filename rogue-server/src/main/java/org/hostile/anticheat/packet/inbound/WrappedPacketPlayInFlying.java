package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInFlying extends Packet {

    private final double x;
    private final double y;
    private final double z;

    private final float yaw;
    private final float pitch;

    private final boolean onGround;
    private final boolean moving;
    private final boolean rotating;

    public WrappedPacketPlayInFlying(JsonObject object) {
        this.x = object.get("x").getAsDouble();
        this.y = object.get("y").getAsDouble();
        this.z = object.get("z").getAsDouble();

        this.yaw = object.get("yaw").getAsFloat();
        this.pitch = object.get("pitch").getAsFloat();

        this.onGround = object.get("onGround").getAsBoolean();
        this.moving = object.get("moving").getAsBoolean();
        this.rotating = object.get("rotating").getAsBoolean();
    }
}
