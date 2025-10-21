package test;

import database.core.DBConnection;
import database.core.GenericDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenericDAOTest {

    private DBConnection dbConnection;
    private Connection mockConnection;
    private GenericDAO<Object> dao;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        dbConnection = new DBConnection(null, mockConnection);
        dao = new GenericDAO<>(dbConnection, "test_table");
    }

    @Test
    void testFindFirst() throws SQLException {
        String condition = "id = 1";
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        // Supposons que mapToEntity renvoie le même objet à des fins de test
        when(mockResultSet.getObject(anyString())).thenReturn(new Object());

        Optional<Object> result = dao.findFirst(condition);

        assertTrue(result.isPresent());
        verify(mockStatement).executeQuery();
    }

    @Test
    void testFindFirstNoResult() throws SQLException {
        String condition = "id = 2";
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Object> result = dao.findFirst(condition);

        assertFalse(result.isPresent());
        verify(mockStatement).executeQuery();
    }
}