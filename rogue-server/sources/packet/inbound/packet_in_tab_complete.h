#pragma once

#include <string>

#include "../packet.h"
#include "../../location/block_location.h"

class PacketPlayInTabComplete : public Packet {

    const std::string message;
    const BlockLocation targetPosition;

public:

    PacketPlayInTabComplete(json json) :
        message(json["message"]),
        targetPosition(json["targetPosition"])
    {}

    auto getMessage() -> std::string {
        return message;
    }

    auto getTargetPosition() -> BlockLocation {
        return targetPosition;
    }
};