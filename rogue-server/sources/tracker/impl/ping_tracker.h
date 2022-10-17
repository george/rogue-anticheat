#pragma once

#include <map>
#include <mutex>

#include "../tracker.h"
#include "../../data/player_template.h"

class PingTracker : public Tracker {

    std::map<short, long> transactionMap{};
    std::mutex mutex{};

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

            mutex.lock();
            transactionMap.erase(transactionMap.find(transactionId));
            mutex.unlock();
        } else if (event->checkType("out_transaction")) {
            auto data = event->getData();
            auto transactionId = data["transactionId"];

            lastTransaction = transactionId;

            mutex.lock();
            transactionMap.insert(std::make_pair(transactionId, timestamp));
            mutex.unlock();
        } else if (event->isFlying()) {
            for(const auto &pair : transactionMap) {
                if (timestamp - pair.second > 3000) {
                    playerData->addViolation(Violation("Tracker", "B", {
                        "transactionId", std::to_string(pair.first),
                        "delay", std::to_string(timestamp - pair.second)
                    }, 1));
                }
            }
        }
    }
};