package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.AutoClickerCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.util.MathUtil;

@CheckMetadata(type = "AutoClicker", name = "A")
public class AutoClickerA extends AutoClickerCheck {

    public AutoClickerA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle() {
        debug("stDev", MathUtil.getStandardDeviation(clicks));
    }

    @Override
    public int getSampleSize() {
        return 100;
    }
}
