package com.litmus7.empManagement.utils;

import java.io.InputStream;
import java.util.Properties;

public class ErrorCodeManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ErrorCodeManager.class.getClassLoader().getResourceAsStream("com/resources/error-codes.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find error-codes.properties");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error loading error-codes.properties", e);
        }
    }

    public static String getMessage(int errorCode) {
        return properties.getProperty(String.valueOf(errorCode));
    }
}