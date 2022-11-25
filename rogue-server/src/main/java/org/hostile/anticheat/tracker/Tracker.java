package org.hostile.anticheat.tracker;

import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;

public abstract class Tracker {

    protected final PlayerData data;

    public Tracker(PlayerData data) {
        this.data = data;
    }

    public abstract void handle(PacketEvent event);

}
