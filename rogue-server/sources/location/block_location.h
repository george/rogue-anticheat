#pragma once

#include "nlohmann/json.hpp"

class BlockLocation {

    const double posX;
    const double posY;
    const double posZ;

    BlockLocation(nlohmann::json json);

    auto getX() -> double;

    auto getY() -> double;

    auto getZ() -> double;

};