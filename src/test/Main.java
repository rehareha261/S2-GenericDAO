package test;

import database.core.DBConnection;
import database.core.Database;
import database.provider.PostgreSQL;
import database.core.GenericDAO;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        Database database = new PostgreSQL("localhost", "5432", "dao", "", "");
        Connection dbConnection = database.createConnection();

        // Exemple d'utilisation de GenericDAO et du test pour la méthode count()
        GenericDAO<Emp> empDAO = new GenericDAO<>(dbConnection, "Emp");

        // Compter tous les enregistrements
        long totalRecords = empDAO.count(null);
        System.out.println("Total records in Emp: " + totalRecords);

        // Compter avec une condition
        long conditionRecords = empDAO.count("salary > 50000");
        System.out.println("Employees with salary > 50000: " + conditionRecords);
    }
}