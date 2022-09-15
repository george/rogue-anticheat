#pragma once

#include <vector>
#include <deque>

#include <nlohmann/json.hpp>

#include "../check/check.h"
#include "../tracker/tracker.h"

class PlayerData {

private:

    std::vector<Tracker*> trackers{};
//    std::vector<Check*> checks{};
//    std::deque<Violation> violations{};

    int ticksExisted;
    int entityId;

    std::string uuid;

public:

    PlayerData();

    auto hasViolations() -> bool;

    auto getViolations() -> nlohmann::json;

    auto handlePacket() -> void;


};