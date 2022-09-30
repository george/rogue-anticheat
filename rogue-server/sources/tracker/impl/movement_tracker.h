#pragma once

#include <string>
#include <vector>

#include "../../location/vector.h"
#include "../../tracker/tracker.h"
#include "../../data/player_template.h"
#include "../../location/custom_location.h"
#include "../../event/type/position_update_event.h"

struct Velocity {

    const double x;
    const double y;
    const double z;
    const short transactionId;

    int completedTick;

    Velocity(double x, double y, double z, short transactionId) :
        x(x),
        y(y),
        z(z),
        transactionId(transactionId)
    {}

};

class MovementTracker : public Tracker {

    CustomLocation *previousLocation = new CustomLocation(0, 0, 0);
    CustomLocation *currentLocation = new CustomLocation(0, 0, 0);

    bool smallMove;
    bool teleporting;
    bool canFly;

    std::vector<Vector> teleports{};

    std::vector<Velocity> velocities{};
    std::vector<Velocity> activeVelocities{};

    double walkSpeed = 0.2;
    std::string gamemode;

public:

    MovementTracker(PlayerTemplate *playerTemplate) :
        Tracker(playerTemplate)
    {}

    auto handle(PacketEvent *event) -> void override {
        if (event->isFlying()) {
            auto data = event->getData();

            teleporting = false;
            smallMove = false;

            walkSpeed = event->getWalkSpeed();
            gamemode = event->getGamemde();

            if (!data["moving"] && !data["rotating"]) {
                smallMove = true;
            }

            double x = currentLocation->getPosX();
            double y = currentLocation->getPosY();
            double z = currentLocation->getPosZ();

            float yaw = currentLocation->getYaw();
            float pitch = currentLocation->getPitch();

            if (data["moving"]) {
                x = data["x"];
                y = data["y"];
                z = data["z"];
            }

            if (data["rotating"]) {
                yaw = data["yaw"];
                pitch = data["pitch"];
            }

            auto location = CustomLocation(x, y, z, yaw, pitch);

            for(auto teleport : teleports) {
                if (x == teleport.getPosX() && y == teleport.getPosY() && z == teleport.getPosZ()) {
                    teleporting = true;
                }
            }

            if (std::abs(location.getPosX() - currentLocation->getPosX()) > 8 ||
                std::abs(location.getPosY() - currentLocation->getPosY()) > 8 ||
                std::abs(location.getPosZ() - currentLocation->getPosZ()) > 8) {

                if (!teleporting && playerData->getTicksExisted() > 20) {
                    playerData->addViolation(Violation("Tracker", "A", {}, 1));

                    previousLocation = currentLocation;
                    currentLocation = &location;

                    return;
                }
            }

            if (x != 0 || y != 0 || z != 0 && gamemode == "SURVIVAL") {
                playerData->handlePositionUpdate(PositionUpdateEvent(*currentLocation, location));
            }

            for(const auto &velocity : velocities) {
                if (playerData->getTicksExisted() >= velocity.completedTick) {
                    std::remove(velocities.begin(), velocities.end(), velocity);
                }
            }

            previousLocation = currentLocation;
            currentLocation = &location;
        } else if ()
    }
};