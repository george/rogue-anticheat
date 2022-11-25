package org.hostile.anticheat.tracker.impl;

import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.Packet;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInBlockDig;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInEntityAction;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInUseEntity;
import org.hostile.anticheat.tracker.Tracker;

public class ActionTracker extends Tracker {

    private int lastAttack;

    private boolean sneaking;
    private boolean digging;

    public ActionTracker(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity packet = (WrappedPacketPlayInUseEntity) event.getPacket();

            this.lastAttack = data.getTicksExisted();
        } else if (event.getPacket() instanceof WrappedPacketPlayInEntityAction) {
            WrappedPacketPlayInEntityAction packet = (WrappedPacketPlayInEntityAction) event.getPacket();

            switch (packet.getAction()) {
                case START_SNEAKING:
                    this.sneaking = true;
                    break;
                case STOP_SNEAKING:
                    this.sneaking = false;
                    break;
            }
        } else if (event.getPacket() instanceof WrappedPacketPlayInBlockDig) {
            WrappedPacketPlayInBlockDig packet = (WrappedPacketPlayInBlockDig) event.getPacket();

            switch (packet.getPlayerDigType()) {
                case START_DESTROY_BLOCK:
                    this.digging = true;
                    break;
                case STOP_DESTROY_BLOCK:
                case ABORT_DESTROY_BLOCK:
                    this.digging = false;
                    break;
            }
        }
    }
}
