#include "player_data.h"

#include <utility>

PlayerData::PlayerData(std::string uuid)
    : uuid(std::move(uuid)) {


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
    for(const auto &tracker : this->trackers) {
        tracker->handle(&event);
    }
}