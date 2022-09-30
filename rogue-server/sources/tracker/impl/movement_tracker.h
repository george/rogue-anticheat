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

    bool shouldRemove;

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

    bool shouldRemove{};

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

    short lastTransaction;

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

            for(auto &velocity : velocities) {
                if (velocity.transactionId == id) {
                    velocity.completedTick = std::ceil(playerData->getTicksExisted() + ((velocity.getHorizontal() / 2 + 2) * 15));
                    velocity.shouldRemove = true;

                    activeVelocities.emplace_back(velocity);
                }
            }

            for(auto abilities : pendingAbilities) {
                if (abilities.transactionId == id) {
                    abilities.shouldRemove = true;

                    canFly = abilities.canFly;
                }
            }

            std::remove_if(velocities.begin(), velocities.end(), [](auto velocity){
                return velocity.shouldRemove;
            });

            std::remove_if(pendingAbilities.begin(), pendingAbilities.end(), [](auto abilities){
                return abilities.shouldRemove;
            });
        } else if (event->checkType("out_transaction")) {
            lastTransaction = event->getData()["transactionId"];
        }
    }
};