package org.hostile.anticheat.manager;

import org.hostile.anticheat.check.impl.aimassist.AimAssistA;
import org.hostile.anticheat.check.impl.aimassist.AimAssistB;
import org.hostile.anticheat.check.impl.aimassist.AimAssistC;
import org.hostile.anticheat.check.impl.autoclicker.*;
import org.hostile.anticheat.check.impl.badpackets.BadPacketsA;
import org.hostile.anticheat.check.impl.badpackets.BadPacketsB;
import org.hostile.anticheat.check.impl.fly.FlyA;
import org.hostile.anticheat.check.impl.fly.FlyB;
import org.hostile.anticheat.check.impl.reach.ReachA;
import org.hostile.anticheat.check.impl.speed.SpeedA;
import org.hostile.anticheat.check.impl.velocity.VelocityA;
import org.hostile.anticheat.check.impl.killaura.KillAuraA;
import org.hostile.anticheat.check.impl.timer.TimerA;
import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckManager {

    private static final List<Constructor<? extends Check<?>>> CHECK_CONSTRUCTORS = Arrays.asList(
            AimAssistA.class, AimAssistB.class, AimAssistC.class,

            AutoClickerA.class, AutoClickerB.class, AutoClickerC.class, AutoClickerD.class,
            AutoClickerE.class,

            BadPacketsA.class, BadPacketsB.class,

            FlyA.class, FlyB.class,

            KillAuraA.class,

            ReachA.class,

            SpeedA.class,

            TimerA.class,

            VelocityA.class
    ).stream().map(clazz -> {
        try {
            return clazz.getDeclaredConstructor(PlayerData.class);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }).collect(Collectors.toList());

    public List<Check<?>> getChecks(PlayerData playerData) {
        return CHECK_CONSTRUCTORS.stream()
                .map(constructor -> {
                    try {
                        return constructor.newInstance(playerData);
                    } catch (Exception exc) {
                        return null;
                    }
                }).collect(Collectors.toList());
    }
}
