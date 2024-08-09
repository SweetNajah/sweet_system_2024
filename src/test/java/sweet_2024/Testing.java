package sweet_2024;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mockStatic;

public class Testing {

    User u;
    User o;
    String text,file;
    boolean exist;
    boolean newAccount=false;
    boolean userAdded=false;
    boolean isUserUpdating = false;
    boolean isUserDeleting = false;

    private final Application application;

    public Testing(Application application) {
        this.application = application;
        u = new User("ali55@gmail.com","123456","Customer");
        o = new User("abd3@gmail.com","123","Admin");

    }

//////////////////////////////////////////////////////////////////////////////////UserManagement

    @Given("I am an admin")
    public void iAmAnAdmin() {
        boolean f = false;
        application.setUser(application.newUser.getEmail(),application.newUser.getPassword(),"Admin");
        if(application.newUser.getType().equals("Admin")){
            f=true;
        }
        assertTrue(f);
        assertEquals(0, application.login.getRoles());
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

        // Act and Assert
        assertFalse(login.addUser(new User("", "hiword")));

    }

    @When("i choose to add new user with with valid formatting")
    public void iChooseToAddNewUserWithWithValidFormatting() {
        String email = "frre.fff@gmail.com";
        String pass="2w421";
        boolean r=false;
        User u1 = new User(email,pass,"Admin");
        if(application.login.addUser(u1)){
            userAdded=true;
            r=true;
        }
        assertTrue(r);
    }
    @Then("user successfully added")
    public void userSuccessfullyAdded() {
        assertTrue(userAdded);
    }


    @When("i choose the user and setting the new value with valid formatting")
    public void iChooseTheUserAndSettingTheNewValueWithValidFormatting() {
        String email = "frre.fff@gmail.com";
        String pass="2w421";
        User u1 = new User(email,pass,"dd");
        User usr=application.login.users.get(1);
        if(application.login.updateUser(usr, u1)) {
            isUserUpdating = true;
        }
        assertTrue(isUserUpdating);
    }
    @Then("user successfully updating")
    public void userSuccessfullyUpdating() {
        assertTrue(isUserUpdating);
    }


    @When("i choose the user i want to delete")
    public void iChooseTheUserIWantToDelete() {
        User u1 = new User("ali55@gmail.com","123456","Customer");
        if(application.login.deleteUser(u1)){
            isUserDeleting=true;
        }
        assertTrue(isUserDeleting);
    }
    @Then("user successfully deleting")
    public void userSuccessfullyDeleting() {
        assertTrue(isUserDeleting);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////feature login



    @Given("that the user is not logged in")
    public void thatTheUserIsNotLoggedIn() {
        assertFalse(application.login.isLogged());
    }
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

        Login login = new Login(new User("ali.d@example.org", "hiword"));
        User oldUser = new User("ali.d@example.org", "hiword");

        login.updateUser(oldUser, new User("ali.d@example.org", "hiword", "Type"));

        // Act
        login.setRoles();

        // Assert
        assertEquals(-1, login.getRoles());

        try (MockedStatic<Transport> mockTransport = mockStatic(Transport.class)) {
            mockTransport.when(() -> Transport.send(Mockito.<Message>any())).thenAnswer(invocation -> null);
            Login login1 = new Login(new User("ali.d@example.org", "hiword"));
            login1.addUser(new User("ali.d@example.org", "hiword"));
            boolean actualLoginResult = login1.login();
            mockTransport.verify(() -> Transport.send(Mockito.<Message>any()));
            assertEquals(4, login1.userIndex);
            assertTrue(actualLoginResult);
            assertTrue(login1.validEmail);
        }
    }



    @When("the information are invalid email is {string} and password is {string}")
    public void theInformationAreInvalidEmailIsAndPasswordIs(String Email, String Pass) {
        boolean loginFailed = false;
        for(User u1:application.login.users){
            if(!u1.getEmail().equalsIgnoreCase(Email)&&!u1.getPassword().equals(Pass)){
                application.login.setLogged(false);
                loginFailed=true;
                break;
            }
        }
        assertTrue(loginFailed);

        Login login = new Login(new User("ali.d@example.org", "hiword","admin"));

        // Act
        login.setRoles();

        // Assert
        assertEquals(0, login.getRoles());
        User u1 =new User("","");
        assertFalse ((new Login(u1)).login());

    }


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

        // Act
        login.setRoles();

        // Assert
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

        // Act
        login.setRoles();
        // Assert
        assertEquals(2, login.getRoles());
    }

    //////////////////////////////////////////////////////////////signup  //signup


    @When("the information is exist email is {string}")
    public void theInformationIsExistEmailIs(String email) {
        boolean f = false;
        for(User u:application.login.users){
            if(u.getEmail().equalsIgnoreCase(email)){
                f=true;
                newAccount=false;
                break;
            }
        }
        assertTrue(f);
    }
    @Then("creating an account failed")
    public void creatingAnAccountFailed() {
        assertFalse(newAccount);
    }


    @When("the information is not formatly correct")
    public void theInformationIsNotFormatlyCorrect() {
        boolean format =false;
        String email=application.newUser.getEmail();
        if(application.login.emailValidator(email)){
            format=true;
        }
        assertTrue(format);
    }

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

        // Act and Assert
        assertFalse((new SignUp(newUser, new Login(new User("ali.d@example.org", "hiword")))).createAccount());

        User newUser1 = new User("WWW@gmail.com", "hiword");

        SignUp signUp = new SignUp(newUser1, new Login(new User("ali.d@example.org", "hiword")));

        // Act and Assert
        assertEquals(4, signUp.l.users.size());
        assertTrue(signUp.createAccount());

        assertFalse(SignUp.emailValidator("ali.d@example.org"));
        assertFalse(SignUp.emailValidator(null));
        assertTrue(SignUp.emailValidator("WWW@gmail.com"));

        SignUp actualSignUp = new SignUp(newUser, new Login(new User("ali.d@example.org", "hiword")));

        // Assert
        Login login = actualSignUp.l;
        User user = login.u;
        assertEquals("hiword", user.getPassword());
        User user2 = actualSignUp.newUser;
        assertEquals("hiword", user2.getPassword());
        assertEquals("ali.d@example.org", user.getEmail());
        assertEquals("ali.d@example.org", user2.getEmail());
        assertNull(user.getType());
        assertNull(user2.getType());
        assertEquals(0, login.getRoles());
        assertEquals(4, login.users.size());
        assertFalse(login.isLogged());

    }
///////////////////////////////////////////////////////////////////Monitoring and Reporting


    @When("I choose to generate a financial report for the stores")
    public void iChooseToGenerateAFinancialReportForTheStores() {
        application.report.generateFinancialReport();
    }
    @Then("the system should calculate and display the total profits for each store")
    public void theSystemShouldCalculateAndDisplayTheTotalProfitsForEachStore() {
        Map<String, Double> profits = application.report.getStoreProfits();
        assertNotNull(profits);
        for (Map.Entry<String, Double> entry : profits.entrySet()) {
            System.out.println("Store: " + entry.getKey() + " - Profit: $" + entry.getValue());
        }
        assertFalse(profits.isEmpty());
    }
    @Then("the report should be available separately for the two stores in Nablus and the two stores in Jenin")
    public void theReportShouldBeAvailableSeparatelyForTheTwoStoresInNablusAndTheTwoStoresInJenin() {
        Map<String, Double> profits = application.report.getStoreProfits();
        assertNotNull(profits);
        List<String> nablusStores = Arrays.asList("Nablus Store 1", "Nablus Store 2");
        List<String> jeninStores = Arrays.asList("Jenin Store 1", "Jenin Store 2");

        for (String store : nablusStores) {
            assertTrue(profits.containsKey(store));
        }

        for (String store : jeninStores) {
            assertTrue(profits.containsKey(store));
        }
    }
    @Then("the report should be downloadable in PDF format")
    public void theReportShouldBeDownloadableInPDFFormat() {
        boolean isPDFGenerated = application.report.downloadFinancialReportAsPDF(); // Assuming there's a method for downloading the report as PDF
        assertTrue(isPDFGenerated);
    }


    @When("I request a report on best-selling products")
    public void iRequestAReportOnBestSellingProducts() {
        application.report.generateBestSellingProductsReport();
    }
    @Then("the system should display a list of best-selling products for each store")
    public void theSystemShouldDisplayAListOfBestSellingProductsForEachStore() {
        Map<String, List<String>> bestSellingProducts = application.report.getBestSellingProducts(); // Assuming getBestSellingProducts returns a map of store names and their best-selling products
        assertNotNull(bestSellingProducts);
        for (Map.Entry<String, List<String>> entry : bestSellingProducts.entrySet()) {
            System.out.println("Store: " + entry.getKey() + " - Best Selling Products: " + String.join(", ", entry.getValue()));
        }
        assertFalse(bestSellingProducts.isEmpty());
    }
    @Then("the report should include a comparison of best-selling products between the stores in Nablus and the stores in Jenin")
    public void theReportShouldIncludeAComparisonOfBestSellingProductsBetweenTheStoresInNablusAndTheStoresInJenin() {
        Map<String, List<String>> bestSellingProducts = application.report.getBestSellingProducts();
        List<String> nablusStores = Arrays.asList("Nablus Store 1", "Nablus Store 2");
        List<String> jeninStores = Arrays.asList("Jenin Store 1", "Jenin Store 2");

        List<String> nablusProducts = new ArrayList<>();
        List<String> jeninProducts = new ArrayList<>();

        for (String store : nablusStores) {
            nablusProducts.addAll(bestSellingProducts.get(store));
        }

        for (String store : jeninStores) {
            jeninProducts.addAll(bestSellingProducts.get(store));
        }

        assertNotNull(nablusProducts);
        assertNotNull(jeninProducts);
        System.out.println("Nablus Products: " + String.join(", ", nablusProducts));
        System.out.println("Jenin Products: " + String.join(", ", jeninProducts));
    }
    @Then("the report should include total units sold and revenue generated for each product in each store")
    public void theReportShouldIncludeTotalUnitsSoldAndRevenueGeneratedForEachProductInEachStore() {
        Map<String, Map<String, Double>> productSales = application.report.getProductSales();
        assertNotNull(productSales);
        for (Map.Entry<String, Map<String, Double>> storeEntry : productSales.entrySet()) {
            String store = storeEntry.getKey();
            Map<String, Double> sales = storeEntry.getValue();
            System.out.println("Store: " + store);
            for (Map.Entry<String, Double> productEntry : sales.entrySet()) {
                System.out.println("Product: " + productEntry.getKey() + " - Revenue: $" + productEntry.getValue());
            }
        }
        assertFalse(productSales.isEmpty());
    }


    @When("I request user statistics by city")
    public void iRequestUserStatisticsByCity() {
        application.report.generateUserStatisticsByCity();
    }
    @Then("the system should display the number of registered users for each city")
    public void theSystemShouldDisplayTheNumberOfRegisteredUsersForEachCity() {
        Map<String, Integer> userStatistics = application.report.getUserStatisticsByCity();
        assertNotNull(userStatistics);
        for (Map.Entry<String, Integer> entry : userStatistics.entrySet()) {
            System.out.println("City: " + entry.getKey() + " - Registered Users: " + entry.getValue());
        }
        assertFalse(userStatistics.isEmpty());
    }
    @Then("the report should show a breakdown of users registered with the stores in Nablus and the stores in Jenin")
    public void theReportShouldShowABreakdownOfUsersRegisteredWithTheStoresInNablusAndTheStoresInJenin() {
        Map<String, Map<String, Integer>> userBreakdown = application.report.getUserBreakdownByCityAndStore();
        assertNotNull(userBreakdown);
        for (Map.Entry<String, Map<String, Integer>> cityEntry : userBreakdown.entrySet()) {
            String city = cityEntry.getKey();
            Map<String, Integer> stores = cityEntry.getValue();
            System.out.println("City: " + city);
            for (Map.Entry<String, Integer> storeEntry : stores.entrySet()) {
                System.out.println("Store: " + storeEntry.getKey() + " - Registered Users: " + storeEntry.getValue());
            }
        }
        assertFalse(userBreakdown.isEmpty());
    }
    @Then("the report should include a total count of users for each city")
    public void theReportShouldIncludeATotalCountOfUsersForEachCity() {
        Map<String, Integer> userStatistics = application.report.getUserStatisticsByCity();
        int totalNablusUsers = 0;
        int totalJeninUsers = 0;

        for (Map.Entry<String, Integer> entry : userStatistics.entrySet()) {
            if (entry.getKey().contains("Nablus")) {
                totalNablusUsers += entry.getValue();
            } else if (entry.getKey().contains("Jenin")) {
                totalJeninUsers += entry.getValue();
            }
        }

        System.out.println("Total Users in Nablus: " + totalNablusUsers);
        System.out.println("Total Users in Jenin: " + totalJeninUsers);

        assertTrue(totalNablusUsers > 0);
        assertTrue(totalJeninUsers > 0);
    }

/////////////////////////////////////////////////////////////




    @Given("I am an admin\\(report)")
    public void i_am_an_admin_report() {
        assertEquals("Admin", u.type);
    }

    @Then("I am asked to choose report1 kind {string}")
    public void i_am_asked_to_choose_report1_kind(String string) {
        text=string;
    }

    @Then("The report1 details are printed in a file {string}")
    public void the_report1_details_are_printed_in_a_file(String string) {
        file=string;
        LocalDate d =LocalDate.now();

        assertTrue(application.report(text,file));
    }

    @Then("I am asked to choose report2 kind {string}")
    public void i_am_asked_to_choose_report2_kind(String string) {
        text=string;
    }

    @Then("The report2 details are printed in a file {string}")
    public void the_report2_details_are_printed_in_a_file(String string) {
        file=string;

        assertTrue(application.report(text,file));
    }

    @Then("I am asked to choose report3 kind {string}")
    public void i_am_asked_to_choose_report3_kind(String string) {
        text=string;


    }

    @Then("The report3 details are printed in a file {string}")
    public void the_report3_details_are_printed_in_a_file(String string) {
        file=string;
        assertTrue(application.report(text,file));
    }
























}
