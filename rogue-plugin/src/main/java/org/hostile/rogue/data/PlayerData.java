package org.hostile.rogue.data;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.hostile.rogue.RoguePlugin;
import org.hostile.rogue.data.tracker.impl.CollisionTracker;
import org.hostile.rogue.data.tracker.impl.MovementTracker;
import org.hostile.rogue.packet.WrappedPacket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class PlayerData {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    private final RoguePlugin instance = RoguePlugin.getInstance();

    private final Player player;

    private final CollisionTracker collisionTracker;
    private final MovementTracker movementTracker;

    public PlayerData(Player player) {
        this.player = player;

        this.collisionTracker = new CollisionTracker(this);
        this.movementTracker = new MovementTracker(this);
    }

    public void handlePacket(WrappedPacket packet) {
        movementTracker.handlePacket(packet);
        collisionTracker.handlePacket(packet);

        EXECUTOR_SERVICE.execute(() -> {
            try {
                instance.getRogueWebClient().sendPacket(this, packet);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
    }

    public void handleQuit() {
        EXECUTOR_SERVICE.execute(() -> {
            try {
                instance.getRogueWebClient().sendQuit(player.getUniqueId());
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
    }
}