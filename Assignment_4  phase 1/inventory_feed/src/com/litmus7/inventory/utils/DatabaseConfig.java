package com.litmus7.inventory.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;



public class DatabaseConfig {
	private static final String PROPERTIES_FILE = "src/db.properties";
	private static final Properties props = new Properties();
	
	// moving to static block
	static {
    	try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

	// Function 1 : To get DataBase Connection 
    public static Connection getConnection() throws SQLException {
       
        String url = props.getProperty("dbUrl");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}

