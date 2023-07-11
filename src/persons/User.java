package persons;

/* USER CLASS
 * This base class defines the basic behaviour of all users of the application
 */

public class User {
    private String name;
    private String username;
    private String password; // store encrpyted password

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    // return a formatted string to write into csv.
    public String toString() {
        return name + ',' + username;
    }

    public boolean verifyPassword(String username, String password) {
        String encryptedPassword = password;
        return this.password.equals(encryptedPassword);
    }
}