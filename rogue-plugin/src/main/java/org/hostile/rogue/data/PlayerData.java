package org.hostile.rogue.data;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.hostile.rogue.data.tracker.impl.CollisionTracker;
import org.hostile.rogue.data.tracker.impl.MovementTracker;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class PlayerData {

    private final Player player;

    private final CollisionTracker collisionTracker;
    private final MovementTracker movementTracker;

    private int ticksExisted;

    public PlayerData(Player player) {
        this.player = player;

        this.collisionTracker = new CollisionTracker(this);
        this.movementTracker = new MovementTracker(this);
    }

    public void handlePacket(WrappedPacket packet) {
    }

    public int getKeepAlivePing() {
        return 0;
    }

    public int getTransactionPing() {
        return 0;
    }
}