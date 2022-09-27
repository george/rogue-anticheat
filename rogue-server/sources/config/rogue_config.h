#pragma once

#include <fstream>
#include <nlohmann/json.hpp>

#include "./data/check_data.h"

class RogueConfig {

private:

    std::map<std::string, CheckData> checkData;
    std::string hostname;

    int port;
    int threads;

public:

    RogueConfig();

    auto getPort() const -> int;

    auto getThreads() const -> int;

    auto getHostname() const -> std::string;

    auto getCheckData(const std::string &checkIdentifier) -> CheckData;

};