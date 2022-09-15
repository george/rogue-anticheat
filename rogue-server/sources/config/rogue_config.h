#pragma once

#include <fstream>
#include <nlohmann/json.hpp>

#include "./data/check_data.h"

class RogueConfig {

private:

    std::map<std::string, CheckData> checkData;
    int port;

public:

    RogueConfig();

    auto getPort() const -> int;

    auto getCheckData(const std::string &checkIdentifier) -> CheckData;


};