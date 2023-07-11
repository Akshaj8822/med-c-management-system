package appointments;

import java.time.Instant;

/* APPOINTMENT CLASS
 * defines the structre of appointment.
 */

public class Appointment {
    private Integer doctorId;
    private String patientId;
    private Long timeStamp;
    private String appDate;
    private String appTime;

    // constructor for creating appointment
    public Appointment(Integer doctorId, String patientId, String appDate, String appTime) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.timeStamp = Instant.now().getEpochSecond(); // get unix epoch time when appointment is made
        this.appDate = appDate;
        this.appTime = appTime;
    }

    // copy constructor
    public Appointment(Long timeStamp, String patientId, Integer doctorId, String appDate, String appTime) {
        this.timeStamp = timeStamp;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appDate = appDate;
        this.appTime = appTime;
    }

    // setter for timeStamp
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    // getters
    public Integer getDoctorId() {
        return doctorId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getAppDate() {
        return appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    // overridden toString() method to return string in perscribed format
    public String toString() {
        return timeStamp.toString() + "," + patientId + "," + doctorId.toString() + "," + appDate + "," + appTime;
    }
}
