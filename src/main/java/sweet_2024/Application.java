package sweet_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

public class Application {
    private static final String NO_INFORMATIONS = "There is no information";
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    public boolean loggedIn;
    public   Login login;
    public User newUser;
    public Report report;
    Scanner scanner = new Scanner(System.in);
    String carname;
    static ArrayList<TypeProduct> categories;
    static int[] indexes=new int[2];
    private List<Products> availableProducts;
    private List<Order> customerOrders;
    private List<InstallationRequest> installationRequests;
    private List<String> feedbackList;
    private static void gf(){
        categories=new ArrayList<>();
    }

    public Application() {
        this.loggedIn = false;
        this.newUser = new User("ali55@gmail.com", "147852", "Customer");
        this.login = new Login(newUser);
        this.report=new Report();

    }
    public List<InstallationRequest> getInstallationRequests() {
        return installationRequests;
    }
    public void markAsInstalled(Order requestId) {
        for (InstallationRequest request : installationRequests) {
            if (request.getRequestId() == requestId) {
                request.setInstalled(true);
                LOGGER.info("Installation request " + requestId + " marked as installed.");
                return;
            }
        }
        LOGGER.warning("Installation request not found.");
    }
    public void submitFeedback(String feedback) {
        feedbackList.add(feedback);
        LOGGER.info("Feedback submitted successfully!");
    }
    public List<Products> getAvailableProducts() {
        return availableProducts;
    }
    public void placeOrder(Order order) {
        customerOrders.add(order);
    }
    public List<Order> getCustomerOrders() {
        return customerOrders;
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
    public boolean foundc(String name){
        for(int i=0;i<categories.size();i++){
            if(name.equals(categories.get(i).name)){
                set(i,i);
                return true;
            }
        }
        return false;
    }
    private static void set(int x1,int x2){
        indexes[0]=x1;
        indexes[1]=x2;
    }
    public boolean report(String report, String filename) {
        return switch (report) {

            default -> false;


        };
    }
}