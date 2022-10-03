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

    explicit Collisions(nlohmann::json json) :
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

    auto isClimbing() const -> bool {
        return climbing;
    }

    auto isInCobweb() const -> bool {
        return cobweb;
    }

    auto isInLiquid() const -> bool {
        return inLava || inWater;
    }

    auto isInLava() const -> bool {
        return inLava;
    }

    auto isInWater() const -> bool {
        return inWater;
    }

    auto isMathematicallyOnGround() const -> bool {
        return mathematicallyOnGround;
    }

    auto isUnderBlock() const -> bool {
        return underBlock;
    }

    auto isOnGround() const -> bool {
        return onGround;
    }

    auto isCollidedHorizontally() const -> bool {
        return collidedHorizontally;
    }

    auto isCollidedVertically() const -> bool {
        return collidedVertically;
    }

    auto getFrictionFactor() const -> float {
        return frictionFactor;
    }
};