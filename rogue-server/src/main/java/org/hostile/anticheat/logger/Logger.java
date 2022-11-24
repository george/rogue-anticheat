package org.hostile.anticheat.logger;

import org.hostile.anticheat.logger.factory.LoggerConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {

    private final List<String> loggedMessages;

    private final LoggerConfiguration config;

    public Logger(LoggerConfiguration config) {
        this.config = config;

        if (config.isSaveLogs()) {
            this.loggedMessages = new ArrayList<>();
        } else {
            this.loggedMessages = null;
        }
    }

    public void log(String message) {
        StringBuilder messageBuilder = new StringBuilder("[")
                .append(config.getLoggerName()).append("] ");

        if (config.isLogTimestamps()) {
            messageBuilder.append("[").append(new Date().toString()).append("] ");
        }

        messageBuilder.append(message);

        if (config.isSaveLogs()) {
            loggedMessages.add(messageBuilder.toString());
        }

        System.out.println(messageBuilder.toString());
    }

    public void save() {

    }
}
