package test;

import database.core.GenericDAO;
import database.core.DBConnection;
import database.provider.PostgreSQL;
import org.junit.jupiter.api.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericDAOTest {

    private static GenericDAO<?> genericDAO;
    private static DBConnection dbConnection;

    @BeforeAll
    public static void setup() throws Exception {
        // Ajustez les paramètres ci-dessous pour votre connexion à la base de données
        dbConnection = new PostgreSQL("localhost", "5432", "dao", "", "").createConnection();
        genericDAO = new GenericDAO<>(dbConnection, "my_table");  // Remplacez "my_table" par votre table de test
    }

    @Test
    public void testCountAll() throws SQLException {
        long count = genericDAO.count(null);
        // Remplacez 'expectedCount' par le nombre attendu d'enregistrements
        assertEquals(expectedCount, count);
    }

    @Test
    public void testCountWithCondition() throws SQLException {
        String condition = "column_name = value";  // Ajustez cette condition en fonction de votre table de test
        long count = genericDAO.count(condition);
        // Remplacez 'expectedCountWithCondition' par le nombre attendu d'enregistrements avec la condition
        assertEquals(expectedCountWithCondition, count);
    }
}