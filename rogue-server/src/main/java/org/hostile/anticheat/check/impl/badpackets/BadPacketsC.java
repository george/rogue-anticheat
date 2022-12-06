package org.hostile.anticheat.check.impl.badpackets;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.packet.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInBlockPlace;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInHeldItemSlot;

@CheckMetadata(type = "BadPackets", name = "C")
public class BadPacketsC extends PacketCheck {

    private boolean switched;

    public BadPacketsC(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            switched = false;
        } else if (event.getPacket() instanceof WrappedPacketPlayInBlockPlace) {
            if (switched) {
                fail("switched", true);
            }
        } else if (event.getPacket() instanceof WrappedPacketPlayInHeldItemSlot) {
            switched = true;
        }
    }
}
