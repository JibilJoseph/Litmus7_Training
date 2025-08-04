package com.litmus7.empManagement.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class AppLogger {
    private static Logger logger;

    // Load once class is called
    static 
    {
        try {
            logger = Logger.getLogger("EmployeeManagementLog");

            LogManager.getLogManager().reset(); // reset existing config

            // Clear any existing handlers
            for (Handler handler : logger.getHandlers()) {
                logger.removeHandler(handler);
            }

            // Create log file with timestamp to ensure uniqueness
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String logFileName = "employee-management_" + timestamp + ".log";
            
            // File handler - false means overwrite, but we use timestamp to ensure fresh file
            FileHandler fh = new FileHandler(logFileName, false);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);

            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            
            // Log the start of new session
            logger.info("=== New Application Session Started ===");
            
        } catch (IOException e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
