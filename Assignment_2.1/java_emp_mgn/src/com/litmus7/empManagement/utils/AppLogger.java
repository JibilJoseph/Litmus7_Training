package com.litmus7.empManagement.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLogger {
    private static final Logger logger = LogManager.getLogger(AppLogger.class);

    static {
        logger.info("=== New Application Session Started ===");
    }

    public static Logger getLogger() {
        return logger;
    }
}
