#pragma once

#include <vector>

#include "../tracker.h"

#include "../../data/player_template.h"
#include "../../util/potion_effect_type.h"

const std::vector<int> monitoredEffects = {
        PotionEffectType::SPEED,
        PotionEffectType::JUMP_BOOST,
        PotionEffectType::SLOWNESS
};

struct Potion {

    int effectId;
    int amplifier;
    int duration;

    int endTick{};

    short transactionId;

    bool shouldRemove{};

    Potion(int effectId, int amplifier, int duration, short transactionId) :
        effectId(effectId),
        amplifier(amplifier),
        duration(duration),
        transactionId(transactionId)
    {}

};

class PotionTracker : public Tracker {

    std::vector<Potion> pendingPotions{};
    std::vector<Potion> activePotions{};

    short lastTransaction{};

public:

    explicit PotionTracker(PlayerTemplate *data) :
        Tracker(data)
    {}

    auto handle(PacketEvent *event) -> void override {
        if (event->checkType("out_entity_effect")) {
            auto data = event->getData();

            if (data["entityId"] != playerData->getEntityId()) {
                return;
            }

            auto effect = data["effectId"];

            if (std::count(monitoredEffects.begin(), monitoredEffects.end(), effect) == 0) {
                return;
            }

            auto potion = Potion(effect, data["amplifier"], data["duration"], lastTransaction);
            pendingPotions.emplace_back(potion);
        } else if (event->checkType("in_transaction")) {
            auto data = event->getData();
            auto id = data["transactionId"];

            for(int i = 0; i < pendingPotions.size(); i++) {
                auto potion = pendingPotions.at(i);

                if (potion.transactionId == id) {
                    potion.endTick = playerData->getTicksExisted() + (20 * potion.duration);

                    activePotions.emplace_back(potion);
                    pendingPotions.erase(activePotions.begin() + i);
                }
            }
        } else if (event->isFlying()) {
            for(int i = 0; i < activePotions.size(); i++) {
                auto potion = activePotions.at(i);

                if (playerData->getTicksExisted() >= potion.endTick) {
                    activePotions.erase(activePotions.begin() + i);
                }
            }
        } else if (event->checkType("out_remove_entity_effect")) {
            auto data = event->getData();

            if (data["entityId"] != playerData->getEntityId()) {
                return;
            }

            for(int i = 0; i < activePotions.size(); i++) {
                auto potion = activePotions.at(i);

                if (potion.effectId == data["effectId"]) {
                    activePotions.erase(activePotions.begin() + i);
                }
            }
        } else if (event->checkType("in_transaction")) {
            auto data = event->getData();

            lastTransaction = data["transactionId"];
        }
    }

    auto getAmplifier(PotionEffectType type) -> int {
        for(auto &potion : activePotions) {
            if (potion.effectId == type) {
                return potion.amplifier;
            }
        }

        return 0;
    }
};