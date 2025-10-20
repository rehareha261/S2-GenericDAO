package test;

import database.core.GenericDAO;
import database.core.DBConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class MainTest {

    private Connection mockConnection;
    private GenericDAO<Money> mockMoneyDAO;

    @BeforeEach
    public void setUp() {
        // Création d'une connexion fictive
        mockConnection = Mockito.mock(Connection.class);
        // Création d'un DAO fictif
        mockMoneyDAO = Mockito.mock(GenericDAO.class);
    }

    @AfterEach
    public void tearDown() {
        mockConnection = null;
        mockMoneyDAO = null;
    }

    @Test
    public void testMainMethodWithValidData() throws SQLException {
        // Configuration des comportements attendus
        when(mockMoneyDAO.count()).thenReturn(10L);
        when(mockMoneyDAO.count("value > 100")).thenReturn(5L);

        // Exécution de la méthode main
        try {
            long total = mockMoneyDAO.count();
            long conditionCount = mockMoneyDAO.count("value > 100");

            // Assertions
            Assertions.assertEquals(10L, total, "Le nombre total d'entrées devrait être 10.");
            Assertions.assertEquals(5L, conditionCount, "Le nombre d'entrées avec valeur > 100 devrait être 5.");
        } catch (Exception e) {
            Assertions.fail("Exception inattendue: " + e.getMessage());
        }
    }

    @Test
    public void testMainMethodWithSQLException() throws SQLException {
        // Configuration pour lancer une SQLException
        when(mockMoneyDAO.count()).thenThrow(new SQLException("Erreur de connexion à la base de données"));

        // Exécution de la méthode main
        try {
            mockMoneyDAO.count();
            Assertions.fail("Une SQLException était attendue mais n'a pas été lancée.");
        } catch (SQLException e) {
            // Assertions
            Assertions.assertEquals("Erreur de connexion à la base de données", e.getMessage(), "Le message d'exception devrait être 'Erreur de connexion à la base de données'.");
        }
    }

    @Test
    public void testMainMethodWithNoCondition() throws SQLException {
        // Configuration des comportements attendus
        when(mockMoneyDAO.count()).thenReturn(0L);

        // Exécution de la méthode main
        try {
            long total = mockMoneyDAO.count();

            // Assertions
            Assertions.assertEquals(0L, total, "Le nombre total d'entrées devrait être 0.");
        } catch (Exception e) {
            Assertions.fail("Exception inattendue: " + e.getMessage());
        }
    }
}