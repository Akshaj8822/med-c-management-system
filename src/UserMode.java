import persons.Student;
import persons.UsersDB;

public class UserMode {
    public Student student;
    public UsersDB db;
    public Boolean loginStatus;

    public UserMode() {
        this.student = null;
        this.db = new UsersDB();
        this.loginStatus = false;
    }

    // login handler
    public Student login(String username, String password) {
        loginStatus = db.checkPassword(username, password);

        return loginStatus ? student : null;
    }

    // logout handler
    public boolean logout() {
        loginStatus = loginStatus ? false : true;
        return loginStatus;
    }

    // handles the creation of new Student during runtime
    public boolean createNewStudent(String name, String bitsId, String bitsEmail, String mobileNo) {
        Student newStudent = new Student(name, bitsId, bitsEmail, mobileNo);

        db.addStudent(newStudent);
        return true;
    }
}
