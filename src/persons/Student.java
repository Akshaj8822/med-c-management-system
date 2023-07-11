package persons;

import notices.NoticeBoard;

/* STUDENT CLASS
 * The Student class extends User and adds a tracking functionality
 * for dues and more attributes for storing student details.
 */

public class Student extends User {
    private String bitsId;
    private String bitsEmail;
    private String mobileNo;
    private Double dues;

    // initialize Student class object
    public Student(String username, String password, String name, String bitsId, String bitsEmail, String mobileNo) {
        super(name, username, password);
        this.bitsId = bitsId;
        this.bitsEmail = bitsEmail;
        this.mobileNo = mobileNo;
        this.dues = 0.0;
    }

    // initialize Student class object with default username and password
    // here username is bitsId and password is mobileNo
    public Student(String name, String bitsId, String bitsEmail, String mobileNo) {
        super(name, bitsId, mobileNo);
        this.bitsId = bitsId;
        this.bitsEmail = bitsEmail;
        this.mobileNo = mobileNo;
        this.dues = 0.0;
    }

    public String getName() {
        return super.getName();
    }

    // getter
    public String getBitsId() {
        return bitsId;
    }

    public String getBitsEmail() {
        return bitsEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    // update dues of a student
    public void updateDues(double amount) {
        this.dues += dues;
    }

    // show all notices
    public void seeAllNotices() {
        NoticeBoard nb = new NoticeBoard();
        nb.showAllNotices();
    }

    // return formatted string to write into csv
    public String toString() {
        return super.getName() + ',' + bitsId + ',' + bitsEmail + ',' + mobileNo;
    }
}
