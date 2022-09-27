#pragma once

#include "../packet.h"

class PacketPlayOutPosition : public Packet {

    const double x;
    const double y;
    const double z;
    const float yaw;
    const float pitch;

public:

    PacketPlayOutPosition(json json) :
        x(json["x"]),
        y(json["y"]),
        z(json["z"]),
        yaw(json["yaw"]),
        pitch(json["pitch"])
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
};