#pragma once

#include "../event.h"

#include <iostream>
#include <utility>

class PacketEvent : Event {

private:

    const nlohmann::json json;
    long timestamp;

public:
    
    explicit PacketEvent(nlohmann::json *json) :
        json(*json)
    {
        auto data = *json;
        timestamp = data["timestamp"];
    }

    auto getTimestamp() const -> long {
        return timestamp;
    }

    auto getData() -> nlohmann::json {
        return this->json["packet"];
    }

    auto getCollisions() -> nlohmann::json {
        return this->json["collisions"];
    }

    auto getGamemde() -> nlohmann::json {
        return this->json["gamemode"];
    }

    auto getWalkSpeed() -> nlohmann::json {
        return this->json["walkSpeed"];
    }

    auto checkType(const std::string &name) -> bool {
        return json["type"] == name;
    }

    auto isFlying() -> bool {
        return checkType("in_flying") || checkType("in_position") ||
                checkType("in_look") || checkType("in_position_look");
    }

};