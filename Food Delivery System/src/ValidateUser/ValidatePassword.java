package ValidateUser;

import java.util.regex.Pattern;

public class ValidatePassword {
    public static void isValidPassword(String password) throws InvalidInputException {
        final Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        if (!passwordPattern.matcher(password).matches()) {
            throw new InvalidInputException("Invalid password input");
        }
    }
}
