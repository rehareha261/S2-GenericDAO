package database.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class GenericDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private GenericDAO<Object> genericDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Création des mocks
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Configuration des comportements des mocks
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Initialisation de l'objet à tester
        genericDAO = new GenericDAO<>(mockConnection, "test_table");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Fermeture des ressources
        mockConnection.close();
        mockPreparedStatement.close();
        mockResultSet.close();
    }

    @Test
    public void testCountWithoutCondition() throws SQLException {
        // Configuration du mock pour retourner un résultat spécifique
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(10L);

        // Appel de la méthode à tester
        long count = genericDAO.count(null);

        // Vérification des résultats
        Assertions.assertEquals(10L, count, "Le nombre d'enregistrements devrait être 10 sans condition.");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        // Configuration du mock pour retourner un résultat spécifique
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(5L);

        // Appel de la méthode à tester avec une condition
        long count = genericDAO.count("status = 'active'");

        // Vérification des résultats
        Assertions.assertEquals(5L, count, "Le nombre d'enregistrements devrait être 5 avec la condition 'status = active'.");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCountWithEmptyCondition() throws SQLException {
        // Configuration du mock pour retourner un résultat spécifique
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(10L);

        // Appel de la méthode à tester avec une condition vide
        long count = genericDAO.count("");

        // Vérification des résultats
        Assertions.assertEquals(10L, count, "Le nombre d'enregistrements devrait être 10 avec une condition vide.");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCountSQLException() throws SQLException {
        // Configuration du mock pour lancer une exception
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Erreur de connexion"));

        // Vérification que l'exception est bien lancée
        Assertions.assertThrows(SQLException.class, () -> {
            genericDAO.count(null);
        }, "Une SQLException devrait être lancée en cas d'erreur de connexion.");
    }
}