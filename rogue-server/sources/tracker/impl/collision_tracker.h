#pragma once

#include <mutex>

#include "../tracker.h"
#include "../../data/player_template.h"
#include "../../util/collisions.h"

class CollisionTracker : public Tracker {

    std::mutex mutex{};

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

            mutex.lock();
            delete previousCollisions;
            mutex.unlock();

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