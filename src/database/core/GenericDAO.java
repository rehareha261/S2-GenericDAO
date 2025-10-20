package database.core;

import java.sql.*;
import java.util.*;

public class GenericDAO<T> {
    // Champ pour la table associée
    protected String tableName;
    protected Connection connection;
    
    // Constructeur de GenericDAO
    public GenericDAO(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }
    
    /**
     * Compte le nombre total d'enregistrements dans la table
     * (sans condition)
     * @return Le nombre d'enregistrements
     * @throws SQLException en cas d'erreur SQL
     */
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }
    
    /**
     * Compte le nombre d'enregistrements dans la table avec une condition
     * @param condition La condition SQL WHERE
     * @return Le nombre d'enregistrements correspondant à la condition
     * @throws SQLException en cas d'erreur SQL
     */
    public long count(String condition) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + condition;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }
    
    // Autres méthodes de GenericDAO...
}