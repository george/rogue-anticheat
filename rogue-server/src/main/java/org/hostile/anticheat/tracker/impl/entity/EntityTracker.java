package org.hostile.anticheat.tracker.impl.entity;

import lombok.Getter;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.location.Vector;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutDestroyEntities;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutEntity;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutEntityTeleport;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutNamedEntitySpawn;
import org.hostile.anticheat.tracker.Tracker;
import org.hostile.anticheat.util.entity.TrackedEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class EntityTracker extends Tracker {

    private final Map<Short, Queue<Runnable>> lagCompensation = new ConcurrentHashMap<>();
    private final Map<Integer, TrackedEntity> trackedEntities = new ConcurrentHashMap<>();

    public EntityTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayOutEntity) {
            WrappedPacketPlayOutEntity packet = (WrappedPacketPlayOutEntity) event.getPacket();

            TrackedEntity entity = trackedEntities.get(packet.getEntityId());

            if (entity == null) {
                return;
            }

            double x = (double) packet.getPosX() / 32D;
            double y = (double) packet.getPosY() / 32D;
            double z = (double) packet.getPosZ() / 32D;

            add(() -> entity.handleRelMove(x, y, z));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutEntityTeleport) {
            WrappedPacketPlayOutEntityTeleport packet = (WrappedPacketPlayOutEntityTeleport) event.getPacket();

            TrackedEntity entity = trackedEntities.computeIfAbsent(packet.getEntityId(), (id) -> new TrackedEntity());

            double x = (double) packet.getPosX() / 32D;
            double y = (double) packet.getPosY() / 32D;
            double z = (double) packet.getPosZ() / 32D;

            add(() -> entity.addPosition(new Vector(x, y, z)));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutNamedEntitySpawn) {
            WrappedPacketPlayOutNamedEntitySpawn packet = (WrappedPacketPlayOutNamedEntitySpawn) event.getPacket();

            TrackedEntity entity = trackedEntities.computeIfAbsent(packet.getEntityId(), (id) -> new TrackedEntity());

            double x = packet.getPosX() / 32D;
            double y = packet.getPosY() / 32D;
            double z = packet.getPosZ() / 32D;

            add(() -> entity.addPosition(new Vector(x, y, z)));
        } else if (event.getPacket() instanceof WrappedPacketPlayOutDestroyEntities) {
            WrappedPacketPlayOutDestroyEntities packet = (WrappedPacketPlayOutDestroyEntities) event.getPacket();

            add(() -> packet.getEntities().forEach(trackedEntities::remove));
        } else if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            short transactionId = packet.getTransactionId();

            lagCompensation.getOrDefault(transactionId, new ConcurrentLinkedQueue<>()).forEach(Runnable::run);
            lagCompensation.remove(transactionId);
        }
    }

    public void add(Runnable runnable) {
        lagCompensation.computeIfAbsent(data.getPingTracker().getLastTransaction(),
                (transaction) -> new ConcurrentLinkedQueue<>()
        ).add(runnable);
    }
}
