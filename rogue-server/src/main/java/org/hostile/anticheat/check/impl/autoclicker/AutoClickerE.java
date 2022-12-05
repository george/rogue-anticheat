package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.AutoClickerCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.util.MathUtil;

@CheckMetadata(type = "AutoClicker", name = "E")
public class AutoClickerE extends AutoClickerCheck {

    public AutoClickerE(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle() {
        double skewness = MathUtil.getSkewness(clicks);

        if (skewness < 0.5) { // TODO: Test this against legit players
            fail("skewness", skewness);
        }
    }

    @Override
    public int getSampleSize() {
        return 500;
    }
}
