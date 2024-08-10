package sweet_2024;

import java.util.Scanner;
import java.util.logging.*;

public class Application {
    private static final String NO_INFORMATIONS = "There is no information";
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public boolean loggedIn;
    public   Login login;
    public User newUser;
    public Report report;
    private  Scanner scanner = new Scanner(System.in);

    public Application() {
        this.loggedIn = false;
        this.newUser = new User("ali55@gmail.com", "147852", "Customer");
        this.login = new Login(newUser);
        this.report=new Report();
    }

    public void setUser(String email, String password, String type) {
        newUser = new User(email, password, type);
        login.setUser(newUser);
    }

    private void handleLogin() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        setUser(email, password, ""); // assuming type is not needed for login
        if (login.login()) {
            System.out.println("Login successful!");
            loggedIn = true;
            LOGGER.info("User logged in: " + email);
        } else {
            System.out.println("Login failed.");
            LOGGER.warning("Failed login attempt for user: " + email);
        }
    }

    private void handleSignUp() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your user type: ");
        String type = scanner.nextLine();

        if (isValidEmail(email)) {
            setUser(email, password, type);
            SignUp signUp = new SignUp(newUser, login);
            if (signUp.createAccount()) {
                System.out.println("Account created successfully!");
                LOGGER.info("New account created for user: " + email);
            } else {
                System.out.println("Account creation failed.");
                LOGGER.warning("Failed account creation attempt for user: " + email);
            }
        } else {
            System.out.println("Invalid email format.");
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public void startApplication() {
        System.out.println("Welcome to Sweet Management System!");
        System.out.println("Please choose an option:");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleSignUp();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public boolean report(String report, String filename) {
        return switch (report) {

            default -> false;


        };
    }
}