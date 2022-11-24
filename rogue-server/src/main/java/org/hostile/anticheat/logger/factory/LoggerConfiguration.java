package org.hostile.anticheat.logger.factory;

import lombok.Getter;

@Getter
public class LoggerConfiguration {

    private String loggerName;

    private boolean saveLogs;
    private boolean logTimestamps;

    /**
     * @param loggerName The prefix to be used
     * @return The LoggerConfiguration instance
     */
    public LoggerConfiguration setLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    /**
     * @param saveLogs Whether logs should be saved to a file
     * @return The LoggerConfiguration instance
     */
    public LoggerConfiguration setSaveLogs(boolean saveLogs) {
        this.saveLogs = saveLogs;
        return this;
    }

    /**
     * @param logTimestamps Whether timestamps should be logged
     * @return The LoggerConfiguration instance
     */
    public LoggerConfiguration setLogTimestamps(boolean logTimestamps) {
        this.logTimestamps = logTimestamps;
        return this;
    }
}
