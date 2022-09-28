#include "violation.h"

#include "nlohmann/json.hpp"

#include "../app/rogue_app.h"

auto Violation::getType() -> std::string {
    return this->type;
}

auto Violation::getName() -> std::string {
    return this->name;
}

auto Violation::getViolations() -> int {
    return this->violations;
}

auto Violation::getArgs() -> std::vector<std::string> {
    return *this->args;
}

auto Violation::toJsonObject() -> nlohmann::json {
    CheckData checkData = rogue_app::getCheckData(type + name);
    nlohmann::json data = nlohmann::json::array();

    for(int i = 0; i < args->size() / 2; i++) {
        std::string name = args->at(i * 2);
        std::string violationFlagData = args->at(i * 2 + 1);

        data.push_back({
            {"name", name},
            {"data", violationFlagData}
        });
    }

    nlohmann::json json = {
            {"action", violations > checkData.getMaxViolations() ? "ban" : checkData.getAction()},
            {"checkName", checkData.getName()},
            {"checkType", checkData.getType()},
            {"violations", violations},
            {"data", data},
            {"maxViolations", checkData.getMaxViolations()}
    };

    return json;
}

Violation::~Violation() {
    delete args;
}