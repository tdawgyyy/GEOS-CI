package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    //Test Database Connection
    @Test
    public void testConnection() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        assertEquals(true, conn != null);
    }
}