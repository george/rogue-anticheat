#pragma once

#include "../event/type/packet_event.h"

class Tracker {

public:

    virtual auto handle(PacketEvent *event) -> void = 0;

};