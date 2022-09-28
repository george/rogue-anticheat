#pragma once

#include "../tracker.h"
#include "../../data/player_template.h"
#include "../../packet/inbound/packet_in_use_entity.h"
#include "../../packet/inbound/packet_in_entity_action.h"
#include "../../packet/inbound/packet_in_block_dig.h"

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
        if (event->checkInstance<PacketPlayInUseEntity>()) {
            lastAttack = playerData->getTicksExisted();
        } else if (event->checkInstance<PacketPlayInEntityAction>()) {
            auto packet = dynamic_cast<PacketPlayInEntityAction*>(event->getPacket());
            auto action = packet->getAction();

            if (action == "START_SNEAKING") {
                sneaking = true;
            } else if (action == "STOP_SNEAKING") {
                sneaking = false;
            }
        } else if (event->checkInstance<PacketPlayInBlockDig>()) {
            auto packet = dynamic_cast<PacketPlayInEntityAction*>(event->getPacket());
            auto action = packet->getAction();

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