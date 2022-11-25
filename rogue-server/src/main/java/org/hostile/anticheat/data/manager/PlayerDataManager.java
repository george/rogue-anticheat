package org.hostile.anticheat.data.manager;

import org.hostile.anticheat.data.PlayerData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public PlayerData getData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, PlayerData::new);
    }
}
