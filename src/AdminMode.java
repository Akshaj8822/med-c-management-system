
import persons.Admin;
import persons.Manager;

public class AdminMode {
    public Admin admin;
    public Manager manager;
    public boolean loginStatus;

    public AdminMode() {
        this.admin = new Admin();
        this.manager = new Manager();
        this.loginStatus = false;
    }

    // if username and password match, return Admin object
    public Admin loginAdmin(String username, String password) {
        loginStatus = admin.verifyPassword(username, password);
        return loginStatus ? admin : null;
    }

    // if username and password match, return Manager object
    public Manager loginManager(String username, String password) {
        loginStatus = manager.verifyPassword(username, password);
        return loginStatus ? manager : null;
    }

    // returns true if logout successful
    public boolean logout() {
        loginStatus = loginStatus ? false : true;
        return loginStatus;
    }
}
