#pragma once

#include <vector>
#include <deque>

#include "nlohmann/json.hpp"

#include "../../check/check.h"
#include "../player_template.h"
#include "../tracker_provider.h"
#include "../../tracker/tracker.h"
#include "../../violation/violation.h"
#include "../../tracker/impl/ping_tracker.h"

class PlayerData : public PlayerTemplate, public TrackerProvider {

private:

    std::vector<Check*> checks{};
    std::deque<Violation> violations{};

    int ticksExisted{};
    int entityId{};

    std::string uuid;

public:

    explicit PlayerData(std::string uuid);

    auto hasViolations() -> bool override;

    auto addViolation(Violation violation) -> void override;

    auto getViolations() -> nlohmann::json override;

    auto handlePacket(PacketEvent *event) -> void override;

    auto handlePositionUpdate(PositionUpdateEvent event) -> void override;

    auto getEntityId() -> int override;

    auto getTicksExisted() -> int override;

    auto getUuid() -> std::string override;

    auto getActionTracker() -> ActionTracker* override;

    auto getCollisionTracker() -> CollisionTracker* override;

    auto getMovementTracker() -> MovementTracker* override;

    auto getPingTracker() -> PingTracker* override;

    auto getPotionTracker() -> PotionTracker* override;
    
    ~PlayerData();
};