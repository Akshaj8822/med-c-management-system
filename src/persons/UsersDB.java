package persons;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;

import interfaces.FileHandler;

/* USERSDB CLASS
 * This class manages the record of students and provides CRUD Operations.
 */

public class UsersDB implements FileHandler {
    // The map will be of the form <username, Student> pair.
    private TreeMap<String, Student> students;

    public UsersDB() {
        this.students = new TreeMap<String, Student>();
    }

    // read from csv file
    public void writer() {
        try {
            FileWriter csvWriter = new FileWriter("students.csv", true);
            for (Map.Entry<String, Student> e : students.entrySet()) {
                // write the value returned from overriden toString() in Student
                Student s = e.getValue();
                csvWriter.append(s.toString());
                // add newline
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("UsersDB.writer()");
        } finally {
            students.clear();
        }

    }

    // write to csv file
    public void reader() {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader("students.csv"));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                students.put(new Student(data[0], data[1], data[2], data[3]).getUsername(),
                        new Student(data[0], data[1], data[2], data[3]));
            }
            csvReader.close();
        } catch (Exception e) {
            System.out.println("UsersDB.reader()");
        }

    }

    // CRUD Operations
    // create new Student
    public void addStudent(Student newStudent) {
        reader();
        students.put(newStudent.getUsername(), newStudent);
        writer();
    }

    // read all Student info
    public void showAllStudents() {
        reader();
        // Not sure if this way of calling member functions of a class stored as value
        // in a Map works.
        String alignedFormat = "| %-15s | %-15s | %-15s | %-15s |%n";
        System.out.format("");
        System.out.format("+-----------------+-----------------+-----------------+-----------------+%n");
        System.out.format("|       Name      |     BITS ID     |    BITS Email   |  Mobile Number  |%n");
        System.out.format("+-----------------+-----------------+-----------------+-----------------+%n");
        for (Map.Entry<String, Student> e : students.entrySet()) {
            Student s = e.getValue();
            System.out.format(alignedFormat, s.getName(), s.getBitsId(), s.getBitsEmail(), s.getMobileNo());
        }
        System.out.format("+-----------------+-----------------+-----------------+-----------------+%n");
        writer();
    }

    // update Student info
    public void updateStudent(String username, Student updatedStudent) {
        reader();
        students.remove(username);
        students.put(updatedStudent.getUsername(), updatedStudent);
        writer();
    }

    // delete Student info
    public void deleteStudent(String username) {
        reader();
        students.remove(username);
        writer();
    }

    // handler for login
    public boolean checkPassword(String username, String password) {
        reader();
        Student s = students.get(username);
        writer();
        return s.verifyPassword(username, password);
    }
}