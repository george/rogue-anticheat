#pragma once

#include "../event.h"
#include "../../location/custom_location.h"

class PositionUpdateEvent : public Event {

private:

    CustomLocation previous;
    CustomLocation current;

    const double offsetXZ;
    const double offsetY;

public:

    PositionUpdateEvent(CustomLocation previous, CustomLocation current) :
        previous(previous),
        current(current),
        offsetXZ(previous.getDistance(&current)),

        offsetY(std::abs(previous.getPosY() - current.getPosY()))
    {}

    auto getPrevious() -> CustomLocation {
        return this->previous;
    }

    auto getCurrent() -> CustomLocation {
        return this->current;
    }

    auto getOffsetXZ() const -> double {
        return this->offsetXZ;
    }

    auto getOffsetY() const -> double {
        return this->offsetY;
    }
};