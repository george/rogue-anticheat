#pragma once

#include "../event.h"
#include "../../packet/packet.h"

#include <utility>

class PacketEvent : Event {

private:

    const nlohmann::json json;
    const Packet packet;

public:
    
    explicit PacketEvent(nlohmann::json json, Packet packet) :
        json(std::move(json)),
        packet(packet)
    {}

    auto getJson() -> nlohmann::json {
        return this->json;
    }
};