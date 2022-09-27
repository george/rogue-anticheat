#pragma once

#include "../packet.h"

class PacketPlayInFlying : public Packet {

    const double x;
    const double y;
    const double z;

    const float yaw;
    const float pitch;

    const bool onGround;
    const bool moving;
    const bool rotating;

public:

    PacketPlayInFlying(json json) :
        x(json["x"]),
        y(json["y"]),
        z(json["z"]),
        yaw(json["yaw"]),
        pitch(json["pitch"]),
        onGround(json["onGround"]),
        moving(json["moving"]),
        rotating(json["rotating"])
    {}

    auto getX() -> double {
        return x;
    }

    auto getY() -> double {
        return y;
    }

    auto getZ() -> double {
        return z;
    }

    auto getYaw() -> float {
        return yaw;
    }

    auto getPitch() -> float {
        return pitch;
    }

    auto isOnGround() -> bool {
        return onGround;
    }

    auto isMoving() -> bool {
        return moving;
    }

    auto isRotating() -> bool {
        return rotating;
    }
};