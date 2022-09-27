#pragma once

#include "../packet.h"

class PacketPlayOutEntityEffect : public Packet {

    const int entityId;
    const char effectId;
    const char amplifier;
    const int duration;

public:

    PacketPlayOutEntityEffect(json json) :
        entityId(json["entityId"]),
        effectId((int) json["effectId"]),
        amplifier((int) json["amplifier"]),
        duration(json["duration"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getEffectId() -> char {
        return effectId;
    }

    auto getAmplifier() -> char {
        return amplifier;
    }

    auto getDuration() -> char {
        return duration;
    }
};