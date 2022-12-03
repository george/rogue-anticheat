package org.hostile.anticheat.tracker.impl.movement;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.tracker.Tracker;
import org.hostile.anticheat.util.Collisions;

@Getter
public class CollisionTracker extends Tracker {

    private Collisions collisions = new Collisions();
    private Collisions previousCollisions = new Collisions();

    public CollisionTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            JsonObject collisions = event.getJsonObject().get("collisions").getAsJsonObject();

            this.previousCollisions = this.collisions;
            this.collisions = new Collisions(collisions);
        }
    }
}
