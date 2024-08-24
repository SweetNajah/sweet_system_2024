package sweet_2024;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Login {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    String adminString ="Admin";
    String customerString ="Customer";
    String installerString ="Installer";
    List<User> users=new ArrayList<>();
    int roles;
    boolean isLogged;
    Mailing mailing;
    int verificationCode;
    User user;
    boolean validEmail;
    int userIndex;

    private String username;
    private String password;

    Login() {
        this(null);
    }

    Login(User user){
        this.user = user;
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
            consoleHandler.setLevel(Level.INFO);
            LOGGER.addHandler(consoleHandler);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during logger configuration", e);
        }
        User u1=new User("alidawood@gmail.com","123456", adminString);
        User u2=new User("loay@gmail.com","654321", installerString);
        User u3=new User("abdalbaset@gmail.com","987654", installerString);
        User u4=new User("ali55@gmail.com","123123", adminString);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        isLogged=false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean login() {
        if (emailValidator(user.getEmail())) {
            for (User s : users) {
                if (user.getPassword().equals(s.getPassword()) && user.getEmail().equalsIgnoreCase(s.getEmail())) {
                    mailing = new Mailing(user.getEmail());
                    setValidEmail(true);
                    mailing.sendVerificationCode();
                    userIndex=users.indexOf(s);
                    return true;
                }
            }
        }
        setValidEmail(false);
        return false;
    }

    public  boolean emailValidator(String email){
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean confirmLogin(int verificationCode){
        this.verificationCode=verificationCode;
        if(validEmail&& mailing.verificationCode==this.verificationCode){
            setLogged(true);
            return true;
        }
        return false;
    }

    public void setRoles() {
        String type=users.get(userIndex).getRole();
        if (type.equalsIgnoreCase(adminString)){
            roles=0;
        }
        else if (type.equalsIgnoreCase(customerString)){
            roles=1;
        }
        else if(type.equalsIgnoreCase(installerString)){
            roles= 2;
        }
        else {
            roles=-1;
        }
    }

    public int getRoles() {
        return roles;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public boolean addUser(User u){
        if(emailValidator(u.getEmail())){
            users.add(u);
            return true;
        }
        return false;
    }
    public void setUser(User u){
        this.user =u;
    }
    public boolean updateUser(User oldUser,User newUser){
        boolean isUpdating=false;
        for (User s : users) {
            if (oldUser.getPassword().equals(s.getPassword()) && oldUser.getEmail().equalsIgnoreCase(s.getEmail())) {
                userIndex = users.indexOf(s);
            }
        }
        if(emailValidator(newUser.getEmail())){
            users.get(userIndex).setRole(newUser.getRole());
            users.get(userIndex).setPassword(newUser.getPassword());
            users.get(userIndex).setEmail(newUser.getEmail());
            isUpdating=true;
        }
        return isUpdating;
    }
    public boolean removeUser(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    public User findUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public boolean deleteUser(User u){
        for(User r : users){
            if(r.getEmail().equals(u.getEmail())&&r.getPassword().equals(u.getPassword())){
                users.remove(r);
                return true;
            }
        }
        return false;
    }

    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                this.username = username;
                this.password = password;
                setUser(user);
                return true;
            }
        }
        return false;
    }

}
