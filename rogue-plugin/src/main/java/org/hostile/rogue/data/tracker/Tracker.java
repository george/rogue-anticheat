package org.hostile.rogue.data.tracker;

import org.hostile.rogue.data.PlayerData;
import org.hostile.rogue.packet.WrappedPacket;

public abstract class Tracker {

    protected final PlayerData data;

    public Tracker(PlayerData playerData) {
        this.data = playerData;
    }

    public abstract void handlePacket(WrappedPacket packet);

}
