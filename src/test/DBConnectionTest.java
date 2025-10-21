package test;

import database.core.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBConnectionTest {

    private DBConnection dbConnection;
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        dbConnection = new DBConnection(mockConnection);
    }

    @Test
    void testUpdateFields_success() throws SQLException {
        String tableName = "users";
        String[] fields = {"name", "email"};
        Object[] values = {"John Doe", "john@example.com"};
        String condition = "id = 1";

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dbConnection.updateFields(tableName, fields, values, condition);

        verify(mockConnection).prepareStatement("UPDATE users SET name = ?, email = ? WHERE id = 1");
        verify(mockPreparedStatement).setObject(1, "John Doe");
        verify(mockPreparedStatement).setObject(2, "john@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateFields_fieldValueMismatch() {
        String tableName = "users";
        String[] fields = {"name"};
        Object[] values = {"John Doe", "Extra Value"};
        String condition = "id = 1";

        assertThrows(IllegalArgumentException.class, () -> {
            dbConnection.updateFields(tableName, fields, values, condition);
        });
    }
}