package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.AutoClickerCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.util.MathUtil;

@CheckMetadata(type = "AutoClicker", name = "C")
public class AutoClickerC extends AutoClickerCheck {

    public AutoClickerC(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle() {
        double stDev = MathUtil.getStandardDeviation(clicks);

        if (0.45 > stDev) {
            if (incrementBuffer(1) > 2) {
                fail("stDev", stDev);
            }
        } else {
            decrementBuffer(0.25);
        }
    }

    @Override
    public int getSampleSize() {
        return 100;
    }
}