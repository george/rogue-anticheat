#pragma once

#include "../event/type/packet_event.h"

class Tracker {

protected:

    PlayerTemplate *playerData;

public:

    explicit Tracker(PlayerTemplate *playerData) :
            playerData(playerData)
    {}

    virtual auto handle(PacketEvent *event) -> void = 0;

};