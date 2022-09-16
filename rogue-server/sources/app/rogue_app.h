#pragma once

#include "../config/rogue_config.h"
#include "../config/data/check_data.h"
#include "crow.h"

namespace rogue_app {

    extern RogueConfig config;

    auto initialize() -> std::unique_ptr<crow::SimpleApp>;

    auto getCheckData(const std::string& checkIdentifier) -> CheckData;

    template<typename Base, typename U>
    auto checkInstance(const U *ptr) -> bool;

}