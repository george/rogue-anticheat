#pragma once

class Vector {

    double posX;
    double posY;
    double posZ;

public:

    Vector();

    Vector(double posX, double posY, double posZ);

    auto add(Vector *other) -> Vector*;

    auto multiply(double scalar) -> Vector*;

    auto getPosX() -> double;

    auto getPosY() -> double;

    auto getPosZ() -> double;
};