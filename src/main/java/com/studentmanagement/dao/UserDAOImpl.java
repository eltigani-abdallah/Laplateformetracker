package com.studentmanagement.dao;

import com.studentmanagement.model.User;

import java.sql.*;

public abstract class UserDAOImpl extends BaseDAO<User> implements UserDAO {

    private final Connection connection;

    // Constructor to initialize the database connection
    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // Save a user to the database
    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Retrieve the generated keys to set the user ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework instead
        }
    }

    // Find a user by their username
    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Authenticate a user by checking username and password hash
    @Override
    public boolean authenticateUser(String username, String passwordHash) {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a user with the given username and password hash exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map a ResultSet to a User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
