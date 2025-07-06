//DatabaseConnection.java
package com.studentmanagement.database;


import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public DatabaseConnection() {
        DatabaseConfig config = new DatabaseConfig();
        this.url = config.getDatabaseUrl();
        this.username = config.getDatabaseUsername();
        this.password = config.getDatabasePassword();
        connect();
        runSchemaIfNeeded();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }

    private void runSchemaIfNeeded() {
    try {
        String schemaPath = "/database/schema.sql";
        InputStream inputStream = getClass().getResourceAsStream(schemaPath);

        if (inputStream == null) {
            throw new IOException("Unable to find " + schemaPath);
        }

        // Прочитать весь SQL скрипт
        String schemaScript = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();

        // Разделить по $$ (чтобы сначала выполнить функцию, потом остальное)
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



    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void closeResources() {
        disconnect();
    }
}
