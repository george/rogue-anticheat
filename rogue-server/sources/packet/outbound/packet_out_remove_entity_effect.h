#pragma once

#include "../packet.h"

class PacketPlayOutRemoveEntityEffect : public Packet {

    const int entityId;
    const char effectId;

public:

    PacketPlayOutRemoveEntityEffect(json json) :
        entityId(json["entityId"]),
        effectId((int) json["effectId"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getEffectId() -> char {
        return effectId;
    }
};