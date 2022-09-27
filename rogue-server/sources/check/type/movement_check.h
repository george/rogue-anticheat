#pragma once

#include "../check.h"
#include "../../event/type/position_update_event.h"

class MovementCheck : public Check {

public:

    virtual auto handle(PositionUpdateEvent *event) -> void = 0;

};