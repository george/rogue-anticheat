package org.hostile.anticheat.check.impl.aimassist;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.rotation.AttackCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.RotationEvent;

@CheckMetadata(type = "AimAssist", name = "B")
public class AimAssistB extends AttackCheck {

    public AimAssistB(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handleRotation(RotationEvent event) {
        double deltaPitch = event.getDeltaPitch();
        double gcd = event.getGcd();

        if (deltaPitch > 0.1 && gcd < 0.009) {
            if (incrementBuffer(1) > 5) {
                fail("gcd", gcd);
            }
        } else {
            decrementBuffer(0.35);
        }
    }
}
