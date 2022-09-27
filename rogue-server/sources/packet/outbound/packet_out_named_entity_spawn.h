#pragma once

#include "../packet.h"

class PacketPlayOutNamedEntitySpawn : public Packet {

    const int entityId;
    const int posX;
    const int posY;
    const int posZ;

public:

    PacketPlayOutNamedEntitySpawn(json json) :
        entityId(json["entityId"]),
        posX(json["posX"]),
        posY(json["posY"]),
        posZ(json["posZ"])
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
};