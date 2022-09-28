#pragma once

#include <string>
#include <vector>

#include "nlohmann/json.hpp"

class Violation {

private:

    const std::string name;
    const std::string type;

    const std::vector<std::string> *args;
    const int violations;

public:

    Violation(std::string name, std::string type, std::vector<std::string> *args, int violations) :
        name(std::move(name)),
        type(std::move(type)),
        args(args),
        violations(violations)
    {}

    auto getName() -> std::string;

    auto getType() -> std::string;

    auto getArgs() -> std::vector<std::string>;

    auto getViolations() -> int;

    auto toJsonObject() -> nlohmann::json;

    ~Violation();
};