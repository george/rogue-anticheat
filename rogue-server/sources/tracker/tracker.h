#pragma once

#include "../event/type/packet_event.h"

class Tracker {

protected:

    PlayerTemplate *playerData;

    explicit Tracker(PlayerTemplate *playerData) :
        playerData(playerData)
    {}

public:

    virtual auto handle(PacketEvent *event) -> void = 0;

};