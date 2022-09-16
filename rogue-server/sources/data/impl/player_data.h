#pragma once

#include <vector>
#include <deque>

#include "nlohmann/json.hpp"

#include "../../check/check.h"
#include "../player_template.h"
#include "../../tracker/tracker.h"
#include "../../violation/violation.h"

class PlayerData : public PlayerTemplate {

private:

    std::vector<Tracker*> trackers{};
    std::vector<Check*> checks{};
    std::deque<Violation> violations{};

    int ticksExisted;
    int entityId;

    std::string uuid;

public:

    explicit PlayerData(std::string uuid);

    auto hasViolations() -> bool override;

    auto getViolations() -> nlohmann::json override;

    auto handlePacket(PacketEvent event) -> void override;

    auto getEntityId() -> int override;

    auto getTicksExisted() -> int override;

    auto getUuid() -> std::string override;

};