#pragma once

class CustomLocation {

private:

    double posX;
    double posY;
    double posZ;

    float yaw{};
    float pitch{};

public:

    CustomLocation(double posX, double posY, double posZ);

    CustomLocation(double posX, double posY, double posZ, float yaw, float pitch);

    auto getPosX() const -> double;

    auto getPosY() const -> double;

    auto getPosZ() const -> double;

    auto getYaw() const -> float;

    auto getPitch() const -> float;

    auto getDistance(CustomLocation *location) const -> double;

};