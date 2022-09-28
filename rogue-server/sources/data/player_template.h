#pragma once

#include "nlohmann/json.hpp"

#include "../violation/violation.h"
#include "../event/type/packet_event.h"

class PlayerTemplate {

public:

    virtual auto handlePacket(PacketEvent event) -> void = 0;

    virtual auto addViolation(Violation violation) -> void = 0;

    virtual auto hasViolations() -> bool = 0;

    virtual auto getViolations() -> nlohmann::json = 0;

    virtual auto getEntityId() -> int = 0;

    virtual auto getTicksExisted() -> int = 0;

    virtual auto getUuid() -> std::string = 0;

};