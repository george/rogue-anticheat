package org.hostile.anticheat.check.impl.speed;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;

@CheckMetadata(type = "Speed", name = "A")
public class SpeedA extends PositionUpdateCheck {

    public SpeedA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        debug("distance", event.getOffsetXZ());
    }
}
