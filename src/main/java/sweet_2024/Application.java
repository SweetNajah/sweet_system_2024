package sweet_2024;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.logging.*;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private boolean loggedIn;
    Login login;
    private User newUser;
    Report report;
    Scanner scanner = new Scanner(System.in);
    private Feedback feedbackSystem;
    private RecipeMenu recipeMenu;
    private List<User> users;
    private List<Products> availableProducts;
    private List<Order> customerOrders;
    private List<Order> installationRequests;
    private List<RecipeMenu> recipes;
    private List<Post> posts;
    private List<Feedback> feedbackList;
    private List<Supply> supplies;
    private List<String> dessertRecipes;

    public Application() {
        this.loggedIn = false;
        this.newUser = new User("ali55@gmail.com", "147852", "Customer");
        this.login = new Login(newUser);
        this.report=new Report();
        this.feedbackSystem = new Feedback();
        this.recipes = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.supplies = new ArrayList<>();
        this.supplies = new ArrayList<>();
        this.installationRequests = new ArrayList<>();
        this.availableProducts = new ArrayList<>();
        this.customerOrders = new ArrayList<>();
        this.users = new ArrayList<>();

        this.availableProducts.add(new Products("Chocolate Bar", 50, 1.99));
        this.availableProducts.add(new Products("Vanilla Cake", 30, 15.00));
        this.availableProducts.add(new Products("Candy", 100, 0.10));
        this.supplies.add(new Supply("Chocolate Cake", 20, 300.00));
        this.supplies.add(new Supply("Candy Pack", 50, 100.00));
        this.supplies.add(new Supply("Sugar", 100, "Pending"));
        this.supplies.add(new Supply("Flour", 200, "Approved"));

        this.dessertRecipes = new ArrayList<>();
        this.dessertRecipes.add("Chocolate Cake");
        this.dessertRecipes.add("Vanilla Pudding");
        this.dessertRecipes.add("Strawberry Shortcake");


    }

    public List<Order> getInstallationRequests() {
        return installationRequests;
    }

    public void markAsInstalled(Order requestId) {
        for (Order request : installationRequests) {
            if (request.getOrderId() == requestId.getOrderId()) {
                request.setInstalled(true);
                LOGGER.log(Level.INFO, "Installation request {0} marked as installed.", requestId.getOrderId());
                return;
            }
        }
        LOGGER.warning("Installation request not found.");
    }

    public boolean submitFeedback(String feedbackMessage, User user, Products product, int rating) {
        Feedback feedback = new Feedback(user, product, feedbackMessage, rating);
        feedbackList.add(feedback);
        LOGGER.info("Feedback submitted successfully!");
        return true;
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
        LOGGER.info("Enter your email: ");
        String email = scanner.nextLine();
        LOGGER.info("Enter your password: ");
        String password = scanner.nextLine();

        setUser(email, password, "");
        if (login.login()) {
            LOGGER.info("Login successful!");
            loggedIn = true;
            LOGGER.info("User logged in: " + email);
        } else {
            System.out.println("Login failed.");
            LOGGER.warning("Failed login attempt for user: " + email);
        }
    }

    private void handleSignUp() {
        LOGGER.info("Enter your email: ");
        String email = scanner.nextLine();
        LOGGER.info("Enter your password: ");
        String password = scanner.nextLine();
        LOGGER.info("Enter your user type: ");
        String type = scanner.nextLine();

        if (isValidEmail(email)) {
            setUser(email, password, type);
            SignUp signUp = new SignUp(newUser, login);
            if (signUp.createAccount()) {
                LOGGER.info("Account created successfully!");
                LOGGER.info("New account created for user: " + email);
            } else {
                LOGGER.info("Account creation failed.");
                LOGGER.warning("Failed account creation attempt for user: " + email);
            }
        } else {
            LOGGER.warning("Invalid email format.");
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public void startApplication() {
        LOGGER.info("Welcome to Sweet Management System!");
        LOGGER.info("Please choose an option:");
        LOGGER.info("1. Log In");
        LOGGER.info("2. Sign Up");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleSignUp();
                break;
            default:
                LOGGER.warning("Invalid choice.");
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

    public double generateProfitReport() {
        double totalProfits = 0.0;

        for (Order order : customerOrders) {
            totalProfits += order.getTotalPrice();
        }

        return totalProfits;
    }

    public List<Products> getBestSellingProducts() {
        Map<Products, Integer> productSalesCount = new HashMap<>();

        for (Order order : customerOrders) {
            for (Products product : order.getOrderedProducts()) {
                productSalesCount.put(product, productSalesCount.getOrDefault(product, 0) + 1);
            }
        }

        List<Products> bestSellers = new ArrayList<>();
        int maxSales = 0;
        for (Map.Entry<Products, Integer> entry : productSalesCount.entrySet()) {
            if (entry.getValue() > maxSales) {
                bestSellers.clear();
                bestSellers.add(entry.getKey());
                maxSales = entry.getValue();
            } else if (entry.getValue() == maxSales) {
                bestSellers.add(entry.getKey());
            }
        }

        return bestSellers;
    }

    public boolean addRecipe(RecipeMenu recipe) {
        recipes.add(recipe);
        return true;
    }

    public List<RecipeMenu> getRecipes() {
        return recipes;
    }

    public boolean removeRecipe(String name) {
        return removeItemByName(recipes, name, RecipeMenu::getName);

    }

    private <T> T findItemByName(List<T> itemList, String name, Function<T, String> getNameFunc) {
        for (T item : itemList) {
            if (getNameFunc.apply(item).equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }


    public RecipeMenu findRecipeByName(String name) {
        return findItemByName(recipes, name, RecipeMenu::getName);
    }
    public Post findPostByTitle(String title) {
        return findItemByName(posts, title, Post::getTitle);
    }


    public void addPost(Post post) {
        posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }

    private <T> boolean removeItemByName(List<T> itemList, String name, Function<T, String> getNameFunc) {
        Iterator<T> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (getNameFunc.apply(item).equalsIgnoreCase(name)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removePost(String title) {
        return removeItemByName(posts, title, Post::getTitle);
    }




    public List<Feedback> getFeedback() {
        return feedbackList;
    }

    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }

    public Feedback findFeedbackById(int id) {
        for (Feedback feedback : feedbackList) {
            if (feedback.getId() == id) {
                return feedback;
            }
        }
        return null;
    }

    public void addInventoryItem(Products item) {
        availableProducts.add(item);
        LOGGER.info("Inventory item added: " + item.getName());
    }

    public List<Products> getInventory() {
        return availableProducts;
    }

    public boolean removeInventoryItem(String itemName) {
        for (Products item : availableProducts) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                availableProducts.remove(item);
                LOGGER.info("Inventory item removed: " + itemName);
                return true;
            }
        }
        LOGGER.warning("No inventory item found with the name: " + itemName);
        return false;
    }

    public List<Products> getInventoryItems() {
        return availableProducts;
    }

    public void listInventoryItems() {
        if (availableProducts.isEmpty()) {
            LOGGER.info("No inventory items available.");
        } else {
            LOGGER.info("Listing all inventory items:");
            for (Products item : availableProducts) {
                LOGGER.info(item.toString());
            }
        }
    }

    public Products findInventoryItemByName(String name) {
        for (Products item : availableProducts) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<Supply> getSales() {
        return new ArrayList<>(this.supplies);
    }

    public List<Order> getOrders() {
        return customerOrders;
    }

    public void addOrder(Order order) {
        if (order != null) {
            customerOrders.add(order);
            LOGGER.info("Order added successfully.");
        } else {
            LOGGER.warning("Failed to add order: Order is null.");
        }
    }

    public Order findOrderById(int orderId) {
        for (Order order : customerOrders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        LOGGER.warning("No order found with ID: " + orderId);
        return null;
    }
    public void addUser(User user) {
        if (user != null) {
            users.add(user);
            LOGGER.info("User added successfully.");
        } else {
            LOGGER.warning("Failed to add user: User is null.");
        }
    }


    public User findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            LOGGER.warning("Invalid email input.");
            return null;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }

        LOGGER.info("User not found with email: " + email);
        return null;
    }

    public void addSupply(Supply supply) {
        if (supply != null) {
            supplies.add(supply);
            LOGGER.info("Supply added: " + supply.toString());
        } else {
            LOGGER.warning("Failed to add supply: Supply is null.");
        }
    }

    public boolean removeSupply(String name) {
        if (name == null || name.isEmpty()) {
            LOGGER.warning("No supply name provided to remove.");
            return false;
        }

        for (Supply supply : supplies) {
            if (supply.getName().equalsIgnoreCase(name)) {
                supplies.remove(supply);
                LOGGER.info("Supply removed: " + supply.toString());
                return true;
            }
        }
        LOGGER.warning("Supply not found with name: " + name);
        return false;
    }

    public Supply findSupplyByName(String name) {
        if (name == null || name.isEmpty()) {
            LOGGER.warning("No supply name provided to search.");
            return null;
        }

        for (Supply supply : supplies) {
            if (supply.getName().equalsIgnoreCase(name)) {
                LOGGER.info("Supply found: " + supply.toString());
                return supply;
            }
        }
        LOGGER.warning("No supply found with name: " + name);
        return null;
    }

    public List<Supply> getSupplyRequests() {
        return supplies;
    }

    public List<User> getUsers() {
        return users;
    }

    public static boolean printTextToFile(String fileName, String text) {
        try(FileWriter writer = new FileWriter(fileName)) {
            writer.write(text);
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    public boolean report(String reportType, String filename) {
        String reportContent = generateReportContent(reportType);
        if (reportContent.isEmpty()) {
            LOGGER.warning("Invalid report type: " + reportType);
            return false;
        }

        return printTextToFile(filename, reportContent);
    }

    private String generateReportContent(String reportType) {
        switch (reportType) {
            case "Sales":
                return generateSalesReport();
            case "Product rates":
                return generateProductRatesReport();
            case "Category products":
                return generateCategoryProductsReport();
            case "Rates and reviews":
                return generateRatesAndReviewsReport();
            default:
                return "";
        }
    }
    private String generateSalesReport() {
        StringBuilder report = new StringBuilder();
        report.append("Sales Report\n");
        report.append("============\n");
        for (Supply sale : supplies) {
            report.append(sale.toString()).append("\n");
        }
        return report.toString();
    }

    private String generateProductRatesReport() {
        StringBuilder report = new StringBuilder();
        report.append("Product Rates Report\n");
        report.append("====================\n");
        for (Products product : availableProducts) {
            report.append(product.getName()).append(": ").append(product.getProductRating()).append("\n");
        }
        return report.toString();
    }

    private String generateCategoryProductsReport() {
        StringBuilder report = new StringBuilder();
        report.append("Category Products Report\n");
        report.append("========================\n");
        report.append("Category: Sweets\n");
        for (Products product : availableProducts) {
            if (product.getCategory().equals("Sweets")) {
                report.append(product.getName()).append("\n");
            }
        }
        return report.toString();
    }

    private String generateRatesAndReviewsReport() {
        StringBuilder report = new StringBuilder();
        report.append("Rates and Reviews Report\n");
        report.append("========================\n");
        for (Feedback feedback : feedbackList) {
            report.append("Product: ").append(feedback.getProduct().getName()).append("\n");
            report.append("Rating: ").append(feedback.getRating()).append("\n");
            report.append("Review: ").append(feedback.getFeedbackMessage()).append("\n\n");
        }
        return report.toString();
    }


    public String navigateToFeedbackSystem() {
        if (feedbackSystem != null) {
            return "Navigated to the feedback system.";
        } else {
            return "Failed to navigate to the feedback system.";
        }
    }

    public List<String> getDessertRecipes() {
        return dessertRecipes != null ? dessertRecipes : new ArrayList<>();
    }

    public String navigateToRecipesMenu() {
        return "Navigated to the recipes menu.";
    }
}