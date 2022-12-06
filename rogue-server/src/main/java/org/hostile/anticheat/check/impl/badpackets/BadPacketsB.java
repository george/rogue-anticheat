package org.hostile.anticheat.check.impl.badpackets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.packet.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInHeldItemSlot;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInTransaction;
import org.hostile.anticheat.packet.outbound.WrappedPacketPlayOutHeldItemSlot;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@CheckMetadata(type = "BadPackets", name = "B")
public class BadPacketsB extends PacketCheck {

    private final Queue<SlotChange> slotChanges = new ConcurrentLinkedQueue<>();

    private int lastSlot;

    public BadPacketsB(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInHeldItemSlot) {
            WrappedPacketPlayInHeldItemSlot packet = (WrappedPacketPlayInHeldItemSlot) event.getPacket();

            int slot = packet.getSlot();

            if (slot > 8 || slot < 0) {
                fail("slot", slot);
            } else if (slot == lastSlot) {
                fail("slot", slot, "lastSlot", lastSlot);
            }

            lastSlot = slot;
        } else if (event.getPacket() instanceof WrappedPacketPlayOutHeldItemSlot) {
            WrappedPacketPlayInHeldItemSlot packet = (WrappedPacketPlayInHeldItemSlot) event.getPacket();

            slotChanges.add(new SlotChange(packet.getSlot(), pingTracker.getLastTransaction()));
        } else if (event.getPacket() instanceof WrappedPacketPlayInTransaction) {
            WrappedPacketPlayInTransaction packet = (WrappedPacketPlayInTransaction) event.getPacket();

            slotChanges.removeIf(slotChange -> {
                if (packet.getTransactionId() == slotChange.getTransactionId()) {
                    lastSlot = slotChange.getSlot();
                    return true;
                }
                return false;
            });
        }
    }

    @Getter @AllArgsConstructor
    private static class SlotChange {

        private final int slot;
        private final int transactionId;

    }
}
