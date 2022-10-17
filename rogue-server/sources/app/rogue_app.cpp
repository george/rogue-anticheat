#include "rogue_app.h"

#include "../data/impl/player_data.h"

RogueConfig rogue_app::config = RogueConfig();
std::mutex rogue_app::mutex{};
std::map<std::string, PlayerTemplate*> rogue_app::playerDataMap = {};

auto rogue_app::getCheckData(const std::string &checkIdentifier) -> CheckData {
    mutex.lock();
    auto checkData = config.getCheckData(checkIdentifier);
    mutex.unlock();

    return checkData;
}

auto rogue_app::getPlayerData(const std::string &uuid) -> PlayerTemplate* {
    auto iterator = rogue_app::playerDataMap.find(uuid);

    if (iterator == rogue_app::playerDataMap.end()) {
        auto playerData = new PlayerData(uuid);

        rogue_app::playerDataMap.insert(std::make_pair(uuid, playerData));
        return playerData;
    }

    return iterator->second;
}

auto rogue_app::removePlayerData(const std::string &uuid) -> void {
    auto iterator = rogue_app::playerDataMap.find(uuid);

    if (iterator != rogue_app::playerDataMap.end()) {
        delete iterator->second;
        rogue_app::playerDataMap.erase(iterator);
    }
}