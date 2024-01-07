package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private final String DB_URL;
    private Connection connection;

    public DatabaseManager(String dbPath) {
        this.DB_URL = "jdbc:sqlite:" + dbPath;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        if (connection == null || isClosed()) {
            connect();
        }
        return connection;
    }

    public void closeConnection () {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
               return true;
        }
    }
}

