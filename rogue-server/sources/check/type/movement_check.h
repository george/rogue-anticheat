#pragma once

#include "../check.h"
#include "../../event/type/position_update_event.h"

class MovementCheck : public Check {

public:

    explicit MovementCheck(PlayerTemplate *data) :
        Check(data)
    {}

    virtual auto handle(PositionUpdateEvent *event, TrackerProvider *provider) -> void = 0;

};