#pragma once

#include <map>

#include "../config/rogue_config.h"
#include "../config/data/check_data.h"
#include "../data/player_template.h"

#include "crow.h"

namespace rogue_app {

    extern std::map<std::string, PlayerTemplate*> playerDataMap;

    extern RogueConfig config;

    auto getCheckData(const std::string &checkIdentifier) -> CheckData;

    auto getPlayerData(const std::string &uuid) -> PlayerTemplate*;

    auto removePlayerData(const std::string &uuid) -> void;

    template<typename Base, typename U>
    auto checkInstance(const U *ptr) -> bool;

}