package database.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class GenericDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private GenericDAO<Object> genericDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
        genericDAO = new GenericDAO<>(mockConnection, "test_table");

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @AfterEach
    void tearDown() throws SQLException {
        mockConnection.close();
    }

    @Test
    void testCountWithoutCondition() throws SQLException {
        // Arrange
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(5L);

        // Act
        long count = genericDAO.count(null);

        // Assert
        Assertions.assertEquals(5L, count, "The count should be 5 when no condition is provided.");
    }

    @Test
    void testCountWithCondition() throws SQLException {
        // Arrange
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(3L);

        // Act
        long count = genericDAO.count("status = 'active'");

        // Assert
        Assertions.assertEquals(3L, count, "The count should be 3 for the condition 'status = active'.");
    }

    @Test
    void testCountWithEmptyResultSet() throws SQLException {
        // Arrange
        Mockito.when(mockResultSet.next()).thenReturn(false);

        // Act
        long count = genericDAO.count(null);

        // Assert
        Assertions.assertEquals(0L, count, "The count should be 0 when the result set is empty.");
    }

    @Test
    void testCountThrowsSQLException() throws SQLException {
        // Arrange
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count(null);
        }, "An SQLException should be thrown when there is a database error.");
    }

    @Test
    void testCountWithInvalidCondition() throws SQLException {
        // Arrange
        Mockito.when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Invalid SQL syntax"));

        // Act & Assert
        Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count("invalid SQL syntax");
        }, "An SQLException should be thrown for invalid SQL syntax.");
    }
}