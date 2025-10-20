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

    private GenericDAO<Object> genericDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        genericDAO = new GenericDAO<Object>() {
            {
                connection = mockConnection;
                tableName = "test_table";
            }
        };

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @AfterEach
    void tearDown() throws SQLException {
        mockResultSet.close();
        mockPreparedStatement.close();
        mockConnection.close();
    }

    @Test
    void testCountWithoutCondition() throws SQLException {
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(5L);

        long count = genericDAO.count(null);

        Assertions.assertEquals(5L, count, "The count should be 5 when there are 5 records in the table.");
    }

    @Test
    void testCountWithCondition() throws SQLException {
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(3L);

        long count = genericDAO.count("status = 'active'");

        Assertions.assertEquals(3L, count, "The count should be 3 when there are 3 active records in the table.");
    }

    @Test
    void testCountWithEmptyResultSet() throws SQLException {
        Mockito.when(mockResultSet.next()).thenReturn(false);

        long count = genericDAO.count(null);

        Assertions.assertEquals(0L, count, "The count should be 0 when there are no records in the table.");
    }

    @Test
    void testCountWithSQLException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("Database error"));

        SQLException exception = Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count(null);
        });

        Assertions.assertEquals("Database error", exception.getMessage(), "The exception message should match the thrown SQLException message.");
    }

    @Test
    void testCountWithInvalidCondition() throws SQLException {
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(0L);

        long count = genericDAO.count("invalid_column = 'value'");

        Assertions.assertEquals(0L, count, "The count should be 0 when the condition does not match any records.");
    }
}