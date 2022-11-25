package org.hostile.anticheat.tracker.impl;

import lombok.Getter;
import lombok.Setter;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutEntityEffect;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutRemoveEntityEffect;
import org.hostile.anticheat.tracker.Tracker;
import org.hostile.anticheat.util.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PotionTracker extends Tracker {

    private static final List<Integer> MONITORED_EFFECTS = Arrays.stream(PotionEffectType.values())
            .map(PotionEffectType::getPotionId)
            .collect(Collectors.toList());

    private final List<Potion> activePotions = new ArrayList<>();
    private final List<Potion> pendingPotions = new ArrayList<>();

    public PotionTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayOutEntityEffect) {
            WrappedPacketPlayOutEntityEffect packet = (WrappedPacketPlayOutEntityEffect) event.getPacket();

            if (!MONITORED_EFFECTS.contains((int) packet.getEffectId())) {
                return;
            }

            this.pendingPotions.add(new Potion(
                    PotionEffectType.of(packet.getEffectId()),
                    packet.getAmplifier(),
                    packet.getDuration(),
                    data.getPingTracker().getLastTransaction()
            ));
        } else if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            this.pendingPotions.removeIf(potion -> {
                if (potion.getTransactionId() == transactionId) {
                    potion.setExpiresAt(data.getTicksExisted() + (20 * potion.getDuration()));
                    this.activePotions.add(potion);
                    return true;
                }

                return false;
            });
        } else if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            this.activePotions.removeIf(potion -> potion.getExpiresAt() > data.getTicksExisted());
        } else if (event.getPacket() instanceof WrappedPacketPlayOutRemoveEntityEffect) {
            WrappedPacketPlayOutRemoveEntityEffect packet = (WrappedPacketPlayOutRemoveEntityEffect) event.getPacket();

            if (data.getEntityId() != packet.getEntityId()) {
                return;
            }

            this.pendingPotions.add(new Potion(
                    PotionEffectType.of(packet.getEffectId()),
                    0,
                    0,
                    data.getPingTracker().getLastTransaction())
            );
        }
    }

    public int getPotionLevel(PotionEffectType potionEffectType) {
        return activePotions.stream()
                .filter(potion -> potion.getType().equals(potionEffectType))
                .map(Potion::getAmplifier)
                .findFirst()
                .orElse(0);
    }

    @Getter
    private static class Potion {

        private final PotionEffectType type;

        private final int amplifier;
        private final int duration;

        private final short transactionId;

        @Setter private int expiresAt;

        public Potion(PotionEffectType type, int amplifier, int duration, short transactionId) {
            this.type = type;
            this.amplifier = amplifier;
            this.duration = duration;
            this.transactionId = transactionId;
        }
    }
}
