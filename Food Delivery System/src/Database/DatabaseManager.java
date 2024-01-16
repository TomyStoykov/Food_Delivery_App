package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Food_Delivery_App";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "********";

    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection to MySQL successful");
        } catch (SQLException e) {
            System.out.println("Connection to MySQL failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null || isClosed()) {
            connect();
        }
        return connection;
    }

    public void closeConnection() {
        try
        {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e){
            System.out.println("Failed to close the MySQL connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private boolean isClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Failed to check if the MySQL connection is closed: " + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }
}