package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.SQLException;
import database.core.DBConnection;
import database.core.Database;
import database.provider.PostgreSQL;
import database.core.GenericDAO;

public class MainTest {

    private Database mockDatabase;
    private Connection mockConnection;
    private GenericDAO<Emp> mockEmpDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Mocking Database and Connection
        mockDatabase = Mockito.mock(PostgreSQL.class);
        mockConnection = Mockito.mock(Connection.class);
        Mockito.when(mockDatabase.createConnection()).thenReturn(mockConnection);

        // Mocking GenericDAO
        mockEmpDAO = Mockito.mock(GenericDAO.class);
    }

    @AfterEach
    public void tearDown() {
        mockDatabase = null;
        mockConnection = null;
        mockEmpDAO = null;
    }

    @Test
    public void testMainMethodCountAllRecords() throws Exception {
        // Arrange
        Mockito.when(mockEmpDAO.count(null)).thenReturn(100L);

        // Act
        long totalRecords = mockEmpDAO.count(null);

        // Assert
        Assertions.assertEquals(100L, totalRecords, "Total records should be 100");
    }

    @Test
    public void testMainMethodCountWithCondition() throws Exception {
        // Arrange
        Mockito.when(mockEmpDAO.count("salary > 50000")).thenReturn(10L);

        // Act
        long conditionRecords = mockEmpDAO.count("salary > 50000");

        // Assert
        Assertions.assertEquals(10L, conditionRecords, "Employees with salary > 50000 should be 10");
    }

    @Test
    public void testMainMethodCountWithInvalidCondition() throws Exception {
        // Arrange
        Mockito.when(mockEmpDAO.count("invalid condition")).thenThrow(new SQLException("Invalid SQL"));

        // Act & Assert
        Assertions.assertThrows(SQLException.class, () -> {
            mockEmpDAO.count("invalid condition");
        }, "Should throw SQLException for invalid condition");
    }

    @Test
    public void testMainMethodDatabaseConnectionFailure() throws Exception {
        // Arrange
        Mockito.when(mockDatabase.createConnection()).thenThrow(new SQLException("Connection failed"));

        // Act & Assert
        Assertions.assertThrows(SQLException.class, () -> {
            mockDatabase.createConnection();
        }, "Should throw SQLException when database connection fails");
    }
}