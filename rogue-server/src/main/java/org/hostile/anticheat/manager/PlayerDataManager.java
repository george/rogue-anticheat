package org.hostile.anticheat.manager;

import org.hostile.anticheat.data.PlayerData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public PlayerData getData(UUID uuid) {
        return this.playerDataMap.computeIfAbsent(uuid, PlayerData::new);
    }

    public void handleQuit(UUID uuid) {
        this.playerDataMap.remove(uuid);
    }
}