#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInSettings : public Packet {

    const std::string language;
    const int view;
    const std::string chatVisibility;
    const bool enableColors;
    const int modelPartFlags;

public:

    PacketPlayInSettings(json json) :
        language(json["language"]),
        view(json["view"]),
        chatVisibility(json["chatVisibility"]),
        enableColors(json["enableColors"]),
        modelPartFlags(json["modelPartFlags"])
    {}

    auto getLanguage() -> std::string {
        return language;
    }

    auto getView() -> int {
        return view;
    }

    auto getChatVisibility() -> std::string {
        return chatVisibility;
    }

    auto getEnableColors() -> bool {
        return enableColors;
    }

    auto getModelPartFlags() -> int {
        return modelPartFlags;
    }
};