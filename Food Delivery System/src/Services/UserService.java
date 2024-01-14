package Services;

import Model.User;
import Repositories.UserRepository;
import Security.PasswordHasher;
import ValidateUser.InvalidInputException;
import ValidateUser.ValidateEmail;
import ValidateUser.ValidatePassword;
import ValidateUser.ValidateUsername;

public class UserService {
    private final UserRepository userRepository;
    private User loggedInUser;
    private int loggedInUserId;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean registerUser(String username,String password,String email,String address,String phoneNumber) {
        try {
            ValidateUsername.isValidUsername(username);
            ValidatePassword.isValidPassword(password);
            byte[] salt = PasswordHasher.generateSalt();
            String hashedPassword = PasswordHasher.hashPassword(password, salt);
            ValidateEmail.isValidEmail(email);
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

    public boolean loginUser(String username,String password) {
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
                loggedInUserId = foundUser.getUser_id();
                return true;
            } else {
                System.out.println("Invalid password.");
                return false;
            }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logoutUser() {
        loggedInUser = null;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
