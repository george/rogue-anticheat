#pragma once

#include "../packet.h"

class PacketPlayOutEntityTeleport : public Packet {

    const int entityId;
    const int posX;
    const int posY;
    const int posZ;
    const char yaw;
    const char pitch;

public:

    PacketPlayOutEntityTeleport(json json) :
        entityId(json["json"]),
        posX(json["posX"]),
        posY(json["posY"]),
        posZ(json["posZ"]),
        yaw((int) json["yaw"]),
        pitch((int) json["pitch"])
    {}

    auto getEntityId() -> int {
        return entityId;
    }

    auto getPosX() -> int {
        return posX;
    }

    auto getPosY() -> int {
        return posY;
    }

    auto getPosZ() -> int {
        return posZ;
    }

    auto getYaw() -> char {
        return yaw;
    }

    auto getPitch() -> char {
        return pitch;
    }
};