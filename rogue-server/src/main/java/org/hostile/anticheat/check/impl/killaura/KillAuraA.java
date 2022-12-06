package org.hostile.anticheat.check.impl.killaura;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.packet.PacketCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.anticheat.packet.inbound.WrappedPacketPlayInUseEntity;

/**
 * Checks for illegal attacking behavior
 */
@CheckMetadata(type = "KillAura", name = "A")
public class KillAuraA extends PacketCheck {

    private boolean sameTick;

    private int entityId;

    public KillAuraA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event.getPacket() instanceof WrappedPacketPlayInFlying) {
            sameTick = false;
        } else if (event.getPacket() instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity packet = (WrappedPacketPlayInUseEntity) event.getPacket();

            if (packet.getAction() != WrappedPacketPlayInUseEntity.EntityUseAction.ATTACK) {
                return;
            }

            int packetEntityId = packet.getEntityId();

            if (sameTick && (packetEntityId != entityId)) {
                fail("entityId", packetEntityId, "previousEntityId", entityId);
            }

            sameTick = true;
            entityId = packetEntityId;
        }
    }
}
