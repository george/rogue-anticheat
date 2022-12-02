package org.hostile.anticheat.check.impl.autoclicker;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInArmAnimation;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Checks for excessively high CPS without double-clicking
 */
@CheckMetadata(type = "AutoClicker", name = "D")
public class AutoClickerD extends PacketCheck {

    private final Queue<Integer> ticks = new ConcurrentLinkedQueue<>();

    private long lastCheck;

    public AutoClickerD(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            if (event.getTimestamp() - lastCheck > 1000) {
                int cps = this.ticks.size();

                if (cps > 15) {
                    double distinct = this.ticks.stream().distinct().count();
                    double doubleClicks = cps - distinct;
                    double ratio = distinct / doubleClicks;

                    if (doubleClicks < 1) {
                        if (incrementBuffer(1) > 3) {
                            fail("cps", cps, "distinct", distinct, "doubleClicks", doubleClicks, "ratio", ratio);
                        }
                    } else {
                        decrementBuffer(0.5);
                    }

                    this.ticks.clear();
                    this.lastCheck = event.getTimestamp();
                }
            }
        } else if (event.getPacket() instanceof WrappedPacketPlayInArmAnimation && !actionTracker.isDigging()) {
            this.ticks.add(data.getTicksExisted());
        }
    }
}
