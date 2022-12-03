package org.hostile.anticheat.check.impl.aimassist;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.rotation.AttackCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.RotationEvent;

@CheckMetadata(type = "AimAssist", name = "A")
public class AimAssistA extends AttackCheck {

    public AimAssistA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handleRotation(RotationEvent event) {
        if (!actionTracker.isAttacking()) {
            return;
        }

        double sensitivityX = event.getSensitivityX();
        double sensitivityY = event.getSensitivityY();

        if (sensitivityX < 0 && sensitivityY < 0) {
            if (incrementBuffer(1) > 10) {
                fail("sensitivityX", sensitivityX, "sensitivityY", sensitivityY);
            }
        } else {
            decrementBuffer(0.25);
        }

        debug("sensitivity", sensitivityX);
    }
}
