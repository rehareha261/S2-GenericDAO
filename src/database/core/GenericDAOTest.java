import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private GenericDAO<Object> genericDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Mocking the Connection, PreparedStatement, and ResultSet
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // Setting up the GenericDAO with a mock connection and a test table name
        genericDAO = new GenericDAO<>(mockConnection, "test_table");

        // Mocking the behavior of the connection to return the prepared statement
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        // Mocking the behavior of the prepared statement to return the result set
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @AfterEach
    public void tearDown() {
        // Reset mocks after each test
        Mockito.reset(mockConnection, mockPreparedStatement, mockResultSet);
    }

    @Test
    public void testCountWithoutCondition() throws SQLException {
        // Mocking the result set to return a count of 5
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(5L);

        // Calling the count method without condition
        long count = genericDAO.count();

        // Asserting the expected count
        Assertions.assertEquals(5L, count, "The count should be 5");
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        // Mocking the result set to return a count of 3
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getLong(1)).thenReturn(3L);

        // Calling the count method with a condition
        long count = genericDAO.count("column_name = 'value'");

        // Asserting the expected count
        Assertions.assertEquals(3L, count, "The count should be 3 with the given condition");
    }

    @Test
    public void testCountWithoutConditionNoResults() throws SQLException {
        // Mocking the result set to return no results
        Mockito.when(mockResultSet.next()).thenReturn(false);

        // Calling the count method without condition
        long count = genericDAO.count();

        // Asserting the expected count
        Assertions.assertEquals(0L, count, "The count should be 0 when no results are found");
    }

    @Test
    public void testCountWithConditionNoResults() throws SQLException {
        // Mocking the result set to return no results
        Mockito.when(mockResultSet.next()).thenReturn(false);

        // Calling the count method with a condition
        long count = genericDAO.count("column_name = 'non_existent_value'");

        // Asserting the expected count
        Assertions.assertEquals(0L, count, "The count should be 0 when no results match the condition");
    }

    @Test
    public void testCountSQLException() throws SQLException {
        // Mocking the prepared statement to throw an SQLException
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("SQL error"));

        // Asserting that an SQLException is thrown when calling the count method
        Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count();
        }, "An SQLException should be thrown when there is an SQL error");
    }

    @Test
    public void testCountWithConditionSQLException() throws SQLException {
        // Mocking the prepared statement to throw an SQLException
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("SQL error"));

        // Asserting that an SQLException is thrown when calling the count method with a condition
        Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count("column_name = 'value'");
        }, "An SQLException should be thrown when there is an SQL error with condition");
    }
}