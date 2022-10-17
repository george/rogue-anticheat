#pragma once

#include "../../type/movement_check.h"

class SpeedA : public MovementCheck {

    float lastFriction = 0.91;
    double lastOffsetXZ = 0.026;

public:

    explicit SpeedA(PlayerTemplate *data) :
            MovementCheck(data)
    {}

    auto handle(PositionUpdateEvent *event, TrackerProvider *provider) -> void override {
        double offsetXZ = event->getOffsetXZ();
        double offsetY = event->getOffsetY();

        double movementSpeed = provider->getMovementTracker()->getWalkSpeed() / 2;
        double jumpHeight = 0.42F + (provider->getPotionTracker()->getAmplifier(PotionEffectType::JUMP_BOOST) * 0.1);

        if (provider->getCollisionTracker()->getPreviousCollisions()->isOnGround()) {
            movementSpeed *= 1.3;

            lastFriction *= 0.91F;
            movementSpeed *= 0.16277136 / std::pow(lastFriction, 3);

            if (provider->getCollisionTracker()->getPreviousCollisions()->isUnderBlock() || (0.001 < offsetY < jumpHeight)) {
                movementSpeed += 0.2;
            }
        } else {
            movementSpeed = 0.026;
            lastFriction = 0.91F;
        }

        movementSpeed += provider->getMovementTracker()->getHorizontalVelocity();
        movementSpeed += (0.2 * provider->getPotionTracker()->getAmplifier(PotionEffectType::SPEED));
        movementSpeed -= (0.15 * provider->getPotionTracker()->getAmplifier(PotionEffectType::SLOWNESS));

        double ratio = (offsetXZ - lastOffsetXZ) / movementSpeed;
//        std::cout << std::to_string(ratio) << std::endl;

        lastOffsetXZ = offsetXZ * lastFriction;
        lastFriction = provider->getCollisionTracker()->getPreviousCollisions()->getFrictionFactor();
    }

    auto getType() -> std::string override {
        return "Speed";
    }

    auto getName() -> std::string override {
        return "A";
    }
};