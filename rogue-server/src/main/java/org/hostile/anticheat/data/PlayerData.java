package org.hostile.anticheat.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.AntiCheatServer;
import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.check.type.impl.packet.PacketCheck;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.tracker.impl.entity.EntityTracker;
import org.hostile.anticheat.tracker.impl.movement.CollisionTracker;
import org.hostile.anticheat.tracker.impl.movement.MovementTracker;
import org.hostile.anticheat.tracker.impl.player.ActionTracker;
import org.hostile.anticheat.tracker.impl.player.PingTracker;
import org.hostile.anticheat.tracker.impl.player.PotionTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class PlayerData {

    private final List<Check<?>> checks = new ArrayList<>();
    private final Queue<JsonObject> violations = new ConcurrentLinkedQueue<>();

    private final UUID uuid;

    private final ActionTracker actionTracker;
    private final CollisionTracker collisionTracker;
    private final EntityTracker entityTracker;
    private final MovementTracker movementTracker;
    private final PingTracker pingTracker;
    private final PotionTracker potionTracker;

    private int entityId;
    private int ticksExisted;

    private String gameMode;
    private String worldName;

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
            ++ticksExisted;

            JsonObject object = event.getJsonObject();

            entityId = object.get("entityId").getAsInt();
            gameMode = object.get("gamemode").getAsString();
            worldName = object.get("world").getAsString();
        }

        actionTracker.handle(event);
        collisionTracker.handle(event);
        entityTracker.handle(event);
        movementTracker.handle(event);
        pingTracker.handle(event);
        potionTracker.handle(event);

        checks.stream()
                .filter(check -> check instanceof PacketCheck)
                .forEach(check -> ((PacketCheck) check).handle(event));
    }

    public JsonArray getViolations() {
        JsonArray array = new JsonArray();

        violations.forEach(array::add);
        violations.clear();

        return array;
    }

    public void addViolation(JsonObject jsonObject) {
        this.violations.add(jsonObject);
    }

    @SuppressWarnings("unchecked")
    public <T extends Check<?>> List<T> getChecks(Predicate<Check<?>> predicate) {
        return checks.stream()
                .filter(predicate)
                .map(check -> (T) check)
                .collect(Collectors.toList());
    }
}