//DatabaseConfig.java
package com.studentmanagement.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private Properties properties = new Properties();
    private final String configPath = "/config/application.properties";

    public DatabaseConfig() {
        try (InputStream input = getClass().getResourceAsStream(configPath)) {
            if (input == null) {
                throw new IOException("Unable to find " + configPath);
            }
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Unable to load database configuration.");
            e.printStackTrace();
        }
    }

    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }
}
