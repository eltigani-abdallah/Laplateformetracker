package com.studentmanagement.database;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    //DatabaseConnection instance to be used across all tests
    private static DatabaseConnection db;

    //This method rans once before any of the test methods in the class
    @BeforeAll
    static void init() {
        db = new DatabaseConnection();
    }
    //Test to check if the database connection is established
    @Test
    void testConnectionEstablished() {
        assertTrue(db.isConnected(), "Database connection should be established");
        Connection conn = db.getConnection();
        assertNotNull(conn, "Connection should not be null");
    }
    //Test to execute a simple SQL query and verify the result
    @Test
    void testSimpleQuery() throws SQLException {
        ResultSet rs = db.executeQuery("SELECT 1");
        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
    }
    // This method runs once after all the test methods have been executed
    @AfterAll
    static void cleanup() {
        db.closeResources();
    }
}
