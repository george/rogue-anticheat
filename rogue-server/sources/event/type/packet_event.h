#pragma once

#include "../event.h"

#include <utility>

class PacketEvent : Event {

private:

    const nlohmann::json json;

public:
    
    explicit PacketEvent(nlohmann::json json) :
        json(std::move(json)) {}

    auto getJson() -> nlohmann::json {
        return this->json;
    }
};