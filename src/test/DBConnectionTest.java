package test;

import database.core.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBConnectionTest {

    private DBConnection dbConnection;
    private Connection mockConnection;
    
    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        dbConnection = new DBConnection(null, mockConnection);
    }

    @Test
    void testExistsShouldReturnTrueWhenRecordExists() throws Exception {
        // Setup mock to return a result set that will simulate an existing record
        // Assume a table "users" and condition "username='testuser'"
        // Setup code to mock ResultSet and PreparedStatement behaviour
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        boolean exists = dbConnection.exists("users", "username='testuser'");
        assertTrue(exists);
    }

    @Test
    void testExistsShouldReturnFalseWhenNoRecordExists() throws Exception {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        boolean exists = dbConnection.exists("users", "username='nonexistentuser'");
        assertFalse(exists);
    }
}