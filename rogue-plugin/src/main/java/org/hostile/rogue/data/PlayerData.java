package org.hostile.rogue.data;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.hostile.rogue.RoguePlugin;
import org.hostile.rogue.data.tracker.impl.CollisionTracker;
import org.hostile.rogue.data.tracker.impl.MovementTracker;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class PlayerData {

    private final RoguePlugin instance = RoguePlugin.getInstance();

    private final Player player;

    private final CollisionTracker collisionTracker;
    private final MovementTracker movementTracker;

    public PlayerData(Player player) {
        this.player = player;

        this.collisionTracker = new CollisionTracker(this);
        this.movementTracker = new MovementTracker(this);
    }

    @SneakyThrows
    public void handlePacket(WrappedPacket packet) {
        movementTracker.handlePacket(packet);
        collisionTracker.handlePacket(packet);

        instance.getRogueWebClient().sendPacket(this, packet);
    }

    @SneakyThrows
    public void handleQuit() {
        instance.getRogueWebClient().sendQuit(player.getUniqueId());
    }
}