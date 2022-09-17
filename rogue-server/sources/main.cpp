#define CROW_MAIN

#include "app/rogue_app.h"

#include <nlohmann/json.hpp>
#include <iostream>

auto log(const std::string &content) -> void {
    std::cout << content << std::endl;
}

auto main() -> int {
    log("Initializing Rogue server!");

    std::unique_ptr<crow::SimpleApp> app = rogue_app::initialize();

    log("Starting application on port " + std::to_string(rogue_app::config.getPort()));

    app->port(rogue_app::config.getPort())
        .bindaddr(rogue_app::config.getHostname())
        .multithreaded()
        .run();
}