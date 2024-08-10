package sweet_2024;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

public class MainClass {
    private static final String ADMIN_STRING = "Admin";
    private static final String STORE_OWNER_STRING = "Store Owner";
    private static final String SUPPLIER_STRING = "Supplier";
    private static final String INVALID_INPUT_MESSAGE = "Invalid Input";
    private static final String INVALID_INFORMATION_PLEASE_TRY_AGAIN = "Invalid information! Please try again.";
    private static final String STRING = "********************************************************************";
    private static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());

    static {
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.OFF);
            }
        }
    }

    public static void main(String[] arg) {

        Application sweetSystem = new Application();
        setupLogger();

        Scanner scanner = new Scanner(System.in);
        LOGGER.info("SWEET MANAGEMENT SYSTEM");

        int authen = -1;

        do {
            try {
                LOGGER.info("1-Sign-up \n2-Sign-in \n3-Exit");
                authen = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }

            switch (authen) {
                case 1 -> signUp(scanner, sweetSystem);
                case 2 -> signIn(scanner, sweetSystem);
                case 3 -> System.exit(0);
                default -> LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
            }
        } while (authen != 3);

        scanner.close();
    }

    private static void setupLogger() {
        try {
            LOGGER.setUseParentHandlers(false);
            Handler[] handlers = LOGGER.getHandlers();
            for (Handler handler : handlers) {
                LOGGER.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(java.util.logging.LogRecord logRecord) {
                    return logRecord.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(consoleHandler);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during logger configuration", e);
        }
    }

    private static void signUp(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter your email: ");
        String email = scanner.nextLine();

        LOGGER.info("Enter your password: ");
        String password = scanner.nextLine();

        LOGGER.info("Enter your role (Admin/Store Owner/Supplier): ");
        String role = scanner.nextLine();

        if (sweetSystem.login.emailValidator(email)) {
            sweetSystem.login.addUser(new User(email, password, role));
            LOGGER.info("User Created Successfully");
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }

    private static void signIn(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter your email: ");
        String signInEmail = scanner.nextLine();

        LOGGER.info("Enter your password: ");
        String signInPassword = scanner.nextLine();

        sweetSystem.login.setUser(new User(signInEmail, signInPassword, ""));

        if (sweetSystem.login.login()) {
            sweetSystem.login.setRoles();
            handleRoles(scanner, sweetSystem, signInEmail, signInPassword);
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }

    private static void handleRoles(Scanner scanner, Application sweetSystem, String signInEmail, String signInPassword) {
        int roles = sweetSystem.login.getRoles();
        String welcomeMsg = "WELCOME TO SWEET MANAGEMENT SYSTEM";

        switch (roles) {
            case 0 -> {
                LOGGER.info(welcomeMsg);
                sweetSystem.setUser(signInEmail, signInPassword, ADMIN_STRING);
                adminDashboard(scanner, sweetSystem);
            }
            case 1 -> {
                sweetSystem.setUser(signInEmail, signInPassword, STORE_OWNER_STRING);
                LOGGER.info(welcomeMsg);
                handleStoreOwnerOptions(scanner, sweetSystem);
            }
            case 2 -> {
                sweetSystem.setUser(signInEmail, signInPassword, SUPPLIER_STRING);
                LOGGER.info(welcomeMsg);
                handleSupplierOptions(scanner, sweetSystem);
            }
            default -> LOGGER.info("Invalid role! Please try again.");
        }
    }

    private static void adminDashboard(Scanner adminScanner, Application sweetSystem) {
        int adminChoice = -1;

        do {
            try {
                LOGGER.info("Admin Dashboard");
                LOGGER.info("1-Manage Users\n2-View Reports\n3-Manage Content\n4-Main Menu\n5-Sign out");
                adminChoice = adminScanner.nextInt();
                adminScanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                adminScanner.nextLine();
                continue;
            }

            switch (adminChoice) {
                case 1 -> manageUsers(adminScanner, sweetSystem);
                case 2 -> viewReports(sweetSystem);
                case 3 -> manageContent(adminScanner, sweetSystem);
                case 4 -> mainMenu(adminScanner, sweetSystem);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (adminChoice != 5);
    }
    private static void mainMenu(Scanner scanner, Application sweetSystem) {
        int mainChoice = -1;

        do {
            try {
                LOGGER.info("Main Menu");
                LOGGER.info("1-Admin Dashboard\n2-Customer Dashboard\n3-Installer Dashboard\n4-Sign out");
                mainChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }

            switch (mainChoice) {
                case 1 -> adminDashboard(scanner, sweetSystem);
                case 2 -> customerDashboard(scanner, sweetSystem);
                case 3 -> installerDashboard(scanner, sweetSystem);
                case 4 -> signOut();
                default -> invalidChoice();
            }
        } while (mainChoice != 4);
    }

    // Customer Dashboard method
    private static void customerDashboard(Scanner scanner, Application sweetSystem) {
        int customerChoice = -1;

        do {
            try {
                LOGGER.info("Customer Dashboard");
                LOGGER.info("1-Order Products\n2-View Orders\n3-Provide Feedback\n4-Main Menu\n5-Sign out");
                customerChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }

            switch (customerChoice) {
                case 1 -> orderProducts(scanner, sweetSystem);
                case 2 -> viewOrders(sweetSystem);
                case 3 -> provideFeedback(scanner, sweetSystem);
                case 4 -> mainMenu(scanner, sweetSystem);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (customerChoice != 5);
    }

    // Installer Dashboard method
    private static void installerDashboard(Scanner scanner, Application sweetSystem) {
        int installerChoice = -1;

        do {
            try {
                LOGGER.info("Installer Dashboard");
                LOGGER.info("1-Install Product\n2-View Installation Requests\n3-Provide Feedback\n4-Main Menu\n5-Sign out");
                installerChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }

            switch (installerChoice) {
                case 1 -> installProduct(scanner, sweetSystem);
                case 2 -> viewInstallationRequests(sweetSystem);
                case 3 -> provideFeedback(scanner, sweetSystem);
                case 4 -> mainMenu(scanner, sweetSystem);  // Go back to the Main Menu
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (installerChoice != 5);
    }


    private static void orderProducts(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Ordering products...");

        // Display available products
        List<Products> products = sweetSystem.getAvailableProducts();
        if (products.isEmpty()) {
            LOGGER.info("No products available.");
            return;
        }

        LOGGER.info("Available products:");
        for (int i = 0; i < products.size(); i++) {
            LOGGER.info((i + 1) + "- " + products.get(i).getProductName() + " - " + products.get(i).getProductPrice() + " USD");
        }

        // Prompt user to select a product to order
        LOGGER.info("Enter the number of the product you want to order:");
        int productIndex;
        try {
            productIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
            return;
        }

        if (productIndex < 0 || productIndex >= products.size()) {
            LOGGER.warning("Invalid product selection.");
            return;
        }

        Products selectedProduct = products.get(productIndex);

        // Prompt user to enter quantity
        LOGGER.info("Enter the quantity:");
        int quantity;
        try {
            quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
            return;
        }

        if (quantity <= 0) {
            LOGGER.warning("Quantity must be greater than zero.");
            return;
        }

        // Create order and add to customer's order list
        Order order = new Order(selectedProduct, quantity);
        sweetSystem.placeOrder(order);

        LOGGER.info("Order placed successfully! " + quantity + " x " + selectedProduct.getProductName());
    }


    private static void viewOrders(Application sweetSystem) {
        LOGGER.info("Viewing orders...");

        List<Order> orders = sweetSystem.getCustomerOrders();
        if (orders.isEmpty()) {
            LOGGER.info("No orders have been placed yet.");
            return;
        }

        LOGGER.info("Your Orders:");
        for (Order order : orders) {
            LOGGER.info(order.toString());
        }
    }


    private static void installProduct(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Installing product...");


        List<InstallationRequest> installationRequests = sweetSystem.getInstallationRequests();
        if (installationRequests.isEmpty()) {
            LOGGER.info("No installation requests available.");
            return;
        }

        LOGGER.info("Installation Requests:");
        for (int i = 0; i < installationRequests.size(); i++) {
            LOGGER.info((i + 1) + "- " + installationRequests.get(i).toString());
        }

        // Prompt user to select an installation request
        LOGGER.info("Enter the number of the request to mark as installed:");
        int requestIndex;
        try {
            requestIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
            return;
        }

        if (requestIndex < 0 || requestIndex >= installationRequests.size()) {
            LOGGER.warning("Invalid request selection.");
            return;
        }

        Order selectedRequest = installationRequests.get(requestIndex).getOrder();
        sweetSystem.markAsInstalled(selectedRequest);

       // LOGGER.info("Product marked as installed: " + selectedRequest.getProduct().getName());
    }


    private static void viewInstallationRequests(Application sweetSystem) {
        LOGGER.info("Viewing installation requests...");

        List<InstallationRequest> installationRequests = sweetSystem.getInstallationRequests();
        if (installationRequests.isEmpty()) {
            LOGGER.info("No installation requests available.");
            return;
        }

        LOGGER.info("Pending Installation Requests:");
        for (InstallationRequest request : installationRequests) {
            LOGGER.info(request.toString());
        }
    }


    private static void provideFeedback(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Providing feedback...");

        LOGGER.info("Enter your feedback:");
        String feedback = scanner.nextLine();

        sweetSystem.submitFeedback(feedback);
        LOGGER.info("Thank you for your feedback!");
    }



    private static void manageUsers(Scanner adminScanner, Application sweetSystem) {
        LOGGER.info("Manage Users");
        // Implement user management logic
        LOGGER.info(STRING);
    }

    private static void viewReports(Application sweetSystem) {
        LOGGER.info("View Reports");
        // Implement report viewing logic
        LOGGER.info(STRING);
    }

    private static void manageContent(Scanner adminScanner, Application sweetSystem) {
        LOGGER.info("Manage Content");
        // Implement content management logic
        LOGGER.info(STRING);
    }

    private static void handleStoreOwnerOptions(Scanner scanner, Application sweetSystem) {
        int select;

        while (true) {
            LOGGER.info("Choose an option:\n1. Manage Inventory\n2. View Sales\n3. Exit");
            select = scanner.nextInt();

            if (select == 1) {
                // Implement inventory management logic
            } else if (select == 2) {
                // Implement sales viewing logic
            } else if (select == 3) {
                break;
            } else {
                LOGGER.info(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private static void handleSupplierOptions(Scanner scanner, Application sweetSystem) {
        int select;

        while (true) {
            LOGGER.info("Choose an option:\n1. Manage Supplies\n2. View Orders\n3. Exit");
            select = scanner.nextInt();

            if (select == 1) {
                // Implement supplies management logic
            } else if (select == 2) {
                // Implement orders viewing logic
            } else if (select == 3) {
                break;
            } else {
                LOGGER.info(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private static void signOut() {
        LOGGER.info("Sign Out");
        LOGGER.info(STRING);
    }

    private static void invalidChoice() {
        LOGGER.info("Invalid choice! Please try again.");
        LOGGER.info(STRING);
    }
}
