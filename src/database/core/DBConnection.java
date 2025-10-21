package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    public DBConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Met à jour certains champs spécifiques d'un enregistrement dans la base de données.
     * 
     * @param tableName Le nom de la table à mettre à jour.
     * @param fields Les noms des champs à mettre à jour.
     * @param values Les valeurs des champs à mettre à jour.
     * @param condition La condition pour identifier l'enregistrement à mettre à jour.
     * @throws SQLException en cas d'erreur SQL.
     */
    public void updateFields(String tableName, String[] fields, Object[] values, String condition) throws SQLException {
        if (fields.length != values.length) {
            throw new IllegalArgumentException("Le nombre de champs doit être égal au nombre de valeurs.");
        }

        StringBuilder setClause = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            setClause.append(fields[i]).append(" = ?");
            if (i < fields.length - 1) {
                setClause.append(", ");
            }
        }

        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + condition;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            pstmt.executeUpdate();
        }
    }
}