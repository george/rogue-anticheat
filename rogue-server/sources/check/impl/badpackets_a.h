#pragma once

#include "../type/packet_check.h"

class BadPacketsA : public PacketCheck {

public:

    BadPacketsA(PlayerTemplate *data) :
            PacketCheck(data)
    {}

    auto handle(PacketEvent *event) -> void override {
        if (event->isFlying()) {
            if (std::abs(event->getData()["pitch"].get<float>()) > 90) {
                fail({"pitch", event->getData()["pitch"]});
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