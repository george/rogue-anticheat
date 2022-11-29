package org.hostile.anticheat.check.killaura;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.PacketCheck;
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
            this.sameTick = false;
        } else if (event.getPacket() instanceof WrappedPacketPlayInUseEntity) {
            WrappedPacketPlayInUseEntity packet = (WrappedPacketPlayInUseEntity) event.getPacket();

            if (packet.getAction() != WrappedPacketPlayInUseEntity.EntityUseAction.ATTACK) {
                return;
            }

            int entityId = packet.getEntityId();

            if (this.sameTick && (entityId != this.entityId)) {
                fail("entityId", entityId, "previousEntityId", this.entityId);
            }

            this.entityId = entityId;
            this.sameTick = true;
        }
    }
}
