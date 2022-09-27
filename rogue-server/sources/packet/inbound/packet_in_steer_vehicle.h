#pragma once

#include "../packet.h"

class PacketPlayInSteerVehicle : public Packet {

    const float strafeSpeed;
    const float forwardSpeed;

    const bool jumping;
    const bool sneaking;

public:

    PacketPlayInSteerVehicle(json json) :
        strafeSpeed(json["strafeSpeed"]),
        forwardSpeed(json["forwardsSpeed"]),
        jumping(json["jumping"]),
        sneaking(json["sneaking"])
    {}

    auto getStrafeSpeed() -> float {
        return strafeSpeed;
    }

    auto getForwardSpeed() -> float {
        return forwardSpeed;
    }

    auto isJumping() -> bool {
        return jumping;
    }

    auto isSneaking() -> bool {
        return sneaking;
    }
};