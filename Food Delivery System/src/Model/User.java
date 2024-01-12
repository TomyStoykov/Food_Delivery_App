package Model;

public class User {
    private int user_id;
    private String name;
    private String email;
    private String hashedPassword;
    private String address;
    private String phoneNumber;
    private String role;
    private byte[] salt;

    public User(String name, String email, String hashedPassword, String address, String phoneNumber, String role,byte[] salt) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.salt = salt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
