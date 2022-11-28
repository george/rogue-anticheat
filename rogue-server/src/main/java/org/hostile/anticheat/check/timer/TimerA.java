package org.hostile.anticheat.check.timer;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.util.MathUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@CheckMetadata(type = "Timer", name = "A")
public class TimerA extends PacketCheck {

    private Queue<Long> delays = new ConcurrentLinkedQueue<>();

    private long lastFlying = System.currentTimeMillis();

    public TimerA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            long timestamp = event.getTimestamp();
            long timeElapsed = timestamp - this.lastFlying;

            this.delays.add(timeElapsed);

            if (!movementTracker.isTeleporting() && this.delays.size() == 30) {
                double averageDelay = MathUtil.getAverage(delays);
                double difference = Math.abs(50 - averageDelay);

                if (difference > 3) {
                    if (incrementBuffer(1) > 5) {
                        fail("averageDelay", averageDelay, "difference", difference);
                    }
                } else {
                    decrementBuffer(0.25);
                }

                this.delays.clear();
            }
            this.lastFlying = timestamp;
        }
    }
}
