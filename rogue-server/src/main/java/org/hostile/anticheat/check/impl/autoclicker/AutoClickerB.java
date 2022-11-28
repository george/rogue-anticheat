package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.AutoClickerCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.util.MathUtil;

@CheckMetadata(type = "AutoClicker", name = "B")
public class AutoClickerB extends AutoClickerCheck {

    public AutoClickerB(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle() {
        double kurtosis = MathUtil.getKurtosis(clicks);

        if (kurtosis < 0) {
            if (incrementBuffer(1) > 3) {
                fail("kurtosis", kurtosis);
            }
        } else {
            decrementBuffer(0.25);
        }
    }

    @Override
    public int getSampleSize() {
        return 400;
    }
}
