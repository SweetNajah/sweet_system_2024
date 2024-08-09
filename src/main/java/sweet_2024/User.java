package sweet_2024;

public class User {
    String email;
    String password;
    String type;

    String firstName;
    String lastName;

    public User(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
