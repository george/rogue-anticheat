package org.hostile.anticheat.check.type.impl.rotation;

import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.RotationEvent;

public abstract class RotationCheck extends Check<RotationEvent> {

    public RotationCheck(PlayerData playerData) {
        super(playerData);
    }
}
