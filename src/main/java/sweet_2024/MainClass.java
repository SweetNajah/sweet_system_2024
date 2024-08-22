package sweet_2024;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

public class MainClass {
    private static boolean adminExists = false;
    private static final String ADMIN_STRING = "Admin";
    private static final String STORE_OWNER_STRING = "Store Owner";
    private static final String SUPPLIER_STRING = "Supplier";
    private static final String CUSTOMER_STRING = "Customer";
    private static final String INVALID_INPUT_MESSAGE = "Invalid Input";
    private static final String INVALID_INFORMATION_PLEASE_TRY_AGAIN = "Invalid information! Please try again.";
    private static final String STRING = "*********************************************************************";
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
        LOGGER.info("Enter your role (Admin/Store Owner/Supplier/Customer): ");
        String role = scanner.nextLine();
        if (ADMIN_STRING.equalsIgnoreCase(role) && adminExists) {
            LOGGER.warning("Admin account already exists! Cannot create another Admin account.");
            return;
        }
        if (sweetSystem.login.emailValidator(email)) {
            User newUser = new User(email, password, role);
            sweetSystem.addUser(newUser);
            LOGGER.info("User Created Successfully");

            Mailing mailing = new Mailing(email);
            String subject = "Welcome to Sweet Management System";
            String text = "Dear " + role + ",\n\nThank you for signing up with us! We are excited to have you on board.";
            mailing.sendEmail(subject, text);

            if (ADMIN_STRING.equalsIgnoreCase(role)) {
                adminExists = true;
            }
            sendEmailToUser(email);
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }

    private static void signIn(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter your email: ");
        String signInEmail = scanner.nextLine();
        LOGGER.info("Enter your password: ");
        String signInPassword = scanner.nextLine();
        User user = sweetSystem.findUserByEmail(signInEmail);
        if (user != null && user.getPassword().equals(signInPassword)) {
            sweetSystem.login.setUser(user);

            Mailing mailing = new Mailing(signInEmail);
            mailing.sendVerificationCode();
            LOGGER.info("Verification code sent to " + signInEmail);
            LOGGER.info("The verification code is: " + mailing.verificationCode);//show Verification
            LOGGER.info("Enter the verification code sent to your email: ");
            int enteredCode = scanner.nextInt();
            scanner.nextLine();

            if (enteredCode == mailing.verificationCode) {
                LOGGER.info("Verification successful! You are now logged in.");
                handleRoles(scanner, sweetSystem, signInEmail, signInPassword);
            } else {
                LOGGER.warning("Invalid verification code! Please try again.");
            }
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }


    private static void sendEmailToUser(String email) {
        Mailing mailing = new Mailing(email);
        mailing.sendEmail("Welcome to Sweet Management System", "Thank you for signing up!");
        LOGGER.info("Email sent to " + email);
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
                case 4 -> mainMenu(scanner, sweetSystem);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (installerChoice != 5);
    }

    private static void orderProducts(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Ordering products...");
        List<Products> products = sweetSystem.getAvailableProducts();
        if (products == null || products.isEmpty()) {
            LOGGER.info("No products available.");
            return;
        }
        LOGGER.info("Available products:");
        for (int i = 0; i < products.size(); i++) {
            LOGGER.info((i + 1) + "- " + products.get(i).getName() + " - " + products.get(i).getPrice() + " USD");
        }
        LOGGER.info("Enter the number of the product you want to order:");
        int productIndex;
        try {
            productIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }
        if (productIndex < 0 || productIndex >= products.size()) {
            LOGGER.warning("Invalid product selection.");
            return;
        }
        Products selectedProduct = products.get(productIndex);
        LOGGER.info("Enter the quantity:");
        int quantity;
        try {
            quantity = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }
        if (quantity <= 0) {
            LOGGER.warning("Quantity must be greater than zero.");
            return;
        }
        Order order = new Order(selectedProduct, quantity);
        sweetSystem.placeOrder(order);
        LOGGER.info("Order placed successfully! " + quantity + " x " + selectedProduct.getName());
    }

    private static void installProduct(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Installing product...");
        List<Order> installationRequests = sweetSystem.getInstallationRequests();
        if (installationRequests == null || installationRequests.isEmpty()) {
            LOGGER.info("No installation requests available.");
            return;
        }
        LOGGER.info("Installation Requests:");
        for (int i = 0; i < installationRequests.size(); i++) {
            LOGGER.info((i + 1) + "- " + installationRequests.get(i).toString());
        }
        LOGGER.info("Enter the number of the request to mark as installed:");
        int requestIndex;
        try {
            requestIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } catch (InputMismatchException e) {
            LOGGER.warning("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }
        if (requestIndex < 0 || requestIndex >= installationRequests.size()) {
            LOGGER.warning("Invalid request selection.");
            return;
        }
        Order selectedRequest = installationRequests.get(requestIndex).getOrder();
        sweetSystem.markAsInstalled(selectedRequest);
        LOGGER.info("Product marked as installed: " + selectedRequest.getProductName());
    }

    private static void viewInstallationRequests(Application sweetSystem) {
        LOGGER.info("Viewing installation requests...");
        List<Order> installationRequests = sweetSystem.getInstallationRequests();
        if (installationRequests == null || installationRequests.isEmpty()) {
            LOGGER.info("No installation requests available.");
            return;
        }
        LOGGER.info("Pending Installation Requests:");
        for (Order request : installationRequests) {
            LOGGER.info(request.toString());
        }
    }

    private static void provideFeedback(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Providing feedback...");
        User user = selectUser(scanner, sweetSystem);
        Products product = new Products();
        LOGGER.info("Enter your feedback message:");
        String feedbackMessage = scanner.nextLine();
        LOGGER.info("Enter your rating (0-5):");
        int rating;
        try {
            rating = Integer.parseInt(scanner.nextLine());
            if (rating < 0 || rating > 5) {
                throw new NumberFormatException("Rating must be between 0 and 5.");
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid rating. Please enter a number between 0 and 5.");
            return;
        }
        boolean feedbackSuccess = sweetSystem.submitFeedback(feedbackMessage, user, product, rating);
        if (feedbackSuccess) {
            LOGGER.info("Feedback submitted successfully.");
        } else {
            LOGGER.warning("Failed to submit feedback. Please try again.");
        }
    }

    private static User selectUser(Scanner scanner, Application sweetSystem) {
        List<User> users = sweetSystem.getUsers();
        if (users == null || users.isEmpty()) {
            LOGGER.info("No users available.");
            return null;
        }
        LOGGER.info("Select a user:");
        for (int i = 0; i < users.size(); i++) {
            LOGGER.info((i + 1) + ": " + users.get(i).getEmail() + " - " + users.get(i).getRole());
        }
        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        if (choice >= 0 && choice < users.size()) {
            return users.get(choice);
        } else {
            LOGGER.warning("Invalid choice. Please try again.");
            return selectUser(scanner, sweetSystem);
        }
    }

    private static Products selectProduct(Scanner scanner, Application sweetSystem) {
        List<Products> inventoryItems = sweetSystem.getInventoryItems();
        if (inventoryItems == null || inventoryItems.isEmpty()) {
            LOGGER.info("No products available.");
            return null;
        }
        LOGGER.info("Select a product:");
        for (int i = 0; i < inventoryItems.size(); i++) {
            LOGGER.info((i + 1) + ": " + inventoryItems.get(i).getName() + " - $" + inventoryItems.get(i).getPrice());
        }
        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        if (choice >= 0 && choice < inventoryItems.size()) {
            return inventoryItems.get(choice);
        } else {
            LOGGER.warning("Invalid choice. Please try again.");
            return selectProduct(scanner, sweetSystem);
        }
    }

    private static void manageUsers(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-List Users\n2-Add User\n3-Remove User\n4-Update User\n5-Back to Admin Dashboard");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> listUsers(scanner, sweetSystem);
                case 2 -> addUser(scanner, sweetSystem);
                case 3 -> removeUser(scanner, sweetSystem);
                case 4 -> updateUser(scanner, sweetSystem);
                case 5 -> LOGGER.info("Returning to Admin Dashboard");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 5);
    }

    private static void listUsers(Scanner scanner, Application sweetSystem) {
        List<User> users = sweetSystem.getUsers();
        if (users == null || users.isEmpty()) {
            LOGGER.info("No users available.");
            return;
        }
        LOGGER.info("Listing users:");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            LOGGER.info((i + 1) + ". Email: " + user.getEmail() + ", Role: " + user.getRole());
        }
        LOGGER.info("Enter any key to return to the previous menu:");
        scanner.nextLine();
    }


    private static void addUser(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter email for new user:");
        String email = scanner.nextLine();
        LOGGER.info("Enter password for new user:");
        String password = scanner.nextLine();
        LOGGER.info("Enter role for new user (Admin/Store Owner/Supplier):");
        String role = scanner.nextLine();
        if (sweetSystem.login.emailValidator(email)) {
            sweetSystem.login.addUser(new User(email, password, role));
            LOGGER.info("User added successfully.");
        } else {
            LOGGER.warning(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }

    private static void removeUser(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter email of user to remove:");
        String email = scanner.nextLine();
        boolean removed = sweetSystem.login.removeUser(email);
        if (removed) {
            LOGGER.info("User removed successfully.");
        } else {
            LOGGER.warning("User not found. Please try again.");
        }
    }

    private static void updateUser(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter email of user to update:");
        String email = scanner.nextLine();
        User user = sweetSystem.login.findUserByEmail(email);
        if (user == null) {
            LOGGER.warning("User not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new password for user (leave blank to keep current password):");
        String password = scanner.nextLine();
        LOGGER.info("Enter new role for user (Admin/Store Owner/Supplier) (leave blank to keep current role):");
        String role = scanner.nextLine();
        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        if (!role.isEmpty()) {
            user.setRole(role);
        }
        LOGGER.info("User updated successfully.");
    }

    private static void viewReports(Application sweetSystem) {
        LOGGER.info("Generating reports...");
        double profits = sweetSystem.generateProfitReport();
        LOGGER.info("Total Profits: " + profits + " USD");
        List<Products> bestSellers = sweetSystem.getBestSellingProducts();
        if (!bestSellers.isEmpty()) {
            LOGGER.info("Best Selling Products:");
            for (Products product : bestSellers) {
                LOGGER.info(product.toString());
            }
        } else {
            LOGGER.info("No sales data available.");
        }
    }

    private static void manageContent(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-Manage Recipes\n2-Manage Posts\n3-Manage Feedback\n4-Back to Admin Dashboard");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> manageRecipes(scanner, sweetSystem);
                case 2 -> managePosts(scanner, sweetSystem);
                case 3 -> manageFeedback(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Admin Dashboard");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void manageRecipes(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-Add Recipe\n2-Remove Recipe\n3-Update Recipe\n4-Back to Manage Content");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> addRecipe(scanner, sweetSystem);
                case 2 -> removeRecipe(scanner, sweetSystem);
                case 3 -> updateRecipe(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Manage Content");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void addRecipe(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter recipe name:");
        String name = scanner.nextLine();
        LOGGER.info("Enter recipe ingredients:");
        String ingredients = scanner.nextLine();
        LOGGER.info("Enter recipe steps:");
        String steps = scanner.nextLine();
        RecipeMenu recipe = new RecipeMenu(name, ingredients, steps);
        sweetSystem.addRecipe(recipe);
        LOGGER.info("Recipe added successfully.");
    }

    private static void removeRecipe(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter recipe name to remove:");
        String name = scanner.nextLine();
        boolean removed = sweetSystem.removeRecipe(name);
        if (removed) {
            LOGGER.info("Recipe removed successfully.");
        } else {
            LOGGER.warning("Recipe not found. Please try again.");
        }
    }

    private static void updateRecipe(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter recipe name to update:");
        String name = scanner.nextLine();
        RecipeMenu recipe = sweetSystem.findRecipeByName(name);
        if (recipe == null) {
            LOGGER.warning("Recipe not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new ingredients (leave blank to keep current):");
        String ingredients = scanner.nextLine();
        LOGGER.info("Enter new steps (leave blank to keep current):");
        String steps = scanner.nextLine();
        if (!ingredients.isEmpty()) {
            recipe.setIngredients(ingredients);
        }
        if (!steps.isEmpty()) {
            recipe.setSteps(steps);
        }
        LOGGER.info("Recipe updated successfully.");
    }

    private static void managePosts(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-Add Post\n2-Remove Post\n3-Update Post\n4-Back to Manage Content");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> addPost(scanner, sweetSystem);
                case 2 -> removePost(scanner, sweetSystem);
                case 3 -> updatePost(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Manage Content");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void addPost(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter post title:");
        String title = scanner.nextLine();
        LOGGER.info("Enter post content:");
        String content = scanner.nextLine();
        Post post = new Post(title, content);
        sweetSystem.addPost(post);
        LOGGER.info("Post added successfully.");
    }

    private static void removePost(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter post title to remove:");
        String title = scanner.nextLine();
        boolean removed = sweetSystem.removePost(title);
        if (removed) {
            LOGGER.info("Post removed successfully.");
        } else {
            LOGGER.warning("Post not found. Please try again.");
        }
    }

    private static void updatePost(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter post title to update:");
        String title = scanner.nextLine();
        Post post = sweetSystem.findPostByTitle(title);
        if (post == null) {
            LOGGER.warning("Post not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new content (leave blank to keep current):");
        String content = scanner.nextLine();
        if (!content.isEmpty()) {
            post.setContent(content);
        }
        LOGGER.info("Post updated successfully.");
    }

    private static void manageFeedback(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-View Feedback\n2-Respond to Feedback\n3-Back to Manage Content");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> viewFeedback(sweetSystem);
                case 2 -> respondToFeedback(scanner, sweetSystem);
                case 3 -> LOGGER.info("Returning to Manage Content");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 3);
    }

    private static void viewFeedback(Application sweetSystem) {
        List<Feedback> feedbackList = sweetSystem.getFeedback();
        if (feedbackList == null || feedbackList.isEmpty()) {
            LOGGER.info("No feedback available.");
        } else {
            LOGGER.info("Listing feedback:");
            for (Feedback feedback : feedbackList) {
                LOGGER.info(feedback.toString());
            }
        }
    }

    private static void respondToFeedback(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter feedback ID to respond:");
        int feedbackId = scanner.nextInt();
        scanner.nextLine();
        Feedback feedback = sweetSystem.findFeedbackById(feedbackId);
        if (feedback == null) {
            LOGGER.warning("Feedback not found. Please try again.");
            return;
        }
        LOGGER.info("Enter your response:");
        String response = scanner.nextLine();
        feedback.setResponse(response);
        LOGGER.info("Response recorded successfully.");
    }

    private static void handleStoreOwnerOptions(Scanner scanner, Application sweetSystem) {
        int storeOwnerChoice = -1;
        do {
            try {
                LOGGER.info("Store Owner Dashboard");
                LOGGER.info("1-Manage Inventory\n2-View Sales\n3-Manage Orders\n4-Main Menu\n5-Sign out");
                storeOwnerChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (storeOwnerChoice) {
                case 1 -> manageInventory(scanner, sweetSystem);
                case 2 -> viewSales(sweetSystem);
                case 3 -> manageOrders(scanner, sweetSystem);
                case 4 -> mainMenu(scanner, sweetSystem);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (storeOwnerChoice != 5);
    }

    private static void handleSupplierOptions(Scanner scanner, Application sweetSystem) {
        int supplierChoice = -1;
        do {
            try {
                LOGGER.info("Supplier Dashboard");
                LOGGER.info("1-Manage Supplies\n2-View Requests\n3-Provide Feedback\n4-Main Menu\n5-Sign out");
                supplierChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (supplierChoice) {
                case 1 -> manageSupplies(scanner, sweetSystem);
                case 2 -> viewRequests(sweetSystem);
                case 3 -> provideFeedback(scanner, sweetSystem);
                case 4 -> mainMenu(scanner, sweetSystem);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (supplierChoice != 5);
    }

    private static void signOut() {
        LOGGER.info("Sign Out");
        LOGGER.info(STRING);
    }

    private static void invalidChoice() {
        LOGGER.info("Invalid choice! Please try again.");
        LOGGER.info(STRING);
    }


    private static void manageInventory(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-Add Inventory Item\n2-Remove Inventory Item\n3-Update Inventory Item\n4-Back to Store Dashboard");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> addInventoryItem(scanner, sweetSystem);
                case 2 -> removeInventoryItem(scanner, sweetSystem);
                case 3 -> updateInventoryItem(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Store Dashboard");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void addInventoryItem(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter item name:");
        String name = scanner.nextLine();
        LOGGER.info("Enter item quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        LOGGER.info("Enter item price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Products item = new Products(name, quantity, price);
        sweetSystem.addInventoryItem(item);
        LOGGER.info("Inventory item added successfully.");
    }

    private static void removeInventoryItem(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter item name to remove:");
        String name = scanner.nextLine();
        boolean removed = sweetSystem.removeInventoryItem(name);
        if (removed) {
            LOGGER.info("Inventory item removed successfully.");
        } else {
            LOGGER.warning("Inventory item not found. Please try again.");
        }
    }

    private static void updateInventoryItem(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter item name to update:");
        String name = scanner.nextLine();
        Products item = sweetSystem.findInventoryItemByName(name);
        if (item == null) {
            LOGGER.warning("Inventory item not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new quantity (leave blank to keep current):");
        String quantityInput = scanner.nextLine();
        if (!quantityInput.isEmpty()) {
            int quantity = Integer.parseInt(quantityInput);
            item.setQuantity(quantity);
        }
        LOGGER.info("Enter new price (leave blank to keep current):");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            double price = Double.parseDouble(priceInput);
            item.setPrice(price);
        }
        LOGGER.info("Inventory item updated successfully.");
    }


    private static void viewSales(Application sweetSystem) {
        LOGGER.info("Generating sales report...");
        List<Supply> sales = sweetSystem.getSales();
        if (sales == null || sales.isEmpty()) {
            LOGGER.info("No sales data available.");
        } else {
            LOGGER.info("Sales Report:");
            for (Supply sale : sales) {
                LOGGER.info(sale.toString());
            }
        }
    }

    private static void manageOrders(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-View Orders\n2-Process Order\n3-Update Order\n4-Back to Store Dashboard");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> viewOrders(sweetSystem);
                case 2 -> processOrder(scanner, sweetSystem);
                case 3 -> updateOrder(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Store Dashboard");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void viewOrders(Application sweetSystem) {
        List<Order> orders = sweetSystem.getOrders();
        if (orders == null || orders.isEmpty()) {
            LOGGER.info("No orders available.");
        } else {
            LOGGER.info("Listing orders:");
            for (Order order : orders) {
                LOGGER.info(order.toString());
            }
        }
    }

    private static void processOrder(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter order ID to process:");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        Order order = sweetSystem.findOrderById(orderId);
        if (order == null) {
            LOGGER.warning("Order not found. Please try again.");
            return;
        }
        LOGGER.info("Processing order...");
        order.setStatus("Processed");
        LOGGER.info("Order processed successfully.");
    }

    private static void updateOrder(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter order ID to update:");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        Order order = sweetSystem.findOrderById(orderId);
        if (order == null) {
            LOGGER.warning("Order not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new status (leave blank to keep current):");
        String status = scanner.nextLine();
        if (!status.isEmpty()) {
            order.setStatus(status);
        }
        LOGGER.info("Order updated successfully.");
    }

    private static void viewRequests(Application sweetSystem) {
        LOGGER.info("Generating supply requests report...");
        List<Supply> requests = sweetSystem.getSupplyRequests();
        if (requests == null || requests.isEmpty()) {
            LOGGER.info("No supply requests available.");
        } else {
            LOGGER.info("Supply Requests Report:");
            for (Supply request : requests) {
                LOGGER.info(request.toString());
            }
        }
    }

    private static void manageSupplies(Scanner scanner, Application sweetSystem) {
        int choice = -1;
        do {
            LOGGER.info("1-Add Supply\n2-Remove Supply\n3-Update Supply\n4-Back to Supplier Dashboard");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning(INVALID_INPUT_MESSAGE);
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> addSupply(scanner, sweetSystem);
                case 2 -> removeSupply(scanner, sweetSystem);
                case 3 -> updateSupply(scanner, sweetSystem);
                case 4 -> LOGGER.info("Returning to Supplier Dashboard");
                default -> LOGGER.warning(INVALID_INPUT_MESSAGE);
            }
        } while (choice != 4);
    }

    private static void addSupply(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter supply name:");
        String name = scanner.nextLine();
        LOGGER.info("Enter supply quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        LOGGER.info("Enter supply price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Supply supply = new Supply(name, quantity, price);
        sweetSystem.addSupply(supply);
        LOGGER.info("Supply added successfully.");
    }

    private static void removeSupply(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter supply name to remove:");
        String name = scanner.nextLine();
        boolean removed = sweetSystem.removeSupply(name);
        if (removed) {
            LOGGER.info("Supply removed successfully.");
        } else {
            LOGGER.warning("Supply not found. Please try again.");
        }
    }

    private static void updateSupply(Scanner scanner, Application sweetSystem) {
        LOGGER.info("Enter supply name to update:");
        String name = scanner.nextLine();
        Supply supply = sweetSystem.findSupplyByName(name);
        if (supply == null) {
            LOGGER.warning("Supply not found. Please try again.");
            return;
        }
        LOGGER.info("Enter new quantity (leave blank to keep current):");
        String quantityInput = scanner.nextLine();
        if (!quantityInput.isEmpty()) {
            int quantity = Integer.parseInt(quantityInput);
            supply.setQuantity(quantity);
        }
        LOGGER.info("Enter new price (leave blank to keep current):");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            double price = Double.parseDouble(priceInput);
            supply.setPrice(price);
        }
        LOGGER.info("Supply updated successfully.");
    }
    public static void kop(){
        LOGGER.info("Nothing");
    }

}