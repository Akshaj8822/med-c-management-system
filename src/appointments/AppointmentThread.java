package appointments;

public class AppointmentThread implements Runnable {
    AppointmentMaker appMaker;
    String stringDay;
    String strTime;
    Integer doctorId;
    String patientId;

    public AppointmentThread(String stringDay, String strTime, Integer doctorId, String patientId) {
        this.appMaker = new AppointmentMaker();
        this.stringDay = stringDay;
        this.strTime = strTime;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public void run() {
        // only one thread can make an appointment at a time
        try {
            appMaker.createAppointment(stringDay, strTime, doctorId, patientId);
        } catch (IllegalArgumentException e) {
            System.out.println("AppointmentThread.run()");
        }
    }
}
