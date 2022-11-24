package org.hostile.anticheat.data.manager;

import org.hostile.anticheat.data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getData(UUID uuid) {
        return playerDataMap.compute(uuid, (playerUuid, data) -> {
            if (data == null) {
                return new PlayerData(playerUuid);
            }

            return data;
        });
    }
}
