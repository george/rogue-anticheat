#pragma once

#include "../packet.h"

class PacketPlayInTransaction : public Packet {

    const int windowId;
    const short transactionId;
    const bool accepted;

public:

    PacketPlayInTransaction(json json) :
        windowId(json["windowId"]),
        transactionId(json["transactionId"]),
        accepted(json["accepted"])
    {}

    auto getWindowId() -> int {
        return windowId;
    }

    auto getTransactionId() -> short {
        return transactionId;
    }

    auto isAccepted() -> bool {
        return accepted;
    }
};