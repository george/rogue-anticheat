#pragma once

#include "../packet.h"

class PacketPlayOutAbilities : public Packet {

    const bool invulnerable;
    const bool flying;
    const bool ableToFly;
    const bool instantBuild;

    const float flySpeed;
    const float walkSpeed;

public:

    PacketPlayOutAbilities(json json) :
        invulnerable(json["invulnerable"]),
        flying(json["flying"]),
        ableToFly(json["canFly"]),
        instantBuild(json["canInstantlyBuild"]),
        flySpeed(json["flySpeed"]),
        walkSpeed(json["walkSpeed"])
    {}

    auto isInvulnerable() -> bool {
        return invulnerable;
    }

    auto isFlying() -> bool {
        return flying;
    }

    auto canFly() -> bool {
        return ableToFly;
    }

    auto canInstantlyBuild() -> bool {
        return instantBuild;
    }

    auto getFlySpeed() -> float {
        return flySpeed;
    }

    auto getWalkSpeed() -> float {
        return walkSpeed;
    }
};