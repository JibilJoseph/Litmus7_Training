package com.litmus7.inventory.utils;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

public class Applogger {

    private static final Logger logger = Logger.getLogger(Applogger.class.getName());
    private static final String LOG_DIRECTORY = "logs";
    private static final String LOG_FILE_NAME = "inventory_app.log";
    private static boolean loggerInitialized = false;

    public static void setupLogger() {
        if (loggerInitialized) {
            return; // Prevent re-initialization
        }
        try {
            // Create logs directory if it doesn't exist
            File logDir = new File(LOG_DIRECTORY);
            if (!logDir.exists()) {
                logDir.mkdir();
            }

            // Configure FileHandler
            String logFilePath = LOG_DIRECTORY + File.separator + LOG_FILE_NAME;
            FileHandler fh = new FileHandler(logFilePath, true);

            // Remove all existing handlers to prevent console output
            Handler[] handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                logger.removeHandler(handler);
            }
            logger.addHandler(fh);

            // Prevent messages from propagating to parent loggers (which might have console handlers)
            logger.setUseParentHandlers(false);

            // Configure formatter
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // Set logger level (optional, but good practice)
            logger.setLevel(Level.ALL);
            fh.setLevel(Level.ALL);

            loggerInitialized = true;

        } catch (Exception e) { 
            System.err.println("Error setting up logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void logInfo(String message, Object... params) {
        setupLogger();
        logger.log(Level.INFO, message, params);
    }

    public static void logSevere(String message, Throwable thrown) {
        setupLogger();
        logger.log(Level.SEVERE, message, thrown);
    }
    
    public static void logSevere(String message) {
        setupLogger();
        logger.log(Level.SEVERE, message);
    }

    // Add other logging levels as needed (e.g., warning, error)
}

