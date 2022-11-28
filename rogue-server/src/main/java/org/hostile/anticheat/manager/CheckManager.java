package org.hostile.anticheat.manager;

import org.hostile.anticheat.check.impl.autoclicker.AutoClickerA;
import org.hostile.anticheat.check.impl.autoclicker.AutoClickerB;
import org.hostile.anticheat.check.impl.autoclicker.AutoClickerC;
import org.hostile.anticheat.check.impl.reach.ReachA;
import org.hostile.anticheat.check.impl.speed.SpeedA;
import org.hostile.anticheat.check.timer.TimerA;
import org.hostile.anticheat.check.type.Check;
import org.hostile.anticheat.data.PlayerData;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckManager {

    private static final List<Constructor<? extends Check<?>>> CHECK_CONSTRUCTORS = Arrays.asList(
            AutoClickerA.class, AutoClickerB.class, AutoClickerC.class,

            ReachA.class,

            SpeedA.class,

            TimerA.class
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
