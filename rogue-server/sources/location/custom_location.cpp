#include "custom_location.h"

#include <iostream>
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

auto CustomLocation::getPosX() const -> double {
    return this->posX;
}

auto CustomLocation::getPosY() const -> double {
    return this->posY;
}

auto CustomLocation::getPosZ() const -> double {
    return this->posZ;
}

auto CustomLocation::getYaw() const -> float {
    return this->yaw;
}

auto CustomLocation::getPitch() const -> float {
    return this->pitch;
}

auto CustomLocation::getDistance(CustomLocation *location) const -> double {
    return std::sqrt(
            std::pow(std::abs(location->getPosX() - posX), 2) +
            std::pow(std::abs(location->getPosZ() - posZ), 2)
            );
}