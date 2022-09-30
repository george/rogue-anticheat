#pragma once

#include "nlohmann/json.hpp"

class Collisions {

private:

    const bool climbing;
    const bool cobweb;

    const bool inLava;
    const bool inWater;

    const bool mathematicallyOnGround;
    const bool underBlock;
    const bool onGround;

    const bool collidedVertically;
    const bool collidedHorizontally;

    const float frictionFactor;

public:

    Collisions() :
        climbing(false),
        cobweb(false),
        inLava(false),
        inWater(false),
        mathematicallyOnGround(true),
        underBlock(false),
        collidedHorizontally(false),
        collidedVertically(false),
        frictionFactor(0.91),
        onGround(true)
    {}

    Collisions(nlohmann::json json) :
        climbing(json["climbing"]),
        cobweb(json["cobweb"]),
        inLava(json["lava"]),
        inWater(json["water"]),
        mathematicallyOnGround(json["mathematicallyOnGround"]),
        underBlock(json["underBlock"]),
        collidedHorizontally(json["collidedHorizontally"]),
        collidedVertically(json["collidedVertically"]),
        frictionFactor(json["frictionFactor"]),
        onGround(json["onGround"])
    {}

    auto isClimbing() -> bool {
        return climbing;
    }

    auto isInCobweb() -> bool {
        return cobweb;
    }

    auto isInLiquid() -> bool {
        return inLava || inWater;
    }

    auto isInLava() -> bool {
        return inLava;
    }

    auto isInWater() -> bool {
        return inWater;
    }

    auto isMathematicallyOnGround() -> bool {
        return mathematicallyOnGround;
    }

    auto isUnderBlock() -> bool {
        return underBlock;
    }

    auto isOnGround() -> bool {
        return onGround;
    }

    auto isCollidedHorizontally() -> bool {
        return collidedHorizontally;
    }

    auto isCollidedVertically() -> bool {
        return collidedVertically;
    }

    auto getFrictionFactor() -> float {
        return frictionFactor;
    }
};