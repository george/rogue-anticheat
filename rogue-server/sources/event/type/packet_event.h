#pragma once

#include "../event.h"
#include "../../packet/packet.h"

#include <utility>

class PacketEvent : Event {

private:

    const nlohmann::json json;
    const long timestamp;

    Packet *packet;

public:
    
    explicit PacketEvent(nlohmann::json json, Packet *packet) :
        json(std::move(json)),
        timestamp(json["timestamp"]),
        packet(packet)
    {}

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