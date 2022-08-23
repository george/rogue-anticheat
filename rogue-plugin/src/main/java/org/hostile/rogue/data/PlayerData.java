package org.hostile.rogue.data;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.hostile.rogue.RoguePlugin;
import org.hostile.rogue.data.tracker.impl.CollisionTracker;
import org.hostile.rogue.data.tracker.impl.MovementTracker;
import org.hostile.rogue.packet.WrappedPacket;

import java.io.IOException;

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

    public void handlePacket(WrappedPacket packet) {
        movementTracker.handlePacket(packet);
        collisionTracker.handlePacket(packet);

        /*
        Since we're sending data through an HTTP request very quickly,
        making a new thread every time we want to send data is more
        advantageous than using an executor service.
         */
        new Thread(() -> {
            try {
                instance.getRogueWebClient().sendPacket(this, packet);
            } catch (IOException exc) {
//                exc.printStackTrace();
            }
        }).start();
    }

    public void handleQuit() {
        new Thread(() -> {
            try {
                instance.getRogueWebClient().sendQuit(player.getUniqueId());
            } catch (IOException exc) {
//                exc.printStackTrace();
            }
        }).start();
    }
}