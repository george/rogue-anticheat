package org.hostile.anticheat.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.AntiCheatServer;
import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.check.type.impl.PacketCheck;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.Packet;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.tracker.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerData {

    private final List<Check<?>> checks = new ArrayList<>();
    private final List<JsonObject> violations = new ArrayList<>();

    private final UUID uuid;

    private final ActionTracker actionTracker;
    private final CollisionTracker collisionTracker;
    private final EntityTracker entityTracker;
    private final MovementTracker movementTracker;
    private final PingTracker pingTracker;
    private final PotionTracker potionTracker;

    private int entityId;
    private int ticksExisted;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;

        this.actionTracker = new ActionTracker(this);
        this.collisionTracker = new CollisionTracker(this);
        this.entityTracker = new EntityTracker(this);
        this.movementTracker = new MovementTracker(this);
        this.pingTracker = new PingTracker(this);
        this.potionTracker = new PotionTracker(this);

        this.checks.addAll(AntiCheatServer.getInstance().getCheckManager().getChecks(this));
    }

    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            ++this.ticksExisted;
            this.entityId = event.getJsonObject().get("entityId").getAsInt();
        }

        this.actionTracker.handle(event);
        this.collisionTracker.handle(event);
        this.entityTracker.handle(event);
        this.movementTracker.handle(event);
        this.pingTracker.handle(event);
        this.potionTracker.handle(event);

        this.checks.stream()
                .filter(check -> check instanceof PacketCheck)
                .forEach(check -> ((PacketCheck) check).handle(event));
    }

    public JsonArray getViolations() {
        JsonArray array = new JsonArray();

        this.violations.forEach(array::add);
        this.violations.clear();

        return array;
    }
}