#pragma once

#include "../tracker.h"
#include "../../data/player_template.h"

class ActionTracker : public Tracker {

    PlayerTemplate *playerData;

    long lastAttack{};
    bool sneaking{};
    bool digging{};

public:

    explicit ActionTracker(PlayerTemplate *playerData)
        : playerData(playerData)
    {}

    auto handle(PacketEvent *event) -> void override {
        if (event->checkType("in_use_entity")) {
            lastAttack = playerData->getTicksExisted();
        } else if (event->checkType("in_entity_action")) {
            auto packet = event->getData();
            auto action = packet["playerAction"];

            if (action == "START_SNEAKING") {
                sneaking = true;
            } else if (action == "STOP_SNEAKING") {
                sneaking = false;
            }
        } else if (event->checkType("in_dig")) {
            auto packet = event->getData();
            auto action = packet["digType"];

            if (action == "START_DESTROY_BLOCK") {
                digging = true;
            } else if (action == "STOP_DESTROY_BLOCK" || action == "ABORT_DESTROY_BLOCK") {
                digging = false;
            }
        }
    }

    auto isAttacking() -> long {
        return playerData->getTicksExisted() - lastAttack < 1;
    }

    auto isDigging() const -> bool {
        return digging;
    }

    auto isSneaking() const -> bool {
        return sneaking;
    }
};