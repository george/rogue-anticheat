package org.hostile.anticheat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.SneakyThrows;
import org.hostile.anticheat.config.ServerConfiguration;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.manager.PlayerDataManager;
import org.hostile.anticheat.event.PacketEvent;
import org.hostile.anticheat.logger.Logger;
import org.hostile.anticheat.logger.factory.LoggerConfiguration;
import org.hostile.anticheat.manager.CheckManager;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;

@Getter
public class AntiCheatServer {

    private static final AntiCheatServer instance = new AntiCheatServer();

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final Logger logger = new Logger(new LoggerConfiguration()
            .setLoggerName("main")
            .setLogTimestamps(true)
    );

    private final CheckManager checkManager = new CheckManager();
    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final ServerConfiguration serverConfiguration = new ServerConfiguration(this);

    private final HttpServer httpServer;

    @SneakyThrows
    public AntiCheatServer() {
        this.httpServer = HttpServer.create(
                new InetSocketAddress(serverConfiguration.getHostname(), serverConfiguration.getPort()),0
        );
        this.httpServer.setExecutor(Executors.newFixedThreadPool(serverConfiguration.getThreads()));

        this.httpServer.createContext("/players/", (req) -> {
            UUID uuid;

            try {
                uuid = UUID.fromString(req.getRequestURI().getPath().replace("/players/", ""));
            } catch (Exception exc) { // an invalid UUID was sent
                return;
            }

            InputStream inputStream = req.getRequestBody();
            StringBuilder builder = new StringBuilder();

            int i;
            while ((i = inputStream.read()) != -1) {
                builder.append((char) i);
            }

            PacketEvent event = new PacketEvent(gson.fromJson(builder.toString(), JsonObject.class));
            PlayerData data = playerDataManager.getData(uuid);

            data.handle(event);

            JsonArray violations = data.getViolations();
            JsonObject responseObject = new JsonObject();

            responseObject.add("violations", violations);

            String response = gson.toJson(responseObject);
            byte[] bytes = response.getBytes();

            req.sendResponseHeaders(200, bytes.length);
            req.getResponseBody().write(bytes);
        });
    }

    public void start() {
        logger.log("Starting server on http://" + serverConfiguration.getHostname() + ":" + serverConfiguration.getPort() + "/");

        httpServer.start();
    }

    public static void main(String[] args) {
        instance.start();
    }

    public static AntiCheatServer getInstance() {
        return instance;
    }
}
