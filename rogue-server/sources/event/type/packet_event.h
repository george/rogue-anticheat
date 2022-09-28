#pragma once

#include "../event.h"
#include "../../packet/packet.h"
#include "../../packet/inbound/packet_in_position.h"
#include "../../packet/inbound/packet_in_position_look.h"
#include "../../packet/inbound/packet_in_look.h"

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

    auto isPosition() -> bool {
        return checkInstance<PacketPlayInPosition>() || checkInstance<PacketPlayInPositionLook>();
    }

    auto isRotating() -> bool {
        return checkInstance<PacketPlayInPositionLook>() || checkInstance<PacketPlayInLook>();
    }
};