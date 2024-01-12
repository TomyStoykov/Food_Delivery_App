package Repositories;

import Database.DatabaseManager;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private final DatabaseManager databaseManager;

    public UserRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (name,email,hashedPassword,address,phoneNumber,role) VALUES (?,?,?,?,?,?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getHashedPassword());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setBytes(7, user.getSalt());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public User getUserByUsername(String username) {
        String sql ="SELECT user_id, name, email, address, phoneNumber, role FROM users WHERE name = ?";
        Connection connection = null;

        try {
            connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("hashedPassword");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                String role = resultSet.getString("role");
                byte[] salt = resultSet.getBytes("salt");

                User user = new User(username, email, password, address, phoneNumber, role,salt);
                user.setUser_id(userId);
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }
}