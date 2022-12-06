package org.hostile.anticheat.check.impl.badpackets;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.movement.PositionUpdateCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PositionUpdateEvent;

@CheckMetadata(type = "BadPackets", name = "A")
public class BadPacketsA extends PositionUpdateCheck {

    public BadPacketsA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PositionUpdateEvent event) {
        float pitch = event.getCurrentLocation().getPitch();

        if (Math.abs(pitch) > 90) {
            fail("pitch", pitch);
        }
    }
}
