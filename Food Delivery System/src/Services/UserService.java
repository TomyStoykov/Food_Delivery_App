package Services;

import Model.User;
import Repositories.UserRepository;
import Security.PasswordHasher;
import ValidateUser.InvalidInputException;
import ValidateUser.ValidateEmail;
import ValidateUser.ValidatePassword;
import ValidateUser.ValidateUsername;

import java.util.Scanner;

public class UserService {
    private final UserRepository userRepository;
    private User loggedInUser;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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
            byte[] salt = PasswordHasher.generateSalt();
            String hashedPassword = PasswordHasher.hashPassword(password, salt);
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            ValidateEmail.isValidEmail(email);

            System.out.println("Enter your address: ");
            String address = scanner.nextLine();

            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();

            String role = "customer";

            User newUser = new User(username, email, hashedPassword, address, phoneNumber, role,salt);

            userRepository.createUser(newUser);

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

            User foundUser = userRepository.getUserByUsername(username);

            if (foundUser == null) {
                System.out.println("User not found.");
                return false;
            }
            byte[] salt = foundUser.getSalt();
            password = PasswordHasher.hashPassword(password,salt);
            if (password.equals(foundUser.getHashedPassword())) {
                System.out.println("Login successful.");
                loggedInUser = foundUser;
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

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logoutUser() {
        loggedInUser = null;
    }
}
