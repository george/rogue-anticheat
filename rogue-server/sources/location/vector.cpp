#include "vector.h"

Vector::Vector() :
        posX(0),
        posY(0),
        posZ(0)
{}

Vector::Vector(double posX, double posY, double posZ) :
        posX(posX),
        posY(posY),
        posZ(posZ)
{}

auto Vector::add(Vector *other) -> Vector* {
    posX += other->posX;
    posY += other->posY;
    posZ += other->posZ;
    return this;
}

auto Vector::multiply(double scalar) -> Vector* {
    posX *= scalar;
    posY *= scalar;
    posZ *= scalar;
    return this;
}

auto Vector::getPosX() -> double {
    return posX;
}

auto Vector::getPosY() -> double {
    return posY;
}

auto Vector::getPosZ() -> double {
    return posZ;
}