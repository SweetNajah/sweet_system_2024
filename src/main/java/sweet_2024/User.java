package sweet_2024;

public class User {
    String email;
    String password;
    String role;
    String firstName;
    String lastName;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        firstName="Ahmad";
        lastName="Ali";
    }
    private String city;

    public String getCity() {
        return city;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
