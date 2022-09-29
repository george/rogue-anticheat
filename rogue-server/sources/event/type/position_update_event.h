    #pragma once

#include <cmath>

#include "../event.h"
#include "../../location/custom_location.h"

class PositionUpdateEvent : public Event {

private:

    CustomLocation previous;
    CustomLocation current;

    const double offsetX;
    const double offsetY;

public:

    PositionUpdateEvent(CustomLocation previous, CustomLocation current) :
        previous(previous),
        current(current),
        offsetX(
                std::sqrt(
                        std::pow(std::abs(previous.getPosX() - current.getPosX()), 2) +
                        std::pow(std::abs(previous.getPosZ() - current.getPosZ()), 2)
                 )
        ),

        offsetY(std::abs(previous.getPosY() - current.getPosY()))
    {}

    auto getPrevious() -> CustomLocation {
        return this->previous;
    }

    auto getCurrent() -> CustomLocation {
        return this->current;
    }

    auto getOffsetX() -> double {
        return this->offsetX;
    }

    auto getOffsetY() -> double {
        return this->offsetY;
    }
};