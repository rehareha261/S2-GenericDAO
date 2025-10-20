package database.core;

import java.sql.*;

/**
 * Classe générique pour les opérations CRUD dans la base de données.
 */
public class GenericDAO<T> {
    protected Connection connection;
    protected String tableName;
    
    public GenericDAO(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * Compte le nombre total d'enregistrements dans la table, avec ou sans condition.
     * @param condition La condition de filtre des enregistrements, peut être null.
     * @return Le nombre d'enregistrements.
     * @throws SQLException en cas d'erreur SQL.
     */
    public long count(String condition) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        if (condition != null && !condition.isEmpty()) {
            sql += " WHERE " + condition;
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }

    // ... autres méthodes ...
}