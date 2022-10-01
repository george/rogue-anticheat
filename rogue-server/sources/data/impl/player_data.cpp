#include "player_data.h"

#include <utility>
#include <iostream>

#include "../../check/type/movement_check.h"

#include "../../check/impl/badpackets/badpackets_a.h"

ActionTracker *actionTracker;
CollisionTracker *collisionTracker;
MovementTracker *movementTracker;
PingTracker *pingTracker;

PlayerData::PlayerData(std::string uuid)
    : uuid(std::move(uuid)) {

    actionTracker = new ActionTracker(this);
    collisionTracker = new CollisionTracker(this);
    movementTracker = new MovementTracker(this);
    pingTracker = new PingTracker(this);

    checks.push_back(new BadPacketsA(this));
}

auto PlayerData::addViolation(Violation violation) -> void {
    violations.push_back(violation);
}

auto PlayerData::getEntityId() -> int {
    return this->entityId;
}

auto PlayerData::getTicksExisted() -> int {
    return this->ticksExisted;
}

auto PlayerData::getUuid() -> std::string {
    return this->uuid;
}

auto PlayerData::hasViolations() -> bool {
    return !this->violations.empty();
}

auto PlayerData::getActionTracker() -> ActionTracker* {
    return actionTracker;
}

auto PlayerData::getCollisionTracker() -> CollisionTracker * {
    return collisionTracker;
}

auto PlayerData::getMovementTracker() -> MovementTracker* {
    return movementTracker;
}

auto PlayerData::getPingTracker() -> PingTracker* {
    return pingTracker;
}
auto PlayerData::getViolations() -> nlohmann::json {
    nlohmann::json json = nlohmann::json::array();

    for(auto &violation : violations) {
        json.push_back(violation.toJsonObject());
    }

    violations.clear();

    return json;
}

auto PlayerData::handlePacket(PacketEvent event) -> void {
    if (event.isFlying()) {
        ++ticksExisted;
    }

    actionTracker->handle(&event);
    collisionTracker->handle(&event);
    movementTracker->handle(&event);
    pingTracker->handle(&event);

    for(const auto &check : this->checks) {
        if (dynamic_cast<PacketCheck*>(check) != nullptr) {
            ((PacketCheck*) check)->handle(&event, this);
        }
    }
}

auto PlayerData::handlePositionUpdate(PositionUpdateEvent event) -> void {
    for(const auto &check : this->checks) {
        if (dynamic_cast<MovementCheck*>(check) != nullptr) {
            ((MovementCheck*) check)->handle(&event, this);
        }
    }
}

PlayerData::~PlayerData() {
    for(const auto &check : checks) {
        delete &check;
    }

    delete actionTracker;
    delete collisionTracker;
    delete movementTracker;
    delete pingTracker;
}