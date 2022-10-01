#pragma once

#include "../../type/movement_check.h"

class SpeedA : public MovementCheck {

public:

    explicit SpeedA(PlayerTemplate *data) :
            MovementCheck(data)
    {}

    auto handle(PositionUpdateEvent *event, TrackerProvider *provider) -> void override {
    }

    auto getType() -> std::string override {
        return "Speed";
    }

    auto getName() -> std::string override {
        return "A";
    }
};