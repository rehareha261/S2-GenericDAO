package database.core;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import org.mockito.*;

public class GenericDAOTest {
    private static Connection connection;
    private static GenericDAO<Object> dao;

    @BeforeAll
    public static void setup() throws SQLException {
        // Mocking the connection and DAO for testing
        connection = Mockito.mock(Connection.class);
        dao = new GenericDAO<>(connection, "your_table_name");
    }

    @AfterAll
    public static void teardown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testCountWithoutCondition() throws SQLException {
        // Mocking the behavior of the count method
        Mockito.when(dao.count(null)).thenReturn(10L);

        long count = dao.count(null); // Pas de condition
        Assertions.assertTrue(count >= 0, "Count should be non-negative");
        Assertions.assertEquals(10L, count, "Count without condition should return the mocked value");
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        // Mocking the behavior of the count method with a condition
        Mockito.when(dao.count("id > 5")).thenReturn(5L);

        long count = dao.count("id > 5"); // Exemple de condition
        Assertions.assertTrue(count >= 0, "Count with condition should be non-negative");
        Assertions.assertEquals(5L, count, "Count with condition should return the mocked value");
    }

    @Test
    public void testCountWithInvalidCondition() {
        // Simulating an SQL exception for an invalid condition
        try {
            Mockito.when(dao.count("invalid SQL")).thenThrow(new SQLException("Invalid SQL syntax"));
            dao.count("invalid SQL");
            fail("SQLException was expected for invalid SQL condition");
        } catch (SQLException e) {
            Assertions.assertEquals("Invalid SQL syntax", e.getMessage(), "Exception message should match");
        }
    }

    @Test
    public void testConnectionClosed() throws SQLException {
        // Simulating a closed connection scenario
        Mockito.when(connection.isClosed()).thenReturn(true);

        SQLException exception = assertThrows(SQLException.class, () -> {
            dao.count(null);
        });

        Assertions.assertEquals("Connection is closed", exception.getMessage(), "Exception message should indicate closed connection");
    }
}