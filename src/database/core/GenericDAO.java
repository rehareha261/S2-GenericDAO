package database.core;

import java.sql.*;
import java.util.Optional;

public class GenericDAO<T> {
    private DBConnection dbConnection;
    private String tableName;

    public GenericDAO(DBConnection dbConnection, String tableName) {
        this.dbConnection = dbConnection;
        this.tableName = tableName;
    }

    /**
     * Récupère le premier enregistrement correspondant à une condition donnée.
     * @param condition La condition SQL sous forme de chaîne de caractères
     * @return Un Optional contenant le premier enregistrement correspondant, ou un Optional vide si aucun enregistrement n'est trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    public Optional<T> findFirst(String condition) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + condition + " LIMIT 1";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Supposons qu'il existe une méthode pour mapper ResultSet à l'objet T
                T result = mapToEntity(rs);
                return Optional.of(result);
            }
            return Optional.empty();
        }
    }

    /**
     * Méthode fictive pour mapper un ResultSet à une instance de T
     * @param rs le ResultSet à mapper
     * @return une instance de T
     * @throws SQLException en cas d'erreur SQL
     */
    private T mapToEntity(ResultSet rs) throws SQLException {
        // Logique de mapping à implémenter selon les besoins
        return null;
    }

    // Autres méthodes de GenericDAO...
}