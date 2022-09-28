#pragma once

#include "../event.h"
#include "../../packet/packet.h"

#include <iostream>
#include <utility>

class PacketEvent : Event {

private:

    const nlohmann::json json;
    long timestamp;

    Packet *packet;

public:
    
    explicit PacketEvent(nlohmann::json *json, Packet *packet) :
        json(*json),
        packet(packet)
    {
        auto data = *json;
        timestamp = data["timestamp"];
    }

    auto getTimestamp() const -> long {
        return timestamp;
    }

    auto getJson() -> nlohmann::json {
        return this->json;
    }

    auto getPacket() -> Packet* {
        return packet;
    }

    template<typename Base>
    auto checkInstance() -> bool {
        return dynamic_cast<const Base*>(packet) != nullptr;
    }
};