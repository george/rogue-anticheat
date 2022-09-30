#include "player_data.h"

#include "../../check/impl/badpackets_a.h"
#include "../../tracker/impl/action_tracker.h"

#include <utility>
#include <iostream>

PlayerData::PlayerData(std::string uuid)
    : uuid(std::move(uuid)) {

    trackers.push_back(new ActionTracker(this));

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

auto PlayerData::getViolations() -> nlohmann::json {
    nlohmann::json json = nlohmann::json::array();

    while (!violations.empty()) {
        Violation violation = violations.front();
        json.push_back(violation.toJsonObject());

        violations.pop_front();
    }

    return json;
}

auto PlayerData::handlePacket(PacketEvent event) -> void {
    if (event.isFlying()) {
        ++ticksExisted;
    }

    for(const auto &tracker : this->trackers) {
        tracker->handle(&event);
    }

    for(const auto &check : this->checks) {
        if (dynamic_cast<PacketCheck*>(check) != nullptr) {
            ((PacketCheck*) check)->handle(&event);
        }
    }
}

PlayerData::~PlayerData() {
    for(const auto &check : checks) {
        delete &check;
    }

    for(const auto &tracker : trackers) {
        delete &tracker;
    }
}