package database.core;

import java.sql.*;

public class GenericDAO<T> {
    private Connection connection;
    private String tableName;

    public GenericDAO(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * Compte le nombre total d'enregistrements dans la table avec une condition optionnelle
     * @param condition La condition SQL à appliquer (peut être null ou vide si aucune condition n'est nécessaire)
     * @return Le nombre d'enregistrements
     * @throws SQLException en cas d'erreur SQL
     */
    public long count(String condition) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        if (condition != null && !condition.trim().isEmpty()) {
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
}