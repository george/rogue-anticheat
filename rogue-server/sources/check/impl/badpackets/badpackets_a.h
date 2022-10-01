#pragma once

#include "../../type/packet_check.h"

class BadPacketsA : public PacketCheck {

public:

    explicit BadPacketsA(PlayerTemplate *data) :
            PacketCheck(data)
    {}

    auto handle(PacketEvent *event, TrackerProvider *provider) -> void override {
        if (event->isFlying()) {
            auto data = event->getData();
            float pitch = data["pitch"];

            if (std::abs(pitch) > 90) {
                fail({"pitch", std::to_string(pitch)});
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