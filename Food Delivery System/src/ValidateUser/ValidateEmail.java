package ValidateUser;

import java.util.regex.Pattern;



public class ValidateEmail{
    public static void isValidEmail(String email) throws InvalidInputException{
        final Pattern emailPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9._]*@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,}$");
        if(!emailPattern.matcher(email).matches()){
            throw new InvalidInputException("Invalid email input");
        }
    }
}
