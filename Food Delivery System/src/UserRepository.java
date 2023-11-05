import ValidateUser.InvalidInputException;
import ValidateUser.ValidateEmail;
import ValidateUser.ValidatePassword;
import ValidateUser.ValidateUsername;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRepository {

    private DatabaseManager databaseManager;

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

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean registerUser() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            ValidateUsername.isValidUsername(username);
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            ValidatePassword.isValidPassword(password);
            //TODO : hash the password, add salt

            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            ValidateEmail.isValidEmail(email);

            System.out.println("Enter your address: ");
            String address = scanner.nextLine();

            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();

            String role = "customer";

            User newUser = new User(username, email, password, address, phoneNumber, role);

            createUser(newUser);

            System.out.println("Registration successful.");
            return true;

        } catch (InvalidInputException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUser() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            User foundUser = getUserByUsername(username);

            if (foundUser == null) {
                System.out.println("User not found.");
                return false;
            }
            if (password.equals(foundUser.getHashedPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Invalid password.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    private User getUserByUsername(String username) {
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

                User user = new User(username, email, password, address, phoneNumber, role);
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