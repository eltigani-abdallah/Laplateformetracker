//DatabaseConfig.java
package com.studentmanagement.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private Properties properties = new Properties(); // Properties object to store configuration details
    private final String configPath = "/config/application.properties"; // Path to the configuration file

    // Constructor to load database configuration from the properties file
    public DatabaseConfig() {
        try (InputStream input = getClass().getResourceAsStream(configPath)) {
            if (input == null) {
                throw new IOException("Unable to find " + configPath);
            }
            properties.load(input); // Load properties from the input stream
        } catch (IOException e) {
            System.err.println("Unable to load database configuration.");
            e.printStackTrace();
        }
    }

    // Method to retrieve the database URL from the properties
    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    // Method to retrieve the database username from the properties
    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    // Method to retrieve the database password from the properties
    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }
}
