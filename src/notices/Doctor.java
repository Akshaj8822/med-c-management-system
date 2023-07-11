package notices;

import java.util.Comparator;

/* Updated Doctor Class 
 * Changes are marked in the code .
 * The first constructor is a basic copy constructor.
 * Breaking changes (if any) in user class will be addressed in next commit
*/

public class Doctor implements Comparator<Doctor> {
    private String doctorName;
    private Integer doctorId;
    private String consultationType;
    private String time; // format "Start_time;End_time"
    private String daysAvailable;

    // copy constructor
    public Doctor(String doctorName, Integer doctorId, String consultationType, String time,
            String daysAvailable) {
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.consultationType = consultationType;
        this.time = time;
        this.daysAvailable = daysAvailable;
    }

    // constructor for string raw data
    public Doctor(String rawData) {
        try {
            String data[] = rawData.split(",");
            this.doctorId = Integer.parseInt(data[0]);
            this.doctorName = data[1];
            this.consultationType = data[2];
            this.daysAvailable = data[3];
            this.time = data[4];
        } catch (Exception e) {
            System.err.println("Error in instantiating Doctor from raw data line");
        }
    }

    // getters
    public String getName() {
        return doctorName;
    }

    public Integer getId() {
        return doctorId;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public String getTime() {
        return time;
    }

    public String getDaysAvailable() {
        return daysAvailable;
    }

    // get a formatted string for writing into files
    public String toString() {
        return doctorId.toString() + ',' + doctorName + ',' + consultationType + ',' + daysAvailable + ',' + time;
    }

    // Overridden equals method
    public boolean equals(Doctor doc) {
        if (doc == null)
            return false;
        if (this.getId() != doc.getId())
            return false;
        if (!(this.getName().equals(doc.getName())))
            return false;
        if (!(this.getConsultationType().equals(doc.getConsultationType())))
            return false;
        if (!(this.getTime().equals(doc.getTime())))
            return false;
        if (!(this.getDaysAvailable().equals(doc.getDaysAvailable())))
            return false;

        return true;
    }

    // Overridden compare method of Comparator
    public int compare(Doctor d1, Doctor d2) {
        return d1.getId().compareTo(d2.getId());
    }
}
