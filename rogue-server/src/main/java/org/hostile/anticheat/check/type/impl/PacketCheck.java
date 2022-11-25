package org.hostile.anticheat.check.type.impl;

import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;

public abstract class PacketCheck extends Check<PacketEvent> {

    public PacketCheck(PlayerData playerData) {
        super(playerData);
    }

    public abstract void handle(PacketEvent event);

}
