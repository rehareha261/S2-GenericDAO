package test;

import database.core.GenericDAO;
import database.core.DBConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Crée une connexion fictive (à remplacer par une vraie connexion dans un contexte réel)
        Connection connection = DBConnection.getConnection(); // Méthode imaginaire pour obtenir une connexion
        
        GenericDAO<Money> moneyDAO = new GenericDAO<>(connection, "Money");
        
        try {
            long total = moneyDAO.count();
            System.out.println("Total entries in Money table: " + total);
            
            long conditionCount = moneyDAO.count("value > 100");
            System.out.println("Entries in Money table with value > 100: " + conditionCount);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}