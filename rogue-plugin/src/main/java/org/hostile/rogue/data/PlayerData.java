package org.hostile.rogue.data;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class PlayerData {

    private final Player player;

    private int ticksExisted;

    public PlayerData(Player player) {
        this.player = player;
    }

    public void handlePacket(WrappedPacket packet) {
    }

    public int getKeepAlivePing() {
        return 0;
    }

    public int getTransactionPing() {
        return 0;
    }
}