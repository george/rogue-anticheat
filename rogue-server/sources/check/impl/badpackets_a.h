#pragma once

#include "../type/packet_check.h"

class BadPacketsA : public PacketCheck {

public:

    BadPacketsA(PlayerTemplate *data) :
            PacketCheck(data)
    {}

    auto handle(PacketEvent *event, TrackerProvider *provider) -> void override {
        if (event->isFlying()) {
            auto data = event->getData();

            if (std::abs(data["pitch"].get<float>()) > 90) {
                fail({"pitch", data["pitch"]});
            }
        }
    }

    auto getType() -> std::string override {
        return "BadPackets";
    }

    auto getName() -> std::string override {
        return "A";
    }
};