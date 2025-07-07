package com.studentmanagement.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

public class DatabaseConnection {
    private String url; // Database URL
    private String username; // Database username
    private String password; // Database password
    private Connection connection; // Connection object to manage the database connection

    // Constructor to initialize the database connection
    public DatabaseConnection() {
        DatabaseConfig config = new DatabaseConfig();
        this.url = config.getDatabaseUrl();
        this.username = config.getDatabaseUsername();
        this.password = config.getDatabasePassword();
        connect(); // Establish the connection
        runSchemaIfNeeded(); // Run the schema script if needed
    }

    // Method to establish a connection to the database
    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }

    // Method to run the database schema script if needed
    private void runSchemaIfNeeded() {
        try {
            String schemaPath = "/database/schema.sql";
            InputStream inputStream = getClass().getResourceAsStream(schemaPath);
            if (inputStream == null) {
                throw new IOException("Unable to find " + schemaPath);
            }
            // Read the entire SQL script
            String schemaScript = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            inputStream.close();
            // Split by $$ to execute the function first, then the rest
            String[] parts = schemaScript.split("(?<=\\$\\$ language 'plpgsql';)");
            try (Statement stmt = connection.createStatement()) {
                for (String part : parts) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        System.out.println("Executing part:\n" + trimmed);
                        stmt.execute(trimmed);
                    }
                }
            }
            System.out.println("Schema executed successfully.");
        } catch (Exception e) {
            System.err.println("Failed to execute schema.sql.");
            e.printStackTrace();
        }
    }

    // Method to disconnect from the database
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter for the connection object
    public Connection getConnection() {
        return connection;
    }

    // Method to check if the connection is active
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    // Method to execute a SQL query and return the result set
    public ResultSet executeQuery(String sql) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to execute an update SQL statement
    public void executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to execute a safe query with parameters to prevent SQL injection
    public void executeSafeQuery(String sql, List<Object> parameters) {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close resources and disconnect from the database
    public void closeResources() {
        disconnect();
    }
}