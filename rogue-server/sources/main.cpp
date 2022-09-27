#define CROW_MAIN

#include "app/rogue_app.h"

#include <nlohmann/json.hpp>
#include <iostream>

using nlohmann::json;

auto log(const std::string &content) -> void {
    std::cout << content << std::endl;
}

auto main() -> int {
    crow::SimpleApp app = crow::SimpleApp();

    CROW_ROUTE(app,"/quit/<string>")([](std::string uuid) {
        rogue_app::removePlayerData(uuid);
        return crow::response(200);
    });

    CROW_ROUTE(app,"/players/<string>").methods("POST"_method)
    ([&](const crow::request &req, std::string uuid) {
        auto content = req.body;
        auto parsedContent = json(content);

        auto data = rogue_app::getPlayerData(uuid);

        data->handlePacket(PacketEvent(parsedContent));

        if (data->hasViolations()) {
            return crow::response(to_string(data->getViolations()));
        }

        return crow::response("{violations: []}");
    });

    app.port(rogue_app::config.getPort())
        .loglevel(crow::LogLevel::Warning)
        .bindaddr(rogue_app::config.getHostname())
        .multithreaded()
        .run();
}