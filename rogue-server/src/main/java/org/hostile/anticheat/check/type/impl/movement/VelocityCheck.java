package org.hostile.anticheat.check.type.impl.movement;

import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.VelocityEvent;

public abstract class VelocityCheck extends Check<VelocityEvent> {

    protected static final float JUMP_HEIGHT = .42F;

    public VelocityCheck(PlayerData playerData) {
        super(playerData);
    }

}