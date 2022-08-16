package org.hostile.rogue.manager.impl;

import org.bukkit.entity.Player;
import org.hostile.rogue.data.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {

    private final Map<Player, PlayerData> playerData = new HashMap<>();

    public PlayerData getPlayerData(Player player) {
        return playerData.compute(player, (bukkitPlayer, playerData) -> {
            if (playerData == null) {
                return new PlayerData(player);
            }

            return playerData;
        });
    }
}
