package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.packet.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInArmAnimation;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Checks for excessively high CPS without double-clicking
 */
@CheckMetadata(type = "AutoClicker", name = "D")
public class AutoClickerD extends PacketCheck {

    private final List<Integer> ticks = Collections.synchronizedList(new ArrayList<>());

    public AutoClickerD(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying && data.getTicksExisted() % 20 == 0) {
            int cps = ticks.size();

            if (cps >= 16) {
                double distinct = ticks.stream().distinct().count();
                double doubleClicks = cps - distinct;
                double ratio = distinct / doubleClicks;

                if (doubleClicks < 1) {
                    if (incrementBuffer(1) > 3) {
                        fail("cps", cps, "distinct", distinct, "doubleClicks", doubleClicks, "ratio", ratio);
                    }
                } else {
                    decrementBuffer(0.5);
                }
            }

            ticks.clear();
        } else if (event.getPacket() instanceof WrappedPacketPlayInArmAnimation && !actionTracker.isDigging()) {
            ticks.add(data.getTicksExisted());
        }
    }
}
