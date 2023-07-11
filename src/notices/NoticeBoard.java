package notices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;

import interfaces.FileHandler;

/* Updated Notice Board Class 
 * Implements the FileReader interface defined in interfaces/FileHandler.java
 * reader and writer functions for reading from and writing to csv files.
 */

public class NoticeBoard implements FileHandler {
    // TreeMap<doctorId,Doctor>
    private TreeMap<Integer, Doctor> board;

    // default constructor for empty notice board
    public NoticeBoard() {
        this.board = new TreeMap<Integer, Doctor>();
    }

    // write file data to csv file.
    public void writer() {
        try {
            FileWriter csvWriter = new FileWriter("notice.csv", true);
            for (Map.Entry<Integer, Doctor> e : board.entrySet()) {
                Doctor doctor = e.getValue();
                csvWriter.append(doctor.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("NoticeBoard.writer()");
        } finally {
            board.clear();
        }
    }

    // read file data from csv file.
    public void reader() {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader("notice.csv"));
            while ((row = csvReader.readLine()) != null) {
                board.put(new Doctor(row).getId(), new Doctor(row));
            }
            csvReader.close();
        } catch (Exception e) {
            System.out.println("NoticeBoard.reader()");
        }
    }

    // Notice CRUD operations (Create Read Update Delete)
    // create new Notice
    public void addNotice(Doctor newDoctor) {
        reader();
        board.put(newDoctor.getId(), newDoctor);
        writer();
    }

    // read and show all Notices in tabular form
    public void showAllNotices() {
        reader();
        String alignedFormat = "| %-13d | %-13s | %-13s | %-13s | %-13s |%n";
        System.out.format("                                   NOTICE BOARD                                  %n");
        System.out.format("+---------------+---------------+---------------+---------------+---------------+%n");
        System.out.format("| Doctor ID     | Doctor Name   | Consult Type  | Availability  | Time          |%n");
        System.out.format("+---------------+---------------+---------------+---------------+---------------+%n");
        for (Map.Entry<Integer, Doctor> e : board.entrySet()) {
            // get value which is Doctor object
            Doctor doctor = e.getValue();
            System.out.format(alignedFormat, doctor.getId(), doctor.getName(), doctor.getConsultationType(),
                    doctor.getDaysAvailable(), doctor.getTime());
        }
        System.out.format("+---------------+---------------+---------------+---------------+---------------+%n");
        writer();
    }

    // search and get Doctor object based on docId
    public Doctor getNotice(Integer docId) {
        reader();
        Doctor doc = board.get(docId);
        writer();
        return doc;
    }

    // update a notice
    public void updateNotice(int index, Doctor newDoctor) {
        reader();
        board.replace(index, newDoctor);
        writer();
    }

    // delete a notice
    public void deleteNotice(Doctor newDoctor) {
        reader();
        board.remove(newDoctor.getId());
        writer();
    }
}
