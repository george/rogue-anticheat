#pragma once

#include "../check.h"
#include "../../event/type/packet_event.h"

class PacketCheck : public Check {

public:

    PacketCheck(PlayerTemplate *data) :
        Check(data)
    {}

    virtual auto handle(PacketEvent *event, TrackerProvider *provider) -> void = 0;

};