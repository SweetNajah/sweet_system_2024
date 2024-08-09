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
    private static final String CATEGORY ="the Category ";
    private static final String ADMIN_STRING ="Admin";
    private static final String CUSTOMER_STRING ="Customer";
    private static final String EXIT_STRING =". Exit";
    private static final String INSTALLER_STRING ="Installer";
    private static final String INVALID_INPUT_MESSAGE = "Invalid Input";
    private static final String NEXT_TIME ="Enter a valid value in the next time\n";
    private static final String TABS ="     ";
    public static boolean comparePasswords(String inputPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(inputPassword.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexHash = new StringBuilder();
            for (byte b : hashBytes) {
                hexHash.append(String.format("%02x", b));
            }

            return hexHash.toString().equals(hashedPassword);
        } catch (NoSuchAlgorithmException e) {


            return false;
        }
    }

    private static final String INVALID_INFORMATION_PLEASE_TRY_AGAIN = "Invalid information! Please try again.";
    private static final String STRING = "********************************************************************";
    private static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());
    static {

        System.setProperty("mail.debug", "false");


        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.OFF);
            }
        }
    }

    public static void main(String[] arg) {

        Application signInApplication = new Application();
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
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(consoleHandler);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during logger configuration", e);
        }

        Scanner scanner = new Scanner(System.in);

        LOGGER.info("TURBOTWEAK ACCESSORIES");

        int authen = -1;

        do {
            try {
                LOGGER.info("1-Sign-up \n2-Sign-in \n3-Exit");
                authen = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (authen) {
                case 1 -> signUp(scanner, signInApplication);
                case 2 -> signIn(scanner, signInApplication);
                case 3 -> System.exit(0);
                default -> LOGGER.info("Invalid choice! Please try again.");
            }
        } while (authen != 3);

        scanner.close();
    }


    private static void signUp(Scanner scanner, Application signInApplication) {
        LOGGER.info("Enter your email: ");
        String email = scanner.nextLine();

        LOGGER.info("Enter your password: ");
        String password = scanner.nextLine();

        LOGGER.info("Enter your type: ");
        String type = scanner.nextLine();

        if (signInApplication.login.emailValidator(email)) {
            signInApplication.login.addUser(new User(email, password, type));
            LOGGER.info("User Created Successfully");
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }
    private static void signIn(Scanner scanner, Application signInApplication) {
        LOGGER.info("Enter your email: ");
        String signInEmail = scanner.nextLine();

        LOGGER.info("Enter your password: ");
        String signInPassword = scanner.nextLine();

        signInApplication.login.setUser(new User(signInEmail, signInPassword, ""));
        signInApplication.setUser(signInEmail, signInPassword, "");

        if (signInApplication.login.login()) {
            int verificationCode = getVerificationCode(scanner);

            if (signInApplication.login.confirmLogin(verificationCode)) {
                signInApplication.login.setRoles();
                handleRoles(scanner, signInApplication, signInEmail, signInPassword);
            } else {
                LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
            }
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
    }

    private static void adminDashboard(Scanner adminScanner, Application application) {
        int adminChoice = -1;

        do {
            try {
                LOGGER.info("Admin Dashboard");
                LOGGER.info("1-Show all Users\n2-Add User\n3-Delete User\n4-Update User\n5-Main Menu\n6-Sign out");
                adminChoice = adminScanner.nextInt();
                adminScanner.nextLine();
            } catch (InputMismatchException e) {
                LOGGER.warning("Invalid input. Please enter a valid integer.");
                adminScanner.nextLine();
                continue;
            }

            switch (adminChoice) {
                case 1 -> showAllUsers(application);
                case 2 -> addUser(adminScanner, application);
                case 3 -> deleteUser(adminScanner, application);
                case 4 -> updateUser(adminScanner, application);
                case 5 -> signOut();
                default -> invalidChoice();
            }
        } while (adminChoice != 5);
    }

    private static void signOut() {
        LOGGER.info("Sign Out");
        LOGGER.info(STRING);
    }

    private static void invalidChoice() {
        LOGGER.info("Invalid choice! Please try again.");
        LOGGER.info(STRING);
    }
    private static void deleteUser(Scanner adminScanner, Application application) {
        LOGGER.info("Enter user email that needs to be deleted: ");
        String email = adminScanner.nextLine();
        LOGGER.info("Enter user password that needs to be deleted: ");
        String password = adminScanner.nextLine();
        LOGGER.info("Enter your password to confirm deletion: ");
        String adminPassword = adminScanner.nextLine();
        if (application.newUser.getPassword().equals(adminPassword)) {
            if (application.login.deleteUser(new User(email, password))) {
                LOGGER.info("User Deleted Successfully");
            } else {
                LOGGER.info("User Delete Failed");
            }
        } else {
            LOGGER.info("Your Password Invalid! Please Try Again!");
        }
        LOGGER.info(STRING);
    }
    private static final String NO_CHANGE = "-1";
    private static void updateUser(Scanner adminScanner, Application application) {
        LOGGER.info("Enter user email that needs to be updated: ");
        String oldEmail = adminScanner.nextLine();
        LOGGER.info("Enter user password that needs to be updated: ");
        String oldPassword = adminScanner.nextLine();
        LOGGER.info("Enter your password to confirm updating: ");
        String adminPassword4 = adminScanner.nextLine();

        if (validateAdminPassword(oldPassword, oldEmail, adminPassword4, application)) {
            String oldType = findUserType(oldPassword, oldEmail, application.login.users);

            LOGGER.warning("If you want to update value just insert -1 ");
            String newEmail = getUserInput("Enter user new email that needs to be updated: ", adminScanner);
            String newPassword = getUserInput("Enter user new password that needs to be updated: ", adminScanner);
            String newType = getUserInput("Enter user new type that needs to be updated: ", adminScanner);

            updateUserInfo(oldEmail, oldPassword, oldType, newEmail, newPassword, newType, application);
        } else {
            LOGGER.info("Your Password Invalid! Please Try Again!");
        }

        LOGGER.info(STRING);
    }
    private static boolean validateAdminPassword(String oldPassword, String oldEmail, String adminPassword4, Application application) {
        return application.newUser.getPassword().equals(adminPassword4) &&
                application.login.users.stream()
                        .anyMatch(s -> oldPassword.equals(s.getPassword()) && oldEmail.equalsIgnoreCase(s.getEmail()));
    }

    private static String findUserType(String oldPassword, String oldEmail, List<User> users) {
        return users.stream()
                .filter(s -> oldPassword.equals(s.getPassword()) && oldEmail.equalsIgnoreCase(s.getEmail()))
                .findFirst()
                .map(User::getType)
                .orElse("");
    }

    private static String getUserInput(String prompt, Scanner scanner) {
        LOGGER.info(prompt);
        return scanner.nextLine();
    }

    private static void updateUserInfo(String oldEmail, String oldPassword, String oldType, String newEmail, String newPassword, String newType, Application application) {
        boolean scan = comparePasswords(NO_CHANGE, newPassword);

        if (scan) {
            newPassword = oldPassword;
        }
        if (NO_CHANGE.equals(newType)) {
            newType = oldType;
        }
        if (NO_CHANGE.equals(newEmail)) {
            newEmail = oldEmail;
        }

        if (application.login.updateUser(new User(oldEmail, oldPassword, oldType),
                new User(newEmail, newPassword, newType))) {
            LOGGER.info("User Updating Successfully");
        } else {
            LOGGER.info("User Updating Failed");
        }
    }
    private static void addUser(Scanner adminScanner, Application application) {
        LOGGER.info("Enter user email: ");
        String email1 = adminScanner.nextLine();
        LOGGER.info("Enter user password: ");
        String password1 = adminScanner.nextLine();
        LOGGER.info("Enter user type: ");
        String type = adminScanner.nextLine();
        User user = new User(email1, password1, type);
        if (application.login.addUser(user)) {
            LOGGER.info("User Added Successfully");
        } else {
            LOGGER.info(INVALID_INFORMATION_PLEASE_TRY_AGAIN);
        }
        LOGGER.info(STRING);
    }

    private static void showAllUsers(Application application) {
        for (User user : application.login.users) {
            LOGGER.info("Email: " + user.getEmail());
            LOGGER.info("Password: " + user.getPassword());
            LOGGER.info("Type: " + user.getType());
            LOGGER.info("------------------------------------");
        }
        LOGGER.info(STRING);
    }
    private static void handleInstallerOptions(Scanner scanner, Application signInApplication) {
        int select;

        while (true) {
            LOGGER.info("Choose an option:\n1. Install a Product\n2. Exit");
            select = scanner.nextInt();

            if (select == 1) {
               // addnewpro(signInApplication);//create fun addnewpro
            } else if (select == 2) {
                break;
            } else {
                LOGGER.info("Choose a right option\n");
            }
        }
    }

    private static void handleCustomerOptions(Scanner scanner, Application signInApplication) {
        int select;
        String options = """
        Choose an option:
        1. Order a product
        2. Review And Rate A Product
        3. Show all products
        4. Send a request
        5. Exit
        """;

        while (true) {
            LOGGER.info(options);
            select = scanner.nextInt();

            if (select == 1) {
               // orderproduct(signInApplication);
            } else if (select == 2) {
               // newrate(signInApplication);
            }
            else if(select ==3){
                //showallproducts(signInApplication);
            } else if (select ==4) {
               // makerequest(signInApplication);
            } else if(select ==5){
                break;
            }else {
                LOGGER.info("Choose a right option\n");
            }
        }
    }

    private static void handleRoles(Scanner scanner, Application signInApplication, String signInEmail, String signInPassword) {
        int roles = signInApplication.login.getRoles();
        String welcomeMsg="WELCOME TO TURBOTWEAK ACCESSORIE";
        switch (roles) {
            case 0 -> {
                LOGGER.info(welcomeMsg);
                signInApplication.setUser(signInEmail, signInPassword, ADMIN_STRING);
                adminDashboard(scanner, signInApplication);
            }
            case 1 -> {
                signInApplication.setUser(signInEmail, signInPassword, CUSTOMER_STRING);
                LOGGER.info(welcomeMsg);
                handleCustomerOptions(scanner, signInApplication);
            }
            default -> {
                signInApplication.setUser(signInEmail, signInPassword, INSTALLER_STRING);
                LOGGER.info(welcomeMsg);
                handleInstallerOptions(scanner, signInApplication);
            }
        }
    }
    private static int getVerificationCode(Scanner scanner) {
        LOGGER.info("Enter your verificationCode: ");
        return scanner.nextInt();
    }


    private static void addnewcat(Application application) {
        try {
            if (application.newUser.type.equals(ADMIN_STRING)) {
                LOGGER.info("What is the name of the Category?");
                String m = application.scanner.nextLine();

                if (application.foundc(m)) {
                    String ygy1 = CATEGORY + m + " already exists";
                    LOGGER.info(ygy1);
                } else {
                    addnewCategoryConfirmation(m, application);
                }
            } else {
                LOGGER.info("Only admins can add Categories");
            }
        } catch (Exception e) {
            LOGGER.info("An error occurred while adding a new category. Please try again.");
        }
    }

    private static void addnewCategoryConfirmation(String categoryName, Application application) {
        try {
            if (application.newUser.type.equals(ADMIN_STRING)) {
                String m = categoryName;
                if (application.foundc(m)) {
                    String ygy1 = CATEGORY + m + " already exists";
                    LOGGER.info(ygy1);
                } else {
                    boolean response = false;

                    LOGGER.info("Are you sure you want to continue?\n1. yes ");
                    int answer = application.scanner.nextInt();
                    application.scanner.nextLine();

                    if (answer == 1) response = true;

                    if (response) {
                        String ygy1 = "You added the Category " + m;
                        LOGGER.info(ygy1);

                    }
                }
            } else {
                LOGGER.info("Only admins can add Categories");
            }
        } catch (Exception e) {
            LOGGER.info(NEXT_TIME);
        }
    }



}
