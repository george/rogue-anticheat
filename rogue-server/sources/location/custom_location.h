#pragma once

class CustomLocation {

private:

    double posX;
    double posY;
    double posZ;

    float yaw;
    float pitch;

public:

    CustomLocation(double posX, double posY, double posZ);

    CustomLocation(double posX, double posY, double posZ, float yaw, float pitch);

    auto getPosX() -> double;

    auto getPosY() -> double;

    auto getPosZ() -> double;

    auto getYaw() -> float;

    auto getPitch() -> float;

    auto getDistance(CustomLocation *location) -> double;

};