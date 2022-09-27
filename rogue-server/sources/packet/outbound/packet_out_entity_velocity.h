#pragma once

#include "../packet.h"

class PacketPlayOutEntityVelocity : public Packet {

    const int entityId;
    const int velocityX;
    const int velocityY;
    const int velocityZ;

public:

    PacketPlayOutEntityVelocity(json json) :
        entityId(json["entityId"]),
        velocityX(json["velocityX"]),
        velocityY(json["velocityY"]),
        velocityZ(json["velocityZ"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getVelocityX() -> int {
        return velocityX;
    }

    auto getVelocityY() -> int {
        return velocityY;
    }

    auto getVelocityZ() -> int {
        return velocityZ;
    }
};