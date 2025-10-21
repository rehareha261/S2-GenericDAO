package database.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    private Database database;
    private Connection connection;

    public DBConnection(Database database, Connection connection) {
        setDatabase(database);
        setConnection(connection);
    }

    public void commit() throws SQLException {
        getConnection().commit();
    }

    public void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Nouvelle méthode pour vérifier l'existence d'un enregistrement
    public boolean exists(String tableName, String condition) {
        String query = "SELECT 1 FROM " + tableName + " WHERE " + condition + " LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setDatabase(Database database) {
        this.database = database;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Database getDatabase() {
        return database;
    }

    public Connection getConnection() {
        return connection;
    }
}