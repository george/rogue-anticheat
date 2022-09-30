#include "custom_location.h"

#include <cmath>

CustomLocation::CustomLocation(double posX, double posY, double posZ) :
    posX(posX),
    posY(posY),
    posZ(posZ)
{}

CustomLocation::CustomLocation(double posX, double posY, double posZ, float yaw, float pitch) :
    posX(posX),
    posY(posY),
    posZ(posZ),
    yaw(yaw),
    pitch(pitch)
{}

auto CustomLocation::getPosX() -> double {
    return this->posX;
}

auto CustomLocation::getPosY() -> double {
    return this->posY;
}

auto CustomLocation::getPosZ() -> double {
    return this->posZ;
}

auto CustomLocation::getYaw() -> float {
    return this->yaw;
}

auto CustomLocation::getPitch() -> float {
    return this->pitch;
}

auto CustomLocation::getDistance(CustomLocation *location) -> double {
    return std::sqrt(
            std::pow(std::abs(location->getPosX() - posX), 2) +
            std::pow(std::abs(location->getPosZ() - posZ), 2)
            );
}