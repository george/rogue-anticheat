package org.hostile.anticheat.packet.outbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayOutAbilities extends Packet {

    private final boolean invulnerable;
    private final boolean flying;
    private final boolean canFly;
    private final boolean canInstantlyBuild;
    private final float flySpeed;
    private final float walkSpeed;

    public WrappedPacketPlayOutAbilities(JsonObject object) {
        this.invulnerable = object.get("invulnerable").getAsBoolean();
        this.flying = object.get("flying").getAsBoolean();
        this.canFly = object.get("canFly").getAsBoolean();
        this.canInstantlyBuild = object.get("canInstantlyBuild").getAsBoolean();
        this.flySpeed = object.get("flySpeed").getAsFloat();
        this.walkSpeed = object.get("walkSpeed").getAsFloat();
    }
}
