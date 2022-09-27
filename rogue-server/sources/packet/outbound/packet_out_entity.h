#pragma once

#include "../packet.h"

class PacketPlayOutEntity : public Packet {

    const int entityId;
    const char posX;
    const char posY;
    const char posZ;
    const char yaw;
    const char pitch;

public:

    PacketPlayOutEntity(json json) :
        entityId(json["entityId"]),
        posX((int) json["posX"]),
        posY((int) json["posY"]),
        posZ((int) json["posZ"]),
        yaw((int) json["yaw"]),
        pitch((int) json["pitch"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getPosX() -> char {
        return posX;
    }

    auto getPosY() -> char {
        return posY;
    }

    auto getPosZ() -> char {
        return posZ;
    }

    auto getYaw() -> char {
        return yaw;
    }

    auto getPitch() -> char {
        return pitch;
    }

};