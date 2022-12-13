package org.hostile.rogue.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.hostile.rogue.RoguePlugin;
import org.hostile.rogue.data.PlayerData;
import org.hostile.rogue.packet.PacketWrapper;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.packet.inbound.WrappedPacketPlayInFlying;
import org.hostile.rogue.util.json.JsonChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class RogueWebClient {

    private static final Gson GSON = new Gson();

    private final String userAgent;
    private final String host;

    private final int port;

    public RogueWebClient(FileConfiguration configuration) {
        this.userAgent = configuration.getString("user-agent");
        this.host = configuration.getString("host");
        this.port = configuration.getInt("port");
    }

    public void sendPacket(PlayerData playerData, WrappedPacket packet) throws IOException {
        URL url = new URL("http://" + host + ":" + port + "/players/" + playerData.getPlayer().getUniqueId().toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.addRequestProperty("user-agent", userAgent);

        if (packet != null) {
            JsonChain jsonChain = new JsonChain()
                    .addProperty("packet", packet.serialize().getJsonObject())
                    .addProperty("type", packet.getName())
                    .addProperty("timestamp", System.currentTimeMillis());

            if (packet instanceof WrappedPacketPlayInFlying) {
                Player player = playerData.getPlayer();

                jsonChain.addProperty("collisions", playerData.getCollisionTracker().getCollisions().serialize())
                        .addProperty("gamemode", player.getGameMode().name())
                        .addProperty("walkSpeed", player.getWalkSpeed())
                        .addProperty("entityId", player.getEntityId())
                        .addProperty("inVehicle", player.getVehicle() != null)
                        .addProperty("world", player.getWorld().getName());
            }

            String payload = GSON.toJson(jsonChain.getJsonObject());

            int payloadLength = payload.getBytes().length;
            byte[] payloadArray = payload.getBytes();

            connection.setFixedLengthStreamingMode(payloadLength);
            connection.setDoOutput(true);

            connection.connect();
            connection.getOutputStream().write(payloadArray);
        } else {
            connection.connect();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        JsonObject object = GSON.fromJson(builder.toString(), JsonObject.class);

        object.entrySet().forEach(element -> {
            JsonArray array = element.getValue().getAsJsonArray();
            array.forEach(jsonElement -> {
                if (jsonElement.isJsonObject()) {
                    RoguePlugin.getInstance().getViolationHandler().handle(playerData, jsonElement.getAsJsonObject());
                }
            });
        });
    }

    public void sendQuit(UUID uuid) throws IOException {
        URL url = new URL("http://" + host + ":" + port + "/quit/" + uuid.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.addRequestProperty("user-agent", userAgent);
        connection.connect();
    }
}
