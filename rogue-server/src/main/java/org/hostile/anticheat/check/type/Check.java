package org.hostile.anticheat.check.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hostile.anticheat.AntiCheatServer;
import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.data.CheckData;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.logger.Logger;
import org.hostile.anticheat.logger.factory.LoggerConfiguration;
import org.hostile.anticheat.tracker.impl.*;

public abstract class Check<T> {

    protected static final Logger logger = new Logger(
            new LoggerConfiguration()
                    .setLoggerName("check")
                    .setLogTimestamps(true)
    );

    protected final ActionTracker actionTracker;
    protected final CollisionTracker collisionTracker;
    protected final EntityTracker entityTracker;
    protected final MovementTracker movementTracker;
    protected final PingTracker pingTracker;
    protected final PotionTracker potionTracker;

    protected final CheckData checkData;
    protected final CheckMetadata checkMetadata;

    protected final PlayerData data;

    protected double buffer;

    protected final int maxViolations;
    protected int violations;

    public Check(PlayerData playerData) {
        if (!getClass().isAnnotationPresent(CheckMetadata.class)) {
            logger.log("Check " + getClass().getName() + " was not annotated with check metadata!");
            throw new RuntimeException();
        }

        this.checkMetadata = getClass().getDeclaredAnnotation(CheckMetadata.class);
        this.checkData = AntiCheatServer.getInstance().getServerConfiguration()
                .getCheckData().get(checkMetadata.type() + checkMetadata.name());

        this.data = playerData;

        this.actionTracker = data.getActionTracker();
        this.collisionTracker = data.getCollisionTracker();
        this.entityTracker = data.getEntityTracker();
        this.movementTracker = data.getMovementTracker();
        this.pingTracker = data.getPingTracker();
        this.potionTracker = data.getPotionTracker();

        this.maxViolations = checkData.getMaxViolations();
    }

    public abstract void handle(T event);

    protected void fail(Object... data) {
        JsonArray violationData = new JsonArray();

        for(int i = 0; i < data.length; i += 2) {
            JsonObject dataObject = new JsonObject();

            dataObject.addProperty("name", data[i].toString());
            dataObject.addProperty("data", data[i + 1].toString());

            violationData.add(dataObject);
        }

        JsonObject object = new JsonObject();

        object.addProperty("action", ++this.violations >= this.maxViolations ? "ban" : this.checkData.getAction());
        object.addProperty("checkName", checkData.getCheckType());
        object.addProperty("checkType", checkData.getCheckName());
        object.addProperty("violations", violations);
        object.add("data", violationData);
        object.addProperty("maxViolations", maxViolations);

        this.data.addViolation(object);
    }

    protected void debug(Object... data) {
        StringBuilder debugBuilder = new StringBuilder();

        for(int i = 0; i < data.length; i += 2) {
            debugBuilder.append(data[i]).append("=").append(data[i + 1]).append(" ");
        }

        logger.log("[" + checkMetadata.type() + checkMetadata.name() + "] " + debugBuilder.toString());
    }

    protected double incrementBuffer(double amount) {
        return buffer += amount;
    }

    protected double decrementBuffer(double amount) {
        return buffer = Math.max(0, buffer - amount);
    }
}
