#pragma once

#include <string>
#include <utility>

class CheckData {

private:

    const std::string checkType;
    const std::string checkName;
    const std::string action;
    const int maxViolations;

public:

    auto getType() -> std::string;

    auto getName() -> std::string;

    auto getAction() -> std::string;

    auto getMaxViolations() const -> int;

    CheckData(std::string checkType, std::string checkName, std::string action,
              int maxViolations) :
              checkType(std::move(checkType)),
              checkName(std::move(checkName)),
              action(std::move(action)),
              maxViolations(maxViolations) {}
};