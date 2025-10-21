package test;

import database.core.GenericDAO;
import database.core.DBConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class GenericDAOTest {

    private Connection mockConnection;
    private GenericDAO<Money> mockMoneyDAO;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockMoneyDAO = new GenericDAO<>(mockConnection, "Money");
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testCountWithoutCondition() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(100L);

        long count = mockMoneyDAO.count(null);
        Assertions.assertEquals(100L, count);
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(50L);

        String condition = "amount > 1000";
        long count = mockMoneyDAO.count(condition);
        Assertions.assertEquals(50L, count);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        mockStatement.close();
        mockResultSet.close();
    }
}