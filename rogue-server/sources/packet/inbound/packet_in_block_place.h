#pragma once

#include "../packet.h"
#include "../../location/block_location.h"

class PacketPlayInBlockPlace : public Packet {

    const BlockLocation blockLocation;
    const int face;
    const std::string material;

    const float facingX;
    const float facingY;
    const float facingZ;

public:

    PacketPlayInBlockPlace(json json) :
            blockLocation(BlockLocation(json["blockPosition"])),
            face(json["face"]),
            material(json["itemStack"]),
            facingX(json["facingX"]),
            facingY(json["facingY"]),
            facingZ(json["facingZ"])
    {}

    auto getBlockPosition() -> BlockLocation {
        return blockLocation;
    }

    auto getFace() -> int {
        return face;
    }

    auto getMaterial() -> std::string {
        return material;
    }

    auto getFacingX() -> float {
        return facingX;
    }

    auto getFacingY() -> float {
        return facingY;
    }

    auto getFacingZ() -> float {
        return facingZ;
    }
};