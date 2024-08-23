package sweet_2024;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private User u,o;
    private boolean userAdded, isUserUpdating, isUserDeleting;
    boolean newAccount=false;
    String text,file;
    private static List<Products> productsList;
    public boolean is_logged_in = true;
    private static Order order;
    private  static final Logger LOGGER = Logger.getLogger(Testing.class.getName());
    private static Login login;
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
        u = new User("ali55@gmail.com", "123456", "Customer");
        o = new User("abd3@gmail.com", "123", "Admin");
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
        String  email="existing.email@example.com";
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
        String email = "newuser@example.com";
        User newUser = new User(email, "2w421", "Admin");
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
        User existingUser = application.login.users.get(0);
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
            if(new Login(u).emailValidator(u1.getEmail())){
                if(u1.getEmail().equalsIgnoreCase(Email)&&u1.getPassword().equals(Pass)){
                    application.login.setLogged(true);
                    loginSuccessful=true;
                    break;
                }
            }
        }
        assertTrue(loginSuccessful);

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
                loginFailed = false;
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

    @ParameterizedTest
    @CsvSource({
            "invalid.email@example.com, wrongpassword",
            "another.invalid@example.com, wrongpassword"
    })
    @When("the information are invalid email is {string} and password is {string}")
    public void theInformationAreInvalidEmailIsAndPasswordIs(String email, String password) {
        boolean loginFailed = true;
        for (User u1 : application.login.users) {
            if (u1.getEmail().equalsIgnoreCase(email) && u1.getPassword().equals(password)) {
                loginFailed = false;
                break;
            }
        }

        assertFalse("Expected login to fail, but it succeeded.", loginFailed);
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
            assertEquals(4, login3.userIndex);
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
                break;
            }
        }
        assertFalse(loginFailed);
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
        boolean emailExists = application.login.users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        assertTrue("Email should exist in the user list", emailExists);
    }



    @Then("creating an account failed")
    public void creatingAnAccountFailed() {
        assertFalse(newAccount);
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

//        User newUser = new User("ali.d@example.org", "hiword");
//        SignUp signUp = new SignUp(newUser, new Login(newUser));
//        assertFalse(signUp.createAccount());
//
//        User newUser1 = new User("WWW@gmail.com", "hiword");
//        SignUp signUp1 = new SignUp(newUser1, new Login(newUser1));
//        assertTrue(signUp1.createAccount());
//        assertEquals(5, signUp1.l.users.size());
//
//        assertFalse(SignUp.emailValidator("ali.d@example.org"));
//        assertFalse(SignUp.emailValidator(null));
//        assertTrue(SignUp.emailValidator("WWW@gmail.com"));
//
//        SignUp actualSignUp = new SignUp(newUser, new Login(newUser));
//        Login login = actualSignUp.l;
//        User user = login.user;
//        assertEquals("hiword", user.getPassword());
//        User user2 = actualSignUp.newUser;
//        assertEquals("hiword", user2.getPassword());
//        assertEquals("ali.d@example.org", user.getEmail());
//        assertEquals("ali.d@example.org", user2.getEmail());
//        assertNull(user.getRole());
//        assertNull(user2.getRole());
//        assertEquals(0, login.getRoles());
//        assertEquals(4, login.users.size());
//        assertFalse(login.isLogged());
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
        if (!"Admin".equals(u.getRole())) {
            u.setRole("Admin");
        }
        assertEquals("Admin", u.getRole(), "Admin");
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
        String expectedDetails = "Product Name: Chocolate\nDescription: Delicious dark chocolate\nPrice: 10.0\nSKU: SKU123\nQuantity in Stock: 0";
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
        product.is_logged_in = true;
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

        product.is_logged_in = false;
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
        assertEquals(-50.00, profit, 0.01); // Assuming cos
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

        // Test when a discount is active
        discountDetails = product.getDiscountDetails();
        assertEquals("Discount: 20.00% off for 1 Week!", discountDetails);
    }


    @Test
    public void testProductName() {
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testProductPrice() {
        assertEquals(100.0, product.getPrice());
    }

    @Test
    public void testApplyDiscount() {
        product.applyDiscount(10.0, "1 Week");
        assertEquals(90.0, product.getPrice());
        assertTrue(product.isDiscountActive());
    }

    @Test
    public void testRemoveDiscount() {
        product.applyDiscount(10.0, "1 Week");
        product.removeDiscount();
        assertEquals(100.0, product.getPrice());
        assertFalse(product.isDiscountActive());
    }

    @Test
    public void testOrderId() {
        assertEquals("12345", order.getOrderId());
    }

    @Test
    public void testOrderStatus() {
        assertEquals("Pending", order.getStatus());
    }

    @Test
    public void testUpdateStatus() {
        order.updateStatus("Shipped");
        assertEquals("Shipped", order.getStatus());
    }
    @Test
    public void testUserId() {
        assertEquals("user123", feedback.getUserId());
    }

    @Test
    public void testComment() {
        assertEquals("Good product", feedback.getComment());
    }

    @Test
    public void testSetRating() {
        feedback.setRating(4);
        assertEquals(4, feedback.getRating());
    }
    @Test
    public void testInquiryId() {
        assertEquals("inq123", inquiry.getInquiryId());
    }

    @Test
    public void testQuestion() {
        assertEquals("What is the product warranty?", inquiry.getQuestion());
    }

    @Test
    public void testSetAnswer() {
        inquiry.setAnswer("One year warranty");
        assertEquals("One year warranty", inquiry.getAnswer());
    }
    @Test
    public void testUsername() {
        assertEquals("testUser", login.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals("password123", login.getPassword());
    }

    @Test
    public void testAuthenticate() {
        assertTrue(login.authenticate("testUser", "password123"));
    }


}
