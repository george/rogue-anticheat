#pragma once

#include "../tracker.h"
#include "../../data/player_template.h"
#include "../../util/collisions.h"

class CollisionTracker : public Tracker {

    Collisions *currentCollisions;
    Collisions *previousCollisions;

public:

    explicit CollisionTracker(PlayerTemplate *playerData)
            : Tracker(playerData)
    {
        currentCollisions = new Collisions();
        previousCollisions = new Collisions();
    }

    auto handle(PacketEvent *event) -> void override {
        if (event->isFlying()) {
            auto collisions = new Collisions(event->getCollisions());

            delete previousCollisions;

            previousCollisions = currentCollisions;
            currentCollisions = collisions;
        }
    }

    auto getCurrentCollisions() -> Collisions* {
        return currentCollisions;
    }

    auto getPreviousCollisions() -> Collisions* {
        return previousCollisions;
    }
};