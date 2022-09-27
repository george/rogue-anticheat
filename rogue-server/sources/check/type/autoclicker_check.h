#pragma once

#include "./packet_check.h"
#include "../../event/type/packet_event.h"

class AutoClickerCheck : public PacketCheck {

    virtual auto handle(std::vector<long> *event) -> void = 0;

    auto handle(PacketEvent *event) -> void {

    }
};