package persons;

import appointments.AppointmentMaker;
import notices.Doctor;
import notices.NoticeBoard;

/* ADMIN CLASS
 * The Admin class extends user and adds administrative functionalities.
 * There exists methods to Manage the Studemt records, Notice Board, and Appointments
 */

public class Admin extends User {
    public Admin() {
        super("admin", "admin", "admin");
    }

    // CRUD operations for Notices (Create, Read, Update, Delete)
    public void addNotice(Doctor doc) {
        NoticeBoard nb = new NoticeBoard();
        nb.addNotice(doc);
    }

    public void deleteNotice(Doctor doc) {
        NoticeBoard nb = new NoticeBoard();
        nb.deleteNotice(doc);
    }

    public void updateNotice(int index, Doctor doc) {
        NoticeBoard nb = new NoticeBoard();
        nb.updateNotice(index, doc);
    }

    public void seeAllNotices() {
        NoticeBoard nb = new NoticeBoard();
        nb.showAllNotices();
    }

    // CRUD Operations for Student
    public void addStudent(Student s) {
        UsersDB db = new UsersDB();
        db.addStudent(s);
    }

    public void updateStudent(String username, Student newStudent) {
        UsersDB db = new UsersDB();
        db.updateStudent(username, newStudent);
    }

    public void seeAllStudents() {
        UsersDB db = new UsersDB();
        db.showAllStudents();
    }

    public void deleteStudent(String username) {
        UsersDB db = new UsersDB();
        db.deleteStudent(username);
    }

    // show all appointments created
    public void showAllAppointments() {
        AppointmentMaker app = new AppointmentMaker();
        app.showAllAppointments();
    }

}