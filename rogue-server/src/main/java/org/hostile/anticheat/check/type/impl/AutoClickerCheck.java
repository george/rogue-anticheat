package org.hostile.anticheat.check.type.impl;

import org.hostile.anticheat.check.type.impl.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInArmAnimation;

import java.util.ArrayList;
import java.util.List;

public abstract class AutoClickerCheck extends PacketCheck {

    protected final List<Integer> clicks = new ArrayList<>();

    private long lastAttack = System.currentTimeMillis();

    private long sampleSize = getSampleSize();

    public AutoClickerCheck(PlayerData playerData) {
        super(playerData);
    }

    public abstract void handle();

    public abstract int getSampleSize();

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInArmAnimation) {
            if (actionTracker.isDigging()) {
                return;
            }

            long timestamp = event.getTimestamp();
            long timeOffset = timestamp - lastAttack;

            if (timeOffset > 500) {
                lastAttack = timestamp;
                return;
            }

            int ticks = (int) Math.floorDiv(timeOffset, 50);

            clicks.add(ticks);

            if (clicks.size() >= sampleSize) {
                handle();
                clicks.clear();
            }

            lastAttack = timestamp;
        }
    }
}
