package com.studentmanagement.dao;

import java.sql.*;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    protected BaseDAO() {
    }

    protected abstract T mapResultSet(ResultSet rs) throws SQLException;

    protected int count(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected void closeResources(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
