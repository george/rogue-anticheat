#include "rogue_config.h"

#include <iostream>

using nlohmann::json;

RogueConfig::RogueConfig() {
    std::ifstream file("config.json");
    json data = json::parse(file);

    this->port = data["port"];
    this->hostname = data["hostname"];

    std::for_each(data["checks"].begin(), data["checks"].end(), [this](auto check) {
        std::string checkType = check["check_type"];
        std::string checkName = check["check_name"];

        std::string action = check["action"];
        int maxViolations = check["max_violations"];

        std::string checkTitle = checkType + checkName;
        CheckData data = CheckData(checkType, checkName, action, maxViolations);

        std::pair<std::string, CheckData> pair = std::make_pair(checkType + checkName, data);

        std::cout << "Registered check " << checkType << " (Type " << checkName << ")!" << std::endl;

        this->checkData.insert(pair);
    });
}

auto RogueConfig::getPort() const -> int {
    return this->port;
}

auto RogueConfig::getCheckData(const std::string &checkIdentifier) -> CheckData {
    return this->checkData.find(checkIdentifier)->second;
}

auto RogueConfig::getHostname() const -> std::string {
    return this->hostname;
}