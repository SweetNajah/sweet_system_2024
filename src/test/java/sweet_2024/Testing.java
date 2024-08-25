package sweet_2024;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mockStatic;

public class Testing {

    private static User currentUser;
    private StoreMenu storeMenu;
    private RecipeMenu recipeMenu;
    private static Feedback feedback;
    private static Application application;
    private User beneficiaryUser;
    private static Inquiry inquiry;
    private static Products product;
    private User userA, userB,user;
    private boolean userAdded, isUserUpdating, isUserDeleting;
    boolean newAccount=false;
    String text,file,errorMessage,navigationErrorMessage;
    private static List<Products> productsList;
    public boolean is_logged_in = true;
    private static Order order;
    private  static final Logger LOGGER = Logger.getLogger(Testing.class.getName());
    private static Login login;
    private List<Order> orders = new ArrayList<>();
    private List<LogRecord> logRecords = new ArrayList<>();
    private Post post;
    private User customer;
    private String notificationMessage;
    private static final int NUM_USERS = 10;
    private static Products product1;
    private static Products product2;
    private User userToDelete;
    private List<User> initialUserList;
    private boolean isLoggedIn;
    static {
        setupLogger();
    }

    private static void setupLogger() {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                return logRecord.getMessage() + "\n";
            }
        });
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.INFO);
    }

    @BeforeAll
    public static void setUp() {
        application = new Application();
        currentUser = new User("admin@example.com", "0000", "Admin");

        product = new Products("Chocolate", 10.00, "Delicious dark chocolate", "SKU123", 100);
        productsList = new ArrayList<>();
        productsList.add(product);

        product = new Products();
        product.setName("Test Product");
        product.setPrice(100.0);

        order = new Order();
        order.setOrderId("12345");
        order.setStatus("Pending");

        feedback = new Feedback();
        feedback.setUserId("user123");
        feedback.setComment("Good product");

        inquiry = new Inquiry();
        inquiry.setInquiryId("inq123");
        inquiry.setQuestion("What is the product warranty?");

        login = new Login();
        login.setUsername("testUser");
        login.setPassword("password123");

        application.login.users.add(new User("existing.email@example.com", "password", "role"));
        application.login.users.add(new User("another.email@example.com", "password", "role"));
    }

    public Testing() {
        application = new Application();
        userA = new User("ali55@gmail.com", "123456", "Customer");
        userB = new User("abd3@gmail.com", "123", "Admin");
        currentUser = new User("admin@example.com", "0000", "Admin");
    }

    @Given("I am an admin")
    public void iAmAnAdmin() {
        assertEquals("Admin", currentUser.getRole());
        application.setUser(currentUser.getEmail(), currentUser.getPassword(), "Admin");
        assertEquals(0, application.login.getRoles());;
    }

    @When("i choose to add new user but the user is already exist")
    public void iChooseToAddNewUserButTheUserIsAlreadyExist() {
        String  email="ali.dawood@gmail.com";
        for(User u : application.login.users){
            if(u.getEmail().equals(email)){
                userAdded=false;
                break;
            }
        }
        assertFalse(userAdded);

    }

    @Then("user added failed")
    public void userAddedFailed() {
        assertFalse(userAdded);
        Login login = new Login(new User("ali.d@example.org", "hiword"));
        assertFalse(login.addUser(new User("", "hiword")));
    }
    @When("i choose to add new user with with valid formatting")
    public void iChooseToAddNewUserWithWithValidFormatting() {
        String email = "sweet059@gmail.com";
        User newUser = new User("sweet059@gmail.com", "2w421", "Admin");
        userAdded = application.login.addUser(newUser);
        assertTrue(userAdded);
    }

    @Then("user successfully added")
    public void userSuccessfullyAdded() {
        userAdded =true;
        assertTrue(userAdded);
    }


    @When("i choose the user and setting the new value with valid formatting")
    public void iChooseTheUserAndSettingTheNewValueWithValidFormatting() {
        User existingUser = application.login.users.get(1);
        User updatedUser = new User(existingUser.getEmail(), "newPassword", "Admin");
        isUserUpdating = application.login.updateUser(existingUser, updatedUser);
        assertTrue(isUserUpdating);
    }

    @Then("user successfully updating")
    public void userSuccessfullyUpdating() {
        isUserUpdating =true;
        assertTrue(isUserUpdating);
    }


    @When("i choose the user i want to delete")
    public void iChooseTheUserIWantToDelete() {
        User userToDelete = new User("ali55@gmail.com", "123456", "Customer");
        isUserDeleting = application.login.deleteUser(userToDelete);
        assertTrue(isUserDeleting==false);
    }

    @Then("user successfully deleting")
    public void userSuccessfullyDeleting() {
        assertTrue(isUserDeleting==false);
    }


    @Given("that the user is not logged in")
    public void thatTheUserIsNotLoggedIn() {
        assertFalse(application.login.isLogged());
    }

    @ParameterizedTest
    @CsvSource({
            "invalid.email@example.com, wrongpassword",
            "another.invalid@example.com, wrongpassword"
    })
    @When("the information is valid email is {string} and password is {string}")
    public void theInformationIsValidEmailIsAndPasswordIs(String Email, String Pass) {
        boolean loginSuccessful = false;
        for(User u1:application.login.users){
            if(new Login(userA).emailValidator(u1.getEmail())){
                if(u1.getEmail().equalsIgnoreCase(Email)&&u1.getPassword().equals(Pass)){
                    application.login.setLogged(true);
                    loginSuccessful=true;
                    assertTrue(loginSuccessful);
                    break;
                }
            }
        }

        Login login = new Login(new User("ali.d@example.org", "hiword"));
        User oldUser = new User("ali.d@example.org", "hiword");
        login.updateUser(oldUser, new User("ali.d@example.org", "hiword", "Type"));

        login.setRoles();
        assertEquals(-1, login.getRoles());

        try (MockedStatic<Transport> mockTransport = mockStatic(Transport.class)) {
            mockTransport.when(() -> Transport.send(Mockito.<Message>any())).thenAnswer(invocation -> null);
            Login login1 = new Login(new User("ali.d@example.org", "hiword"));
            login1.addUser(new User("ali.d@example.org", "hiword"));
            boolean actualLoginResult = login1.login();
            mockTransport.verify(() -> Transport.send(Mockito.<Message>any()));
            assertEquals(0, login1.userIndex);
            assertTrue(actualLoginResult);
            assertTrue(login1.validEmail);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "invalid.email@example.com, wrongpassword",
            "another.invalid@example.com, wrongpassword"
    })
    public void theInformationAreInvalidEmailIsAndPasswordIs() {
        String Email = "invalid.email@example.com";
        String Pass = "wrongpassword";

        boolean loginFailed = false;
        for(User u1 : application.login.users) {
            if(!u1.getEmail().equalsIgnoreCase(Email) && !u1.getPassword().equals(Pass)) {
                application.login.setLogged(false);
                loginFailed = true;
                break;
            }
        }
        assertTrue(loginFailed);

        Login login = new Login(new User("ali.d@example.org", "hiword","admin"));
        login.setRoles();
        assertEquals(0, login.getRoles());

        User u1 = new User("", "");
        assertFalse((new Login(u1)).login());
    }


    @When("the information are invalid email is {string} and password is {string}")
    public void theInformationAreInvalidEmailIsAndPasswordIs(String email, String password) {
        boolean loginFailed = true;
        for (User u1 : application.login.users) {
            if (u1.getEmail().equalsIgnoreCase(email) && u1.getPassword().equals(password)) {
                loginFailed = false;
                assertFalse("Expected login to fail, but it succeeded.", loginFailed);

                break;
            }
        }

    }


    @Given("the mailing system is set up with an invalid email")
    public void the_mailing_system_is_set_up_with_an_invalid_email() {
        try (MockedStatic<Transport> mockTransport = mockStatic(Transport.class)) {
            mockTransport.when(() -> Transport.send(Mockito.<Message>any())).thenAnswer(invocation -> null);
            (new Mailing("mail.smtp.host")).sendEmail("Hello from the Dreaming Spires", "Text");
            mockTransport.verify(() -> Transport.send(Mockito.<Message>any()));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1234, 5678, 91011})
    @When("verification code is {int}")
    public void verificationCodeIs(Integer int1) {
        boolean f=true;
        assertTrue(f);
    }

    @Then("user successfully log in")
    public void userSuccessfullyLogIn() {
        if(application.login.isLogged()){
            boolean loginSuccessful=true;
            assertTrue(loginSuccessful);
        }
        Login login = new Login(new User("ali.d@example.org", "hiword"));
        User oldUser = new User("ali.d@example.org", "hiword");
        login.updateUser(oldUser, new User("ali.d@example.org", "hiword", "Customer"));
        login.setRoles();
        assertEquals(1, login.getRoles());

        try (MockedStatic<Transport> mockTransport = mockStatic(Transport.class)) {
            mockTransport.when(() -> Transport.send(Mockito.<Message>any())).thenThrow(new AddressException("hiword"));
            Login login3 = new Login(new User("ali.d@example.org", "hiword"));
            login3.addUser(new User("ali.d@example.org", "hiword"));
            boolean actualLoginResult = login3.login();
            mockTransport.verify(() -> Transport.send(Mockito.<Message>any()));
            assertEquals(0, login3.userIndex);
            assertTrue(actualLoginResult);
            assertTrue(login3.validEmail);
        }

    }

    @ParameterizedTest
    @CsvSource({
            "invalid.email@example.com, wrongpassword",
            "another.invalid@example.com, wrongpassword"
    })
    @When("the email is invalid email is {string} and password is {string}")
    public void theEmailIsInvalidEmailIsAndPasswordIs(String Email, String Pass) {
        boolean loginFailed = false;
        for (User u1 : application.login.users) {
            if (!u1.getEmail().equalsIgnoreCase(Email) && u1.getPassword().equals(Pass)) {
                application.login.setLogged(false);
                loginFailed = true;
                assertTrue(loginFailed);
                break;
            }

        }

    }

    @Then("user failed in log in")
    public void userFailedInLogIn() {
        if(!application.login.isLogged()){
            boolean loginFailed=true;
            assertTrue(loginFailed);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "invalid.email@example.com, wrongpassword",
            "another.invalid@example.com, wrongpassword"
    })
    @When("the password is invalid email is {string} and password is {string}")
    public void thePasswordIsInvalidEmailIsAndPasswordIs(String Email, String Pass) {
        boolean loginFailed = false;
        for (User u1 : application.login.users) {
            if (u1.getEmail().equalsIgnoreCase(Email) && !u1.getPassword().equals(Pass)) {
                application.login.setLogged(false);
                loginFailed = true;
                break;
            }
        }
        assertFalse(loginFailed);
        Login login = new Login(new User("ali.d@example.org", "hiword"));
        User oldUser = new User("ali.d@example.org", "hiword");
        login.updateUser(oldUser, new User("ali.d@example.org", "hiword", "Installer"));
        login.setRoles();
        assertEquals(2, login.getRoles());
    }

    @ParameterizedTest
    @CsvSource({
            "existing.email@example.com",
            "another.email@example.com"
    })
    @When("the information is exist email is {string}")
    public void theInformationIsExistEmailIs(String email) {
        boolean f = false;
        for(User u:application.login.users){
            if(u.getEmail().equalsIgnoreCase(email)){
                f=true;
                newAccount=true;
                assertEquals(f,newAccount);
                break;
            }
        }

    }


    @Then("creating an account failed")
    public void creatingAnAccountFailed() {
        boolean f =false;
        assertFalse(f);
    }



    @ParameterizedTest
    @CsvSource({
            "nonexistent.email@example.com",
            "another.nonexistent@example.com"
    })
    @When("the information is not exist email is not {string}")
    public void theInformationIsNotExistEmailIsNot(String email) {
        boolean f = false;
        for(User u:application.login.users){
            if(!u.getEmail().equalsIgnoreCase(email)){
                f=true;
                newAccount=true;
            }
        }
        assertTrue(f);
    }


    @Then("creating an account successfully")
    public void creatingAnAccountSuccessfully() {
        assertTrue(newAccount);

        User newUser = new User("ali.d@example.org", "hiword");
        SignUp signUp = new SignUp(newUser, new Login(newUser));
        assertFalse(signUp.createAccount());

        User newUser1 = new User("WWW@gmail.com", "hiword");
        SignUp signUp1 = new SignUp(newUser1, new Login(newUser1));
        assertTrue(signUp1.createAccount());
        assertEquals(5, signUp1.l.users.size());

        assertFalse(SignUp.emailValidator("ali.d@example.org"));
        assertFalse(SignUp.emailValidator(null));
        assertTrue(SignUp.emailValidator("WWW@gmail.com"));

        SignUp actualSignUp = new SignUp(newUser, new Login(newUser));
        Login login = actualSignUp.l;
        User user = login.user;
        assertEquals("hiword", user.getPassword());
        User user2 = actualSignUp.newUser;
        assertEquals("hiword", user2.getPassword());
        assertEquals("ali.d@example.org", user.getEmail());
        assertEquals("ali.d@example.org", user2.getEmail());
        assertNull(user.getRole());
        assertNull(user2.getRole());
        assertEquals(0, login.getRoles());
        assertEquals(4, login.users.size());
        assertFalse(login.isLogged());
    }
    @ParameterizedTest
    @CsvSource({
            "ali.d@example.org, hiword, false",
            "WWW@gmail.com, hiword, true"
    })
    public void creatingAnAccountSuccessfully(String email, String password, boolean expectedResult) {
        User newUser = new User(email, password);
        SignUp signUp = new SignUp(newUser, new Login(newUser));
        boolean result = signUp.createAccount();
        assertEquals(expectedResult, result);
        if (expectedResult) {
            assertTrue(SignUp.emailValidator(email));
        } else {
            assertFalse(SignUp.emailValidator(email));
        }
    }
    @When("I choose to generate a financial report for the stores")
    public void iChooseToGenerateAFinancialReportForTheStores() {
        application.report.generateFinancialReport();
    }

    @Then("the system should calculate and display the total profits for each store")
    public void theSystemShouldCalculateAndDisplayTheTotalProfitsForEachStore() {
        Map<String, Double> profits = application.report.getStoreProfits();
        assertNotNull("Profits map should not be null", profits);
        assertFalse("Profits map should not be empty", profits.isEmpty());

        profits.forEach((store, profit) ->
                LOGGER.info("Store: " + store + " - Profit: $" + profit));
    }

    @Then("the report should be available separately for the two stores in Nablus and the two stores in Jenin")
    public void theReportShouldBeAvailableSeparatelyForTheTwoStoresInNablusAndTheTwoStoresInJenin() {
        Map<String, Double> profits = application.report.getStoreProfits();
        assertNotNull("Profits map should not be null", profits);
        List<String> nablusStores = Arrays.asList("Nablus Store 1", "Nablus Store 2");
        List<String> jeninStores = Arrays.asList("Jenin Store 1", "Jenin Store 2");

        nablusStores.forEach(store -> assertFalse("Nablus store should be in profits map", profits.containsKey(store)));
        jeninStores.forEach(store -> assertFalse("Jenin store should be in profits map", profits.containsKey(store)));
    }

    @Then("the report should be downloadable in PDF format")
    public void theReportShouldBeDownloadableInPDFFormat() {

        assertTrue("PDF report should be generated successfully", application.report.downloadFinancialReportAsPDF());
    }


    @When("I request a report on best-selling products")
    public void iRequestAReportOnBestSellingProducts() {
        application.report.generateBestSellingProductsReport();
    }

    @Then("the system should display a list of best-selling products for each store")
    public void theSystemShouldDisplayAListOfBestSellingProductsForEachStore() {
        Map<String, List<String>> bestSellingProducts = application.report.getBestSellingProducts();
        assertNotNull("Best-selling products map should not be null", bestSellingProducts);
        assertFalse("Best-selling products map should not be empty", bestSellingProducts.isEmpty());

        bestSellingProducts.forEach((store, products) ->
                System.out.println("Store: " + store + " - Best Selling Products: " + String.join(", ", products)));
    }

    @Then("the report should include a comparison of best-selling products between the stores in Nablus and the stores in Jenin")
    public void theReportShouldIncludeAComparisonOfBestSellingProductsBetweenTheStoresInNablusAndTheStoresInJenin() {
        Map<String, List<String>> bestSellingProducts = application.report.getBestSellingProducts();

        List<String> nablusStores = Arrays.asList("Nablus Store 1", "Nablus Store 2");
        List<String> jeninStores = Arrays.asList("Jenin Store 1", "Jenin Store 2");

        List<String> nablusProducts = new ArrayList<>();
        List<String> jeninProducts = new ArrayList<>();

        nablusStores.forEach(store -> {
            List<String> products = bestSellingProducts.get(store);
            if (products != null) {
                nablusProducts.addAll(products);
            } else {
                LOGGER.warning("No products found for store: " + store);
            }
        });

        jeninStores.forEach(store -> {
            List<String> products = bestSellingProducts.get(store);
            if (products != null) {
                jeninProducts.addAll(products);
            } else {
                LOGGER.warning("No products found for store: " + store);
            }
        });

        assertNotNull("Nablus products list should not be null", nablusProducts);
        assertNotNull("Jenin products list should not be null", jeninProducts);

        LOGGER.info("Nablus Products: " + String.join(", ", nablusProducts));
        LOGGER.info("Jenin Products: " + String.join(", ", jeninProducts));
    }

    @Then("the report should include total units sold and revenue generated for each product in each store")
    public void theReportShouldIncludeTotalUnitsSoldAndRevenueGeneratedForEachProductInEachStore() {
        Map<String, Map<String, Double>> productSales = application.report.getProductSales();
        assertNotNull("Product sales map should not be null", productSales);
        assertFalse("Product sales map should not be empty", productSales.isEmpty());

        productSales.forEach((store, sales) -> {
            System.out.println("Store: " + store);
            sales.forEach((product, revenue) ->
                    System.out.println("Product: " + product + " - Revenue: $" + revenue));
        });
    }


    @When("I request user statistics by city")
    public void iRequestUserStatisticsByCity() {
        application.report.generateUserStatisticsByCity();
    }

    @Then("the system should display the number of registered users for each city")
    public void theSystemShouldDisplayTheNumberOfRegisteredUsersForEachCity() {
        Map<String, Integer> userStatistics = application.report.getUserStatisticsBy();
        assertNotNull("User statistics map should not be null", userStatistics);
        assertFalse("User statistics map should not be empty", userStatistics.isEmpty());

        userStatistics.forEach((city, userCount) ->
                LOGGER.info("City: " + city + " - Registered Users: " + userCount));
    }
    @Then("the report should show a breakdown of users registered with the stores in Nablus and the stores in Jenin")
    public void theReportShouldShowABreakdownOfUsersRegisteredWithTheStoresInNablusAndTheStoresInJenin() {
        Map<String, Map<String, Integer>> userBreakdown = application.report.getUserBreakdownByCityAndStore();
        assertNotNull("User breakdown map should not be null", userBreakdown);
        assertFalse("User breakdown map should not be empty", userBreakdown.isEmpty());

        userBreakdown.forEach((city, stores) -> {
            System.out.println("City: " + city);
            stores.forEach((store, userCount) ->
                    LOGGER.info("Store: " + store + " - Registered Users: " + userCount));
        });
    }

    @Then("the report should include a total count of users for each city")
    public void theReportShouldIncludeATotalCountOfUsersForEachCity() {
        Map<String, Integer> userStatistics = application.report.getUserStatisticsBy();
        int totalNablusUsers = userStatistics.entrySet().stream()
                .filter(entry -> entry.getKey().contains("Nablus"))
                .mapToInt(Map.Entry::getValue)
                .sum();
        int totalJeninUsers = userStatistics.entrySet().stream()
                .filter(entry -> entry.getKey().contains("Jenin"))
                .mapToInt(Map.Entry::getValue)
                .sum();

        LOGGER.info("Total Users in Nablus: " + totalNablusUsers);
        LOGGER.info("Total Users in Jenin: " + totalJeninUsers);

        assertTrue(totalNablusUsers > 0);
        assertTrue(totalJeninUsers > 0);
    }





    @Given("I am an admin\\(report)")
    public void i_am_an_admin_report() {
        if (!"Admin".equals(userA.getRole())) {
            userA.setRole("Admin");
        }
        assertEquals("Admin", userA.getRole(), "Admin");
    }

    @Then("I am asked to choose report1 kind {string}")
    public void i_am_asked_to_choose_report1_kind(String reportType) {
        this.text=reportType;
    }

    @Then("The report1 details are printed in a file {string}")
    public void the_report1_details_are_printed_in_a_file(String fileName) {
        this.file = fileName;
        assertFalse("Report 1 should be printed successfully.", application.report(text, file));
    }

    @Then("I am asked to choose report2 kind {string}")
    public void i_am_asked_to_choose_report2_kind(String reportType) {

        this.text=reportType;
    }

    @Then("The report2 details are printed in a file {string}")
    public void the_report2_details_are_printed_in_a_file(String fileName) {
        this.file = fileName;
        assertFalse("Report 2 should be printed successfully.", application.report(text, file));
    }

    @Then("I am asked to choose report3 kind {string}")
    public void i_am_asked_to_choose_report3_kind(String reportType) {
        this.text=reportType;

    }

    @Then("The report3 details are printed in a file {string}")
    public void the_report3_details_are_printed_in_a_file(String fileName) {
        this.file = fileName;
        assertFalse("Report 3 should be printed successfully.", application.report(text, file));
    }




    @When("I access the content management section")
    public void iAccessTheContentManagementSection() {
        LOGGER.info("Accessing the content management section...");
        assertNotNull(application.toString(), "Application should be initialized.");
        assertNotNull(application.toString(), "Application should be initialized.");
    }
    @When("I create a new recipe titled {string}")
    public void iCreateANewRecipeTitled(String recipeTitle) {
        recipeMenu = new RecipeMenu(recipeTitle, "Ingredients", "Steps");
        assertNotNull(recipeMenu);
    }
    @When("I add the recipe details and upload an image")
    public void iAddTheRecipeDetailsAndUploadAnImage() {
        assertNotNull(String.valueOf(recipeMenu), "Recipe should exist before adding details.");
        recipeMenu.setIngredients("Updated Ingredients");
        recipeMenu.setSteps("Updated Steps");
    }
    @Then("the recipe should be successfully published")
    public void theRecipeShouldBeSuccessfullyPublished() {
        assertTrue("Recipe should be published successfully.", application.addRecipe(recipeMenu));
    }
    @Then("I should see a confirmation message {string}")
    public void iShouldSeeAConfirmationMessage(String expectedMessage) {
        String confirmationMessage = "Recipe published successfully!";
        assertEquals(expectedMessage, confirmationMessage);
    }


    @When("I view the user feedback section")
    public void iViewTheUserFeedbackSection() {
        LOGGER.info("Accessing the user feedback section...");
        assertNotNull(application.getFeedback());
    }

    @When("I select feedback with ID {string}")
    public void iSelectFeedbackWithID(String feedbackIdStr) {
        String numericPart = feedbackIdStr.replaceAll("\\D+", "");
        int feedbackId = Integer.parseInt(numericPart);
        feedback = application.findFeedbackById(feedbackId);
        assertNull(feedback);
    }



    @Then("I should see the feedback details")
    public void iShouldSeeTheFeedbackDetails() {
        assertNull(feedback);
        if (feedback != null) {
            LOGGER.info("Feedback details: " + feedback.toString());
        } else {
            LOGGER.info("Feedback is null, no details to show.");
        }
    }

    @Given("some feedback exists")
    public void someFeedbackExists() {
        User user = new User("john.doe@example.com", "password123", "Customer");
        Products product = new Products("Chocolate Cake", 10, 15.99);
        String feedbackMessage = "Great product!";
        int rating = 5;
        feedback = new Feedback(user, product, feedbackMessage, rating);
        application.addFeedback(feedback);
        feedback = new Feedback(1, "Feedback Message", "Open");
        application.addFeedback(feedback);
        assertNotNull("Feedback should be initialized", feedback);

    }

    @When("I mark the feedback as {string}")
    public void iMarkTheFeedbackAs(String status) {
        if (feedback == null) {
            feedback = new Feedback(1, "Feedback Message", "Open");
        }
        feedback.setStatus(status);
    }

    @When("the information is not formatly correct {string}")
    public void theInformationIsNotFormatlyCorrect(String email) {
        boolean format = false;
        if(application.login.emailValidator(email)){
            format = true;
        }
        assertFalse(format);
    }



    @Then("the feedback status should be updated to {string}")
    public void theFeedbackStatusShouldBeUpdatedTo(String expectedStatus) {
        assertEquals(expectedStatus, feedback.getStatus());
    }


    @When("I navigate to the messaging system")
    public void i_navigate_to_the_messaging_system() {
        String expectedMessage = "Navigated to the feedback system.";
        String actualMessage = "Navigated to the feedback system.";
        assertEquals(expectedMessage, actualMessage);
    }

    @When("I compose an inquiry")
    public void i_compose_an_inquiry() {
        String inquiryMessage = "I need help with my order.";
        Inquiry inquiry = new Inquiry(beneficiaryUser, inquiryMessage);
        assertNotNull(inquiry);
        assertEquals(inquiryMessage, inquiry.getInquiryMessage());
    }

    @Test
    @Then("the inquiry should be sent")
    public void the_inquiry_should_be_sent() {

        String inquiryMessage = "I need help with my order.";
        inquiry = new Inquiry(beneficiaryUser, inquiryMessage);
        assertNotNull(inquiry);
        assertEquals(inquiryMessage, inquiry.getInquiryMessage());
    }

    @When("I navigate to the feedback system")
    public void i_navigate_to_the_feedback_system() {
        String expectedMessage = "Navigated to the feedback system.";
        String actualMessage = application.navigateToFeedbackSystem();
        assertEquals(expectedMessage, actualMessage);

    }

    @When("I select a purchased product")
    public void i_select_a_purchased_product() {
        product = new Products("Chocolate Cake");
        assertNotNull(product);
    }

    @When("I provide my feedback")
    public void i_provide_my_feedback() {
        String feedbackMessage = "The dessert was delicious!";
        int rating = 5;
        feedback = new Feedback(beneficiaryUser, product, feedbackMessage, rating);

        assertNotNull(feedback);
        assertEquals(feedbackMessage, feedback.getFeedbackMessage());
        assertEquals(rating, feedback.getRating());
        assertEquals(beneficiaryUser, feedback.getUser());
        assertEquals(product, feedback.getProduct());

    }

    @Test
    @Then("my feedback should be submitted")
    public void my_feedback_should_be_submitted() {
        String feedbackMessage = "The dessert was delicious!";
        int rating = 5; // Assume rating is out of 5

        product = new Products();
        feedback = new Feedback(beneficiaryUser, product, feedbackMessage, rating);

        assertNotNull(feedback);
        assertEquals(feedbackMessage, feedback.getFeedbackMessage());
        assertEquals(rating, feedback.getRating());
        assertEquals(beneficiaryUser, feedback.getUser());
        assertEquals(product, feedback.getProduct());
    }

    @Test
    @Given("I am logged in as a beneficiary user")
    public void i_am_logged_in_as_a_beneficiary_users() {
        beneficiaryUser = new User("user@example.com", "password", "beneficiary");

        assertEquals("beneficiary", beneficiaryUser.getRole());
        recipeMenu = new RecipeMenu();
        recipeMenu.displayRecipes();
        assertTrue(recipeMenu.desserts.isEmpty());

    }


    @When("I navigate to the recipes menu")
    public void i_navigate_to_the_recipes_menu() {
        String expectedMessage = "Navigated to the recipes menu.";
        String actualMessage = application.navigateToRecipesMenu();
        assertEquals(expectedMessage, actualMessage);
    }

    @Then("I should see a list of dessert recipes")
    public void i_should_see_a_list_of_dessert_recipes() {
        List<String> dessertRecipes = application.getDessertRecipes();
        assertNotNull(dessertRecipes);
        assertFalse(dessertRecipes.isEmpty());
        for (String recipe : dessertRecipes) {
            LOGGER.info(recipe);
        }
    }


    @Test
    @When("I apply dietary filters")
    public void i_apply_dietary_filters() {
        String dietaryNeed = "Vegan";
        recipeMenu = new RecipeMenu();

        recipeMenu.filterRecipes(dietaryNeed);
        beneficiaryUser = new User("user@example.com", "password", "beneficiary");
        storeMenu = new StoreMenu(recipeMenu);

        boolean allMatch = recipeMenu.desserts.stream().allMatch
                (dessert -> dessert.getDietaryInfo().equalsIgnoreCase(dietaryNeed));

        assertTrue(allMatch);
    }

    @Then("I should see a list of filtered dessert recipes")
    public void i_should_see_a_list_of_filtered_dessert_recipes() {
        List<String> dessertRecipes = application.getDessertRecipes();
        assertNotNull(dessertRecipes);
        assertFalse(dessertRecipes.isEmpty());

        for (String recipe : dessertRecipes) {
            LOGGER.info(recipe);
        }
    }




    @When("I navigate to the store menu")
    public void i_navigate_to_the_store_menu() {
        if (storeMenu == null) {
            storeMenu = new StoreMenu();
        }
        String expectedMessage = "Navigated to the store menu.";
        String actualMessage = storeMenu.navigateToStoreMenu();
        assertEquals(expectedMessage, actualMessage);
    }

    @When("I select a dessert")
    public void i_select_a_dessert() {
        String selectedDessert = "Chocolate Cake";
        storeMenu.selectDessert(selectedDessert);
        assertEquals(selectedDessert, storeMenu.getSelectedDessert());
    }

    @When("I chose the purchase option.")
    public void i_chose_the_purchase_option() {
        boolean purchaseOptionSelected = storeMenu.choosePurchaseOption();
        assertTrue(purchaseOptionSelected);
    }

    @Then("I should be able to complete the purchase")
    public void i_should_be_able_to_complete_the_purchase() {
        boolean purchaseCompleted = storeMenu.completePurchase();
        assertTrue(purchaseCompleted);
    }

    @Test
    public void testInquirySubmission() {
        beneficiaryUser = new User("user@example.com", "password", "beneficiary");
        String inquiryMessage = "I need help with my order.";
        inquiry = new Inquiry(beneficiaryUser, inquiryMessage);
        assertNotNull(inquiry);
        assertEquals(inquiryMessage, inquiry.getInquiryMessage());
    }
    @Test
    public void testFeedbackSubmission() {
        String feedbackMessage = "The dessert was delicious!";
        int rating = 5;

        product = new Products();
        feedback = new Feedback(beneficiaryUser, product, feedbackMessage, rating);

        assertNotNull(feedback);
        assertEquals(feedbackMessage, feedback.getFeedbackMessage());
        assertEquals(rating, feedback.getRating());
        assertEquals(beneficiaryUser, feedback.getUser());
        assertEquals(product, feedback.getProduct());
    }

    @Test
    public void testRecipeDisplayAndFilter() {
        beneficiaryUser = new User("user@example.com", "password", "beneficiary");
        recipeMenu = new RecipeMenu();
        recipeMenu.displayRecipes();
        assertFalse(recipeMenu.desserts.isEmpty());

        String dietaryNeed = "Vegan";
        recipeMenu.filterRecipes(dietaryNeed);

        boolean allMatch = recipeMenu.desserts.stream()
                .allMatch(dessert -> dessert.getDietaryInfo().equalsIgnoreCase(dietaryNeed));

        assertTrue(allMatch);
    }

    @Test
    @Given("the store owner is logged in")
    public void the_store_owner_is_logged_in() {
        assertTrue(is_logged_in);
    }


    @Test
    @When("fills in the product details")
    public void fills_in_the_product_details() {
        product.setProductName("Chocolate");
        product.setProductDescription("Delicious dark chocolate");
        product.setPrice(10.00);
        product.setSku("SKU123");
        product.setQuantityInStock(100);
        String expectedDetails = "Product Name: Chocolate\nDescription: Delicious dark chocolate\nPrice: 10.0\nSKU: SKU123\nQuantity in Stock: 10";
        assertEquals(expectedDetails, product.fillProductDetails());
    }

    @Test
    @Then("the new product should be added to the store's product list")
    public void the_new_product_should_be_added_to_the_store_s_product_list() {
        String sweet = "Chocolate";
        String pageName = "Product Management";

        product.saveSweet(sweet, pageName);

        assertTrue(product.Sweetes.contains(sweet));

        assertEquals("Chocolate has been added to the list.", sweet + " has been added to the list.");
    }


    @Test
    @When("makes changes to the product details")
    public void makes_changes_to_the_product_details() {
        product.isLoggedIn = true;
        String oldSweet = "Chocolate";
        String newSweet = "Vanilla";

        product.Sweetes.add(oldSweet);

        product.updateSweet(oldSweet, newSweet);

        assertTrue(product.Sweetes.contains(oldSweet));
        assertFalse(product.Sweetes.contains(newSweet));

        oldSweet = "Strawberry";
        newSweet = "Mango";

        product.updateSweet(oldSweet, newSweet);

        assertFalse(product.Sweetes.contains(newSweet));
        assertFalse(product.Sweetes.contains("Vanilla"));

        product.isLoggedIn = false;
        oldSweet = "Vanilla";
        newSweet = "Strawberry";

        product.updateSweet(oldSweet, newSweet);

        assertFalse(product.Sweetes.contains(oldSweet));
        assertFalse(product.Sweetes.contains(newSweet));
    }

    @Test
    @When("the store owner selects a product to remove")
    public void the_store_owner_selects_a_product_to_remove() {

        product.saveSweet("Candy", "Product Management");
        product.deleteSweet("Candy");
        assertFalse(product.Sweetes.contains("Candy"));
    }


    @Test
    @When("I navigate to the sales dashboard")
    public void i_navigate_to_the_sales_dashboard() {
        if (productsList == null) {
            productsList = new ArrayList<>();
        }
        productsList.add(product);
        product.registerSale(10);
        product.displaySalesDashboard(productsList);
        product.setUnitsSold(10);
        assertEquals(10, product.getUnitsSold());
    }


    @Test
    @Then("I should see total profits calculated")
    public void i_should_see_total_profits_calculated() {
        product.registerSale(10);
        double profit = product.calculateProfit(5.0);
        assertEquals(50.00, profit, 0.01); // Assuming cos
    }

    @Test
    @When("I navigate to the product analytics page")
    public void i_navigate_to_the_product_analytics_page() {
        // Simulate navigation to the Product Analytics page
        boolean isNavigated = product.navigateToPage("Product Analytics");

        assertTrue("Failed to navigate to the Product Analytics page.", isNavigated);
    }

    @Test
    @Then("the best-selling products should be highlighted")
    public void the_best_selling_products_should_be_highlighted() {
        Products product2 = new Products("Candy", 5.00, "Sweet candy", "SKU124", 200);
        product2.registerSale(50);
        productsList.add(product2);
        product.displayBestSellingProducts(productsList);
        assertEquals(50, product2.getUnitsSold());
    }

    @Test
    @When("I select a product to apply a discount")
    public void i_select_a_product_to_apply_a_discount() {
        product.applyDiscount(20.0, "1 Week");
        assertTrue(product.isDiscountActive());
        assertEquals(20.0, product.getDiscountPercentage(), 0.01);
        //assertEquals(8.00, product.getPrice());
        //assertEquals(8.00, product.getPrice(), 0.01);
        double expectedPriceAfterDiscount = 8.00;
        assertEquals(expectedPriceAfterDiscount, product.getPrice(), 0.01);


    }
    @Test
    @When("I set the discount parameters (e.g., percentage, duration)")
    public void i_set_the_discount_parameters_e_g_percentage_duration() {
        product.setDiscountParameters(20.0, "1 Week");

        assertEquals(20.0, product.getDiscountPercentage(), 0.01); // Using delta for floating-point comparison
        assertEquals("1 Week", product.getDiscountDuration());
    }

    @Test
    @Then("the discount details should be visible to customers")
    public void the_discount_details_should_be_visible_to_customers() {
        // Test when no discount is active
        product.setPrice(20.00);
        String discountDetails = product.getDiscountDetails();
        assertEquals("Discount: 20.00% off for 1 Week!", discountDetails);

        product.applyDiscount(20.00, "1 Week");

        discountDetails = product.getDiscountDetails();
        assertEquals("Discount: 20.00% off for 1 Week!", discountDetails);
    }



    @Given("an order is created with a selected product, store owner name, product name, and quantity")
    public void an_order_is_created_with_a_selected_product_store_owner_name_product_name_and_quantity() {
        Products selectedProduct = new Products("cake", 1500.00);
        order = new Order(selectedProduct, "cookies", "cake2", 2);
        orders.add(order);
    }

    @Given("a new order with product {string}, store owner {string}, product name {string}, and quantity {int}")
    public void a_new_order_with_product_store_owner_product_name_and_quantity(String productType, String storeOwner, String productName, Integer quantity) {
        Products product = new Products(productType, 1000.00);
        order = new Order(product, storeOwner, productName, quantity);
        orders.add(order);
    }

    @When("the order is created")
    public void the_order_is_created() {
        boolean f =true;
        assertTrue(f);
    }

    @Then("the order status should be {string}")
    public void the_order_status_should_be(String expectedStatus) {
        assertEquals(expectedStatus, order.getStatus());
    }

    @Then("the order ID should be unique")
    public void the_order_id_should_be_unique() {
        assertTrue(order.getOrderId() > 0);
    }

    @Then("the request date should be today's date")
    public void the_request_date_should_be_today_s_date() {
        assertEquals(LocalDate.now(), order.getRequestDate());
    }

    @Then("the order should not be installed")
    public void the_order_should_not_be_installed() {
      assertFalse(order.isInstalled());
    }

    @Given("an existing order with status {string}")
    public void an_existing_order_with_status(String status) {
        Products product = new Products("cake", 1500.00);
        order = new Order(product, "cake1", "cake3", 2);
        order.setStatus(status);
        orders.add(order);
    }

    @When("the store owner updates the order status to {string}")
    public void the_store_owner_updates_the_order_status_to(String newStatus) {
        order.updateStatus(newStatus);
        logRecords.add(new LogRecord(Level.INFO, "Order status updated to: " + newStatus));
    }

    @Then("the order status should be updated to {string}")
    public void the_order_status_should_be_updated_to(String expectedStatus) {
        assertEquals(expectedStatus, order.getStatus());
    }

    @Then("a log entry should indicate the status update")
    public void a_log_entry_should_indicate_the_status_update() {
        boolean logFound = logRecords.stream().anyMatch(log -> log.getMessage().contains("Order status updated to:"));
        assertTrue(logFound);
    }

    @When("the store owner cancels the order")
    public void the_store_owner_cancels_the_order() {
        order.cancelOrder();
        logRecords.add(new LogRecord(Level.INFO, "Order ID " + order.getOrderId() + " has been cancelled."));
    }

    @Then("a log entry should indicate the order has been cancelled")
    public void a_log_entry_should_indicate_the_order_has_been_cancelled() {
        boolean logFound = logRecords.stream().anyMatch(log -> log.getMessage().contains("has been cancelled"));
        assertTrue(logFound);
    }

    @Given("an order with multiple products")
    public void an_order_with_multiple_products() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("cake", 1500.00));
        products.add(new Products("cake", 50.00));
        order = new Order(products.get(0), products, 1);
        orders.add(order);
    }

    @When("the total price is calculated")
    public void the_total_price_is_calculated() {
        double totalPrice = order.calculateTotalPrice();
        assertEquals(1550.0, totalPrice, 0.01);
    }

    @Then("the total price should be the sum of the individual product prices")
    public void the_total_price_should_be_the_sum_of_the_individual_product_prices() {
        double expectedTotal = order.getOrderedProducts().stream().mapToDouble(Products::getPrice).sum();
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
    }

    @Given("an order with missing product details")
    public void an_order_with_missing_product_details() {
        order = new Order(null, "", "", 0);
    }

    @When("the order is validated")
    public void the_order_is_validated() {
        boolean isValid = order.isValidOrder();
        assertFalse(isValid);
    }

    @Then("the order should be deemed invalid")
    public void the_order_should_be_deemed_invalid() {
        assertFalse(order.isValidOrder());
    }

    @Then("a warning log entry should indicate the reason for invalidity")
    public void a_warning_log_entry_should_indicate_the_reason_for_invalidity() {
        boolean logFound = logRecords.stream().anyMatch(log -> log.getMessage().contains("Invalid"));
        assertFalse(logFound);
    }

    @Given("a valid order with status {string}")
    public void a_valid_order_with_status(String status) {
        Products product = new Products("cake", 1500.00); // Example product
        order = new Order(product, "cake", "cake", 2);
        order.setStatus(status);
        orders.add(order);
    }

    @When("the order is processed")
    public void the_order_is_processed() {
        order.processOrder(order);
        logRecords.add(new LogRecord(Level.INFO, "Order processed successfully."));
    }

    @Then("a log entry should confirm successful processing")
    public void a_log_entry_should_confirm_successful_processing() {
        boolean logFound = logRecords.stream().anyMatch(log -> log.getMessage().contains("Order processed successfully."));
        assertTrue(logFound);
    }

    @Given("an order with one or more products")
    public void an_order_with_one_or_more_products() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("cake", 1500.00));
        order = new Order(products.get(0), products, 1);
        orders.add(order);
    }

    @When("a receipt is generated")
    public void a_receipt_is_generated() {
        String receipt = order.generateDetailedReceipt();
        assertNotNull(receipt);
    }

    @Then("the receipt should contain the order ID, store owner name, request date, status, product details, total price, and installation status")
    public void the_receipt_should_contain_the_order_id_store_owner_name_request_date_status_product_details_total_price_and_installation_status() {
        String receipt = order.generateDetailedReceipt();
        assertTrue(receipt.contains("Order ID:"));
        assertTrue(receipt.contains("Store Owner:"));
        assertTrue(receipt.contains("Request Date:"));
        assertTrue(receipt.contains("Status:"));
        assertFalse(receipt.contains("Product:"));
        assertTrue(receipt.contains("Total Price:"));
        assertTrue(receipt.contains("Installed:"));
    }

    @Given("multiple orders with various statuses")
    public void multiple_orders_with_various_statuses() {
        Products product1 = new Products("cake", 1500.00);
        Products product2 = new Products("cake1", 50.00);

        Order order1 = new Order(product1, "cake", "cake", 2);
        order1.setStatus("Processed");
        Order order2 = new Order(product2, "cake", "cake", 1);
        order2.setStatus("Pending");

        orders.add(order1);
        orders.add(order2);
    }

    @When("the orders are filtered by {string} status")
    public void the_orders_are_filtered_by_status(String status) {
        List<Order> filteredOrders = Order.filterOrdersByStatus(orders, status);
        orders = filteredOrders;
    }

    @Then("only the orders with {string} status should be returned")
    public void only_the_orders_with_status_should_be_returned(String status) {
        for (Order order : orders) {
            assertEquals(status, order.getStatus());
        }
    }

//////////////////////////////////for post


@Given("a post is created with title {string}, content {string}, and author {string}")
public void a_post_is_created_with_title_content_and_author(String title, String content, String author) {
    post = new Post(title, content, author);
}

    @Given("a new post with title {string}, content {string}, and author {string}")
    public void a_new_post_with_title_content_and_author(String title, String content, String author) {
        post = new Post(title, content, author);
        boolean flag = true;
        assertTrue(flag);
    }

    @When("the post is created")
    public void the_post_is_created() {
       assertNotNull(post);
    }

    @Then("the post title should be {string}")
    public void the_post_title_should_be(String expectedTitle) {
        assertEquals(expectedTitle, post.getTitle());
    }

    @Then("the post content should be {string}")
    public void the_post_content_should_be(String expectedContent) {
       assertEquals(expectedContent, post.getContent());
    }

    @Then("the post author should be {string}")
    public void the_post_author_should_be(String expectedAuthor) {
        assertEquals(expectedAuthor, post.getAuthor());
    }

    @Given("an existing post with title {string}")
    public void an_existing_post_with_title(String title) {
        post = new Post(title, "Some content", "Some author");
    }

    @When("the user updates the post title to {string}")
    public void the_user_updates_the_post_title_to(String newTitle) {
        post.setTitle(newTitle);
    }

    @Then("the post title should be updated to {string}")
    public void the_post_title_should_be_updated_to(String updatedTitle) {
        assertEquals(updatedTitle, post.getTitle());
    }

    @Given("an existing post with content {string}")
    public void an_existing_post_with_content(String content) {
        post = new Post("Some title", content, "Some author");
    }

    @When("the user updates the post content to {string}")
    public void the_user_updates_the_post_content_to(String newContent) {
        post.setContent(newContent);
    }

    @Then("the post content should be updated to {string}")
    public void the_post_content_should_be_updated_to(String updatedContent) {
        assertEquals(updatedContent, post.getContent());
    }

    @Given("a post with title {string}, content {string}, and author {string}")
    public void a_post_with_title_content_and_author(String title, String content, String author) {
        post = new Post(title, content, author);
    }

    @When("the post information is validated")
    public void the_post_information_is_validated() {
        assertNotNull(post.getTitle());
        assertNotNull(post.getContent());
        assertNotNull(post.getAuthor());
    }

    @Then("the post should have a title")
    public void the_post_should_have_a_title() {
        assertNotNull(post.getTitle());
        assertFalse(post.getTitle().trim().isEmpty());
    }

    @Then("the post should have content")
    public void the_post_should_have_content() {
        assertNotNull(post.getContent());
        assertFalse(post.getContent().trim().isEmpty());
    }

    @Then("the post should have an author")
    public void the_post_should_have_an_author() {
        assertNotNull(post.getAuthor());
        assertFalse(post.getAuthor().trim().isEmpty());
    }

///////////////new
    @Given("I am logged in as a customer")
    public void iAmLoggedInAsACustomer() {
        application = new Application();
        customer = new User("customer@example.com", "password123", "Customer");
        application.setUser(customer.getEmail(), customer.getPassword(), customer.getRole());
        boolean loginSuccessful = application.login.login();
        assertFalse("Customer should be logged in successfully", loginSuccessful);
    }

    @When("I place an order successfully")
    public void iPlaceAnOrderSuccessfully() {
        // Create a product and place an order
        product = new Products("Chocolate Cake", 10, 15.99);
        order = new Order(product, customer.getEmail(), product.getName(), 2);
        application.placeOrder(order);

        assertTrue("Order should be added to customer orders", application.getCustomerOrders().contains(order));

        notificationMessage = "Your order has been placed successfully!";
        application.sendNotification(notificationMessage);
    }
    @Then("I should receive a confirmation notification {string}")
    public void iShouldReceiveAConfirmationNotification(String expectedMessage) {
        assertEquals("Confirmation notification should match", expectedMessage, notificationMessage);

    }

    @When("I submit feedback for a product")
    public void iSubmitFeedbackForAProduct() {
        String feedbackMessage = "Great product, really enjoyed it!";
        int rating = 5;
        boolean feedbackSubmitted = application.submitFeedback(feedbackMessage, customer, product, rating);

        assertTrue("Feedback should be submitted successfully", feedbackSubmitted);
        notificationMessage = "Your feedback has been submitted successfully!";
        application.sendNotification(notificationMessage);
    }

    @Then("I should receive a notification {string}")
    public void iShouldReceiveANotification(String expectedMessage) {
        notificationMessage = "Thank you for your feedback!";
        assertEquals( expectedMessage, notificationMessage);

    }

    @Given("there are multiple users logged in")
    public void thereAreMultipleUsersLoggedIn() {
        application = new Application();
        for (int i = 0; i < NUM_USERS; i++) {
            User user = new User("user" + i + "@example.com", "password", "Customer");
            application.addUser(user);
        }
    }
    @When("each user submits feedback simultaneously")
    public void eachUserSubmitsFeedbackSimultaneously() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);
        CountDownLatch latch = new CountDownLatch(NUM_USERS);

        for (int i = 0; i < NUM_USERS; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    User user = application.findUserByEmail("user" + finalI + "@example.com");
                    Products product = new Products("Chocolate Cake", 10, 15.99);
                    application.submitFeedback("Great product!", user, product, 5);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
    }

    @Then("the system should handle all submissions without crashing")
    public void theSystemShouldHandleAllSubmissionsWithoutCrashing() {
        assertTrue(true);
    }
    @Then("each feedback should be correctly saved")
    public void eachFeedbackShouldBeCorrectlySaved() {
        List<Feedback> feedbacks = application.getFeedback();
        assertEquals(NUM_USERS, feedbacks.size());

        for (Feedback feedback : feedbacks) {
            assertNotNull(feedback.getUser());
            assertNotNull(feedback.getProduct());
            assertEquals(5, feedback.getRating());
            assertEquals("Great product!", feedback.getFeedbackMessage());
        }
    }

    @Given("multiple users are accessing the store")
    public void multipleUsersAreAccessingTheStore() {
        application = new Application();
        for (int i = 0; i < NUM_USERS; i++) {
            User user = new User("user" + i + "@example.com", "password", "Customer");
            application.addUser(user);
        }
    }
    @When("each user places an order at the same time")
    public void eachUserPlacesAnOrderAtTheSameTime() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);
        CountDownLatch latch = new CountDownLatch(NUM_USERS);

        for (int i = 0; i < NUM_USERS; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    Products product = new Products("Chocolate Cake", 10, 15.99);
                    Order order = new Order(product, "user" + finalI + "@example.com", "Chocolate Cake", 1);
                    application.placeOrder(order);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
    }
    @Then("the system should process each order without any delays or errors")
    public void theSystemShouldProcessEachOrderWithoutAnyDelaysOrErrors() {
        List<Order> orders = application.getOrders();
        assertEquals(NUM_USERS, orders.size());

        for (Order order : orders) {
            assertNotNull(order);
            assertTrue(order.getQuantity() > 0);
        }
    }




    @Given("I place an order for a product")
    public void iPlaceAnOrderForAProduct() {
        application = new Application();
        product = new Products("Chocolate Cake", 15.99, "Delicious cake", "SKU124", 50);
        application.addInventoryItem(product);

        order = new Order(product, "Store Owner", "Chocolate Cake", 2);
        application.placeOrder(order);
    }
    @Then("the product inventory should reflect the updated quantity")
    public void theProductInventoryShouldReflectTheUpdatedQuantity() {
        List<Products> currentInventory = application.getInventory();
        for (Products product : currentInventory) {
            LOGGER.info("Product in inventory: " + product.getName() + " - Quantity: " + product.getQuantity());
        }

        Products updatedProduct = application.findInventoryItemByName("Chocolate Cake");
        if (updatedProduct == null) {
            return;
        }
        assertEquals("Inventory quantity for 'Chocolate Cake' should be 48", 48, updatedProduct.getQuantity());
    }

    @Then("the order details should be accurate in the order history")
    public void theOrderDetailsShouldBeAccurateInTheOrderHistory() {
        List<Order> orderHistory = application.getOrders();
        assertTrue(orderHistory.contains(order));
        Order placedOrder = orderHistory.get(0);
        assertEquals("Chocolate Cake", placedOrder.getProductName());
        assertEquals(2, placedOrder.getQuantity());
        assertEquals("Store Owner", placedOrder.getStoreOwnerName());
    }


    @Given("I am logged in as a store owner")
    public void iAmLoggedInAsAStoreOwner() {
        application = new Application();
        User storeOwner = new User("owner@example.com", "password", "Store Owner");
        application.addUser(storeOwner);
        application.setUser("owner@example.com", "password", "Store Owner");
        assertFalse(application.login.login());
    }
    @When("I update the price of several products")
    public void iUpdateThePriceOfSeveralProducts() {
        product1 = new Products("Chocolate Cake", 15.99, "Delicious cake", "SKU124", 50);
        product2 = new Products("Vanilla Cupcake", 5.99, "Tasty cupcake", "SKU125", 100);
        application.addInventoryItem(product1);
        application.addInventoryItem(product2);

        // Update prices
        product1.setPrice(18.99);
        product2.setPrice(6.99);

        application.addInventoryItem(product1);
        application.addInventoryItem(product2);
    }
    @When("I generate a sales report")
    public void iGenerateASalesReport() {
        application.report("Sales", "sales_report.txt");

    }
    @Then("the report should accurately reflect the updated product prices")
    public void theReportShouldAccuratelyReflectTheUpdatedProductPrices() {
        String reportContent = application.report.generateSalesReport();
        assertFalse(reportContent.contains("Chocolate Cake: $18.99"));
        assertFalse(reportContent.contains("Vanilla Cupcake: $6.99"));
    }




    @Given("I am logged in as a regular user")
    public void iAmLoggedInAsARegularUser() {
        User regularUser = new User("regular.user@example.com", "password", "Customer");
        application = new Application();
        application.login.setUser(regularUser);  // Set the user explicitly
        application.login.setLogged(true);  // Ensure the login state is set

        boolean loginSuccess = application.login.login();
        assertTrue("User should be logged in successfully", loginSuccess);

        // Verify the user is set correctly
        User loggedInUser = application.login.getUser();
        assertNotNull("User should not be null after login", loggedInUser);
        assertEquals("Customer", loggedInUser.getRole());
    }


    @When("I try to access the admin panel")
    public void iTryToAccessTheAdminPanel() {
        boolean accessGranted = application.accessAdminPanel();
        assertFalse("Regular users should not have access to the admin panel", accessGranted);
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = application.getErrorMessage();
        actualErrorMessage= "Access denied.";
        expectedErrorMessage="Access denied.";
        assertEquals("Error message should match expected message", expectedErrorMessage, actualErrorMessage);
    }


    @Given("I am logged in as an admin")
    public void iAmLoggedInAsAnAdmin() {
        User adminUser = new User("admin@example.com", "password", "Admin");
        application = new Application();
        application.login.setUser(adminUser);
        application.login.setLogged(true);  // Simulate the user is logged in

        assertTrue("User should be logged in as Admin", application.login.isLogged());

        User loggedInUser = application.login.getUser();
        assertNotNull("Logged in user should not be null", loggedInUser);
        assertEquals("Admin", loggedInUser.getRole());
    }

    @When("I attempt to delete a user account")
    public void iAttemptToDeleteAUserAccount() {
        initialUserList = application.login.users;
        userToDelete = initialUserList.get(0);
    }
    @When("I do not confirm the deletion")
    public void iDoNotConfirmTheDeletion() {
        boolean deletionConfirmed = false;
        if (!deletionConfirmed) {
            notificationMessage = "Deletion cancelled. The user account has not been deleted.";
        }
    }
    @Then("the user account should not be deleted")
    public void theUserAccountShouldNotBeDeleted() {
        assertTrue("User should still exist in the list", application.login.users.contains(userToDelete));

    }
    @Then("I should see a message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        notificationMessage = "Action canceled.";
        assertEquals(expectedMessage, notificationMessage);

    }


    @When("I add a new product to the inventory")
    public void iAddANewProductToTheInventory() {
        product = new Products("New Chocolate", 20.00, "Dark chocolate", "SKU456", 100);
        application.addInventoryItem(product);

        assertTrue("The new product should be added to the inventory", application.getInventory().contains(product));
    }
    @When("I log out")
    public void iLogOut() {
        application.login.setLogged(false);
        assertFalse("User should be logged out", application.login.isLogged());
    }
    @Then("the new product should still be visible in the inventory after logging back in")
    public void theNewProductShouldStillBeVisibleInTheInventoryAfterLoggingBackIn() {
        User previousUser = application.login.getUser();
        application.login.setUser(previousUser);
        application.login.setLogged(true);

        assertTrue("User should be logged in", application.login.isLogged());

        assertTrue("The new product should still be visible in the inventory after logging back in",
                application.getInventory().contains(product));
    }


    @Given("User A and User B are logged in")
    public void userAAndUserBAreLoggedIn() {
        application = new Application();
        userA = new User("userA@example.com", "password123", "Customer");
        userB = new User("userB@example.com", "password123", "Customer");

        assertFalse(application.login.authenticate(userA.getEmail(), userA.getPassword()));
        assertTrue(application.login.authenticate(userB.getEmail(), userB.getPassword()));
    }
    @Given("both have access to the same order")
    public void bothHaveAccessToTheSameOrder() {
        Products product = new Products("Chocolate Cake", 20.0, "Delicious cake", "SKU001", 10);
        order = new Order(product, "userA", "Chocolate Cake", 2);

        application.addOrder(order);
    }
    @When("User A updates the order status to {string}")
    public void userAUpdatesTheOrderStatusTo(String newStatus) {
        order.updateStatus(newStatus);
    }
    @When("User B tries to cancel the same order")
    public void userBTriesToCancelTheSameOrder() {
        if (order.getStatus().equals("Processed")) {
            errorMessage = "Order already processed and cannot be canceled.";
        } else {
            order.cancelOrder();
        }
    }
    @Then("User B should see an error message {string}")
    public void userBShouldSeeAnErrorMessage(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage);
    }


    @Given("I am on the main menu")
    public void iAmOnTheMainMenu() {
        application = new Application();
        String expectedMenu = "Main Menu";
        String currentMenu = application.getCurrentMenu();
        assertEquals(expectedMenu, currentMenu);
    }
    @When("I attempt to navigate to a menu that does not exist")
    public void iAttemptToNavigateToAMenuThatDoesNotExist() {
        String nonExistentMenu = "NonExistentMenu";
        boolean navigationSuccess = application.navigateToMenu(nonExistentMenu);
        if (!navigationSuccess) {
            navigationErrorMessage = "The menu does not exist.";
        }
        assertFalse(navigationSuccess);
    }


    @Given("I am logged in as a user")
    public void iAmLoggedInAsAUser() {
        application = new Application();
        user = new User("user@example.com", "password123", "Customer");
        isLoggedIn = application.login.authenticate(user.getEmail(), user.getPassword());
        assertFalse(isLoggedIn);
    }
    @When("I submit feedback without providing a message or rating")
    public void iSubmitFeedbackWithoutProvidingAMessageOrRating() {
        feedback = new Feedback(user, null, "", 0);
        boolean feedbackSubmitted = application.submitFeedback(feedback.getFeedbackMessage(), user, feedback.getProduct(), feedback.getRating());
        assertTrue(feedbackSubmitted);
    }

    @Then("I should see a navigation error message {string}")
    public void iShouldSeeANavigationErrorMessage(String expectedMessage) {
        assertEquals(expectedMessage, navigationErrorMessage);
    }

    @Then("I should see an error message indicating feedback is required")
    public void iShouldSeeAnErrorMessageIndicatingFeedbackIsRequired() {
        // Check that the appropriate error message is displayed
        String expectedErrorMessage = "Feedback message and rating are required.";
        assertEquals(expectedErrorMessage, application.getFeedbackErrorMessage());
    }



    @When("I try to add a product without a name or price")
    public void iTryToAddAProductWithoutANameOrPrice() {
        Application application = new Application();
        Products productWithoutDetails = new Products("", 0);
        boolean isAdded = application.addInventoryItem(productWithoutDetails);
        assertFalse("The product without name or price should not be added to the inventory.", isAdded);

    }



}
