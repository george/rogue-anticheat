package org.hostile.anticheat.check.type.impl.rotation;

import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.RotationEvent;

public abstract class AttackCheck extends RotationCheck {

    public AttackCheck(PlayerData playerData) {
        super(playerData);
    }

    public abstract void handleRotation(RotationEvent event);

    @Override
    public void handle(RotationEvent event) {
        if (!actionTracker.hasAttackedRecently()) {
            return;
        }

        handleRotation(event);
    }
}
