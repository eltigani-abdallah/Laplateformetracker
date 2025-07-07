package com.studentmanagement.database;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private static DatabaseConnection db;

    @BeforeAll
    static void init() {
        db = new DatabaseConnection();
    }

    @Test
    void testConnectionEstablished() {
        assertTrue(db.isConnected(), "Подключение к БД должно быть установлено");
        Connection conn = db.getConnection();
        assertNotNull(conn, "Connection не должен быть null");
    }

    @Test
    void testSimpleQuery() throws SQLException {
        ResultSet rs = db.executeQuery("SELECT 1");
        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
    }

    @AfterAll
    static void cleanup() {
        db.closeResources();
    }
}
