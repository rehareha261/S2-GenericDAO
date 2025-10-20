package database.core;

import java.sql.*;
import java.util.*;

public class GenericDAO<T> {
    protected Connection connection;
    protected String tableName;

    // ... autres méthodes et attributs ...

    /**
     * Compte le nombre total d'enregistrements dans la table
     * @param condition La condition SQL optionnelle pour filtrer les enregistrements
     * @return Le nombre d'enregistrements
     * @throws SQLException en cas d'erreur SQL
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

    // Exemple d'utilisation:
    // public long count() throws SQLException {
    //     return count(null);
    // }
    // ... reste du code existant ...
}