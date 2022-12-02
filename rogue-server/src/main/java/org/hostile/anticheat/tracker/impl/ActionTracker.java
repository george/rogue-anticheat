package org.hostile.anticheat.tracker.impl;

import lombok.Getter;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInBlockDig;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInEntityAction;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInUseEntity;
import org.hostile.anticheat.tracker.Tracker;

@Getter
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

            if (packet.getAction() != WrappedPacketPlayInUseEntity.EntityUseAction.ATTACK) {
                return;
            }

            this.lastAttack = data.getTicksExisted() + 1;
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

    public boolean isAttacking() {
        return data.getTicksExisted() == lastAttack;
    }
}
