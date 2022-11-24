package org.hostile.anticheat.config;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.SneakyThrows;
import org.hostile.anticheat.AntiCheatServer;
import org.hostile.anticheat.check.data.CheckData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ServerConfiguration {

    private final Map<String, CheckData> checkData = new HashMap<>();

    private final String hostname;

    private final int port;
    private final int threads;

    @SneakyThrows
    public ServerConfiguration(AntiCheatServer server) {
        Path configPath = Paths.get("config.json");

        if (!configPath.toFile().exists()) {
            Files.copy(getClass().getResourceAsStream("/config.json"), configPath);
        }

        JsonObject configuration = server.getGson()
                .fromJson(new String(Files.readAllBytes(configPath)), JsonObject.class);

        configuration.get("checks").getAsJsonArray().forEach(element -> {
            JsonObject object = element.getAsJsonObject();

            String checkType = object.get("check_type").getAsString();
            String checkName = object.get("check_name").getAsString();

            this.checkData.put(
                    checkType + checkName,
                    new CheckData(object)
            );
        });

        this.hostname = configuration.get("hostname").getAsString();

        this.port = configuration.get("port").getAsInt();

        this.threads = configuration.get("threads").getAsInt();
    }
}