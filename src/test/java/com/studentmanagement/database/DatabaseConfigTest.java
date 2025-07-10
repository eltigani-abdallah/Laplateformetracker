package com.studentmanagement.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {
     // Test method to verify that database properties are loaded correctly
    @Test
    void testLoadDatabaseProperties() {
        DatabaseConfig config = new DatabaseConfig();

        assertNotNull(config.getDatabaseUrl(), "URL не должен быть null");
        assertNotNull(config.getDatabaseUsername(), "Username should not be null");
        assertNotNull(config.getDatabasePassword(), "Password should not be null");
        //Print the database URL and username to the console
        System.out.println("URL: " + config.getDatabaseUrl());
        System.out.println("Username: " + config.getDatabaseUsername());
    }
}

