#pragma once

#include <map>

#include "../tracker.h"
#include "../../data/player_template.h"

class PingTracker : public Tracker {

    std::map<short, long> transactionMap{};

    short lastTransaction{};
    long lastPing{};

public:

    explicit PingTracker(PlayerTemplate *playerTemplate) :
        Tracker(playerTemplate)
    {}

    auto handle(PacketEvent *event) -> void override {
        long timestamp = event->getTimestamp();

        if (event->checkType("in_transaction")) {
            auto data = event->getData();
            auto transactionId = data["transactionId"];

            if (transactionMap.count(transactionId) == 0) {
                return;
            }

            lastPing = timestamp - transactionMap.find(transactionId)->second;
            transactionMap.erase(transactionId);
        } else if (event->checkType("out_transaction")) {
            auto data = event->getData();
            auto transactionId = data["transactionId"];

            lastTransaction = transactionId;
            transactionMap.insert(std::make_pair(transactionId, timestamp));
        } else if (event->isFlying()) {
            for(const auto &pair : transactionMap) {
                if (timestamp - pair.second > 3000) {
                    playerData->addViolation(Violation("Tracker", "B", {}, 1));
                }
            }
        }
    }
};