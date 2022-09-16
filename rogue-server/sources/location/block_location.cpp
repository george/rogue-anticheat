#include "block_location.h"

BlockLocation::BlockLocation(nlohmann::json json) :
        posX(json["x"]),
        posY(json["y"]),
        posZ(json["z"])
{}

auto BlockLocation::getX() -> double {
    return this->posX;
}

auto BlockLocation::getY() -> double {
    return this->posY;
}

auto BlockLocation::getZ() -> double {
    return this->posZ;
}