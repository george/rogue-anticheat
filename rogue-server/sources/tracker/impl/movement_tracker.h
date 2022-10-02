#pragma once

#include <string>
#include <vector>

#include "../../location/vector.h"
#include "../../tracker/tracker.h"
#include "../../data/player_template.h"
#include "../../location/custom_location.h"
#include "../../event/type/position_update_event.h"

struct Abilities {

    bool canFly;
    short transactionId;

    Abilities(bool canFly, short transactionId) :
        canFly(canFly),
        transactionId(transactionId)
    {}

};

struct Velocity {

    double x;
    double y;
    double z;
    short transactionId;

    int completedTick{};

    Velocity(double x, double y, double z, short transactionId) :
        x(x),
        y(y),
        z(z),
        transactionId(transactionId)
    {}

    auto getHorizontal() const -> double {
        return std::sqrt(std::pow(x, 2) + std::pow(z, 2));
    }
};

class MovementTracker : public Tracker {

    CustomLocation *previousLocation = new CustomLocation(0, 0, 0);
    CustomLocation *currentLocation = new CustomLocation(0, 0, 0);

    bool smallMove{};
    bool teleporting{};
    bool canFly{};

    short lastTransaction{};

    std::vector<Vector> teleports{};
    std::vector<Abilities> pendingAbilities{};

    std::vector<Velocity> velocities{};
    std::vector<Velocity> activeVelocities{};

    double walkSpeed = 0.2;
    std::string gamemode;

public:

    explicit MovementTracker(PlayerTemplate *playerTemplate) :
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

                if (location.getPosX() != 0 && location.getPosY() != 0 && location.getPosZ() != 0) {
                    if (!teleporting && data["moving"] && playerData->getTicksExisted() > 20) {
                        playerData->addViolation(Violation("Tracker", "A", {
                                "offsetX", std::to_string(std::abs(location.getPosX() - currentLocation->getPosX())),
                                "offsetY", std::to_string(std::abs(location.getPosY() - currentLocation->getPosY())),
                                "offsetZ", std::to_string(std::abs(location.getPosZ() - currentLocation->getPosZ()))
                        }, 1));
                    }

                    previousLocation = currentLocation;
                    currentLocation = &location;

                    return;
                }
            }

            if (x != 0 || y != 0 || z != 0 && gamemode == "SURVIVAL" && !smallMove && !teleporting && !canFly) {
                playerData->handlePositionUpdate(PositionUpdateEvent(*currentLocation, location));
            }

            std::remove_if(velocities.begin(), velocities.end(), [this](auto velocity){
                return playerData->getTicksExisted() >= velocity.completedTick;
            });

            previousLocation = currentLocation;
            currentLocation = &location;
        } else if (event->checkType("out_position")) {
            auto data = event->getData();

            teleports.emplace_back(data["x"], data["y"], data["z"]);
        } else if (event->checkType("out_entity_velocity")) {
            auto data = event->getData();

            velocities.emplace_back(Velocity(
                    data["x"].get<int>() / 8000.0,
                    data["y"].get<int>() / 8000.0,
                    data["z"].get<int>() / 8000.0,
                            lastTransaction));
        } else if (event->checkType("out_abilities")) {
            auto data = event->getData();

            pendingAbilities.emplace_back(Abilities(data["canFly"], lastTransaction));
        } else if (event->checkType("in_transaction")) {
            auto data = event->getData();
            short id = data["transactionId"];

            for(int i = 0; i < velocities.size(); i++) {
                auto velocity = velocities.at(i);

                if (velocity.transactionId == id) {
                    velocity.completedTick = std::ceil(playerData->getTicksExisted() +
                            ((velocity.getHorizontal() / 2 + 2) * 15));

                    activeVelocities.emplace_back(velocity);
                    velocities.erase(velocities.begin() + i);
                }
            }

            for(int i = 0; i < pendingAbilities.size(); i++) {
                auto abilities = pendingAbilities.at(i);

                if (abilities.transactionId == id) {
                    canFly = abilities.canFly;
                    pendingAbilities.erase(pendingAbilities.begin() + i);
                }
            }
        } else if (event->checkType("out_transaction")) {
            lastTransaction = event->getData()["transactionId"];
        }
    }

    auto getWalkSpeed() -> double {
        return walkSpeed;
    }

    auto getCurrentLocation() -> CustomLocation* {
        return currentLocation;
    }

    auto getPreviousLocation() -> CustomLocation* {
        return previousLocation;
    }

    auto canToggleFly() const -> bool {
        return canFly;
    }

    auto isSmallMove() const -> bool {
        return smallMove;
    }

    auto getHorizontalVelocity() -> double {
        double totalVelocity = 0;

        for(auto &velocity : velocities) {
            totalVelocity += velocity.getHorizontal();
        }

        return totalVelocity;
    }

    auto getVerticalVelocity() -> double {
        double totalVelocity = 0;

        for(auto &velocity : velocities) {
            totalVelocity += velocity.y;
        }

        return totalVelocity;
    }
};