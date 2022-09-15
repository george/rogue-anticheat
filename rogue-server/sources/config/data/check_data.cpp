#include "check_data.h"

auto CheckData::getName() -> std::string {
    return this->checkName;
}

auto CheckData::getType() -> std::string {
    return this->checkType;
}

auto CheckData::getAction() -> std::string {
    return this->action;
}

auto CheckData::getMaxViolations() const -> int {
    return this->maxViolations;
}