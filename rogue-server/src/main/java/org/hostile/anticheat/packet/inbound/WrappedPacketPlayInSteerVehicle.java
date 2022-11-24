package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInSteerVehicle extends Packet {

    private final float strafeSpeed;
    private final float forwardSpeed;

    private final boolean jumping;
    private final boolean sneaking;

    public WrappedPacketPlayInSteerVehicle(JsonObject object) {
        this.strafeSpeed = object.get("strafeSpeed").getAsFloat();
        this.forwardSpeed = object.get("forwardsSpeed").getAsFloat();

        this.jumping = object.get("jumping").getAsBoolean();
        this.sneaking = object.get("sneaking").getAsBoolean();
    }
}
