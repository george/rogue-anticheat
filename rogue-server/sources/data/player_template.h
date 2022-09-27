#pragma once

#include "../event/type/packet_event.h"
#include "nlohmann/json.hpp"

class PlayerTemplate {

public:

    virtual auto handlePacket(PacketEvent event) -> void = 0;

    virtual auto hasViolations() -> bool = 0;

    virtual auto getViolations() -> nlohmann::json = 0;

    virtual auto getEntityId() -> int = 0;

    virtual auto getTicksExisted() -> int = 0;

    virtual auto getUuid() -> std::string = 0;

};