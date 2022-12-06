package org.hostile.anticheat.check.type.impl.movement;

import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;

public abstract class PositionUpdateCheck extends Check<PositionUpdateEvent> {

    public PositionUpdateCheck(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public abstract void handle(PositionUpdateEvent event);

}
