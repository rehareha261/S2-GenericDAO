package test;

import database.core.GenericDAO;
import database.core.DBConnection;
import database.provider.PostgreSQL;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GenericDAOTest {

    private static GenericDAO<?> genericDAO;
    private static DBConnection dbConnection;

    @BeforeAll
    public static void setup() throws Exception {
        // Mocking the DBConnection and GenericDAO for testing
        dbConnection = Mockito.mock(DBConnection.class);
        genericDAO = new GenericDAO<>(dbConnection, "my_table");
    }

    @Test
    public void testCountAll() throws SQLException {
        // Mocking the count method to return a specific value
        when(genericDAO.count(null)).thenReturn(10L);

        long count = genericDAO.count(null);
        assertEquals(10L, count, "The count of all records should be 10");
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        String condition = "column_name = 'value'";
        // Mocking the count method with a condition to return a specific value
        when(genericDAO.count(condition)).thenReturn(5L);

        long count = genericDAO.count(condition);
        assertEquals(5L, count, "The count of records with condition should be 5");
    }

    @Test
    public void testCountWithInvalidCondition() {
        String invalidCondition = "invalid_column = 'value'";
        // Assuming that an invalid condition throws an SQLException
        when(genericDAO.count(invalidCondition)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> genericDAO.count(invalidCondition),
                "An SQLException should be thrown for an invalid condition");
    }

    @Test
    public void testCountWithNullCondition() throws SQLException {
        // Mocking the count method with a null condition to return a specific value
        when(genericDAO.count(null)).thenReturn(10L);

        long count = genericDAO.count(null);
        assertEquals(10L, count, "The count of all records with null condition should be 10");
    }
}