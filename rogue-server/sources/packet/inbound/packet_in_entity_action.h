#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInEntityAction : public Packet {

    const int entityId;
    const std::string action;
    const int auxId;

public:

    PacketPlayInEntityAction(json json) :
        entityId(json["entityId"]),
        action(json["playerAction"]),
        auxId(json["auxId"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getAction() -> std::string {
        return action;
    }

    auto getAuxId() -> int {
        return auxId;
    }
};