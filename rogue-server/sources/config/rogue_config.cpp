#include "rogue_config.h"

#include <iostream>

using nlohmann::json;

RogueConfig::RogueConfig() {
    std::ifstream ifstream("config.json");
    json data = json::parse(ifstream);

    this->port = data["port"];

    std::for_each(data["checks"].begin(), data["checks"].end(), [](auto check) {
        std::cout << check << std::endl;
    });
}

auto RogueConfig::getPort() const -> int {
    return this->port;
}

auto RogueConfig::getCheckData(const std::string &checkIdentifier) -> CheckData {
    return this->checkData.find(checkIdentifier)->second;
}