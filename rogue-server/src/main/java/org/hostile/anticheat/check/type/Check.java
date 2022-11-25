package org.hostile.anticheat.check.type;

import org.hostile.anticheat.check.annotation.CheckMetadata;
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
    protected final MovementTracker movementTracker;
    protected final PingTracker pingTracker;
    protected final PotionTracker potionTracker;

    protected final CheckMetadata checkMetadata;
    protected final PlayerData data;

    protected double buffer;

    public Check(PlayerData playerData) {
        if (!getClass().isAnnotationPresent(CheckMetadata.class)) {
            logger.log("Check " + getClass().getName() + " was not annotated with check metadata!");
            throw new RuntimeException();
        }

        this.checkMetadata = getClass().getDeclaredAnnotation(CheckMetadata.class);
        this.data = playerData;

        this.actionTracker = data.getActionTracker();
        this.collisionTracker = data.getCollisionTracker();
        this.movementTracker = data.getMovementTracker();
        this.pingTracker = data.getPingTracker();
        this.potionTracker = data.getPotionTracker();
    }

    public abstract void handle(T event);

    protected void fail(Object... data) {

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
