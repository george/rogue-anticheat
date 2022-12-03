package org.hostile.anticheat.tracker.impl.player;

import lombok.Getter;
import org.hostile.anticheat.AntiCheatServer;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutTransaction;
import org.hostile.anticheat.tracker.Tracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PingTracker extends Tracker {

    private final long maxPing = AntiCheatServer.getInstance().getServerConfiguration().getMaxPing();

    private final Map<Short, Long> transactionMap = new ConcurrentHashMap<>();

    private short lastTransaction;

    private long lastPing;

    public PingTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        long timestamp = event.getTimestamp();

        if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            if (!transactionMap.containsKey(transactionId)) {
                return;
            }

            long sentAt = transactionMap.get(transactionId);

            lastPing = timestamp - sentAt;
            transactionMap.remove(transactionId);
        } else if (event.getPacket() instanceof WrappedPacketPlayOutTransaction) {
            WrappedPacketPlayOutTransaction packet = (WrappedPacketPlayOutTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            lastTransaction = transactionId;
            transactionMap.put(transactionId, timestamp);
        } else if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            transactionMap.forEach((transactionId, packetTimestamp) -> {
                if (timestamp - packetTimestamp > maxPing) {
                    //TODO: Handle ping being higher than max
                }
            });
        }
    }
}
