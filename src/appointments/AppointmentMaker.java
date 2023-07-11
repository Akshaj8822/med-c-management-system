
package appointments;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

import interfaces.FileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.IllegalArgumentException;
import notices.Doctor;
import notices.NoticeBoard;

/* APPOINTMENT MAKER
 * class for creating and showing appointments.
 */

public class AppointmentMaker implements FileHandler {
    // create reference type for week
    // the key is day of of week
    // the value is a map of 10 min slots in that day i.e
    // 24 hours is split into 10 min slots. The boolean value
    // show if said slot is free.
    EnumMap<DayOfWeek, TreeMap<LocalTime, Boolean>> week;
    // reference type for appointments
    TreeMap<String, Appointment> appointments;

    // convert day string to DayOfWeek enum
    private DayOfWeek getDayOfWeek(String day) {
        DayOfWeek d = DayOfWeek.MONDAY;
        switch (day) {
            case "M":
                d = DayOfWeek.MONDAY;
                break;
            case "T":
                d = DayOfWeek.TUESDAY;
                break;
            case "W":
                d = DayOfWeek.WEDNESDAY;
                break;
            case "Th":
                d = DayOfWeek.THURSDAY;
                break;
            case "F":
                d = DayOfWeek.FRIDAY;
                break;
            case "Sa":
                d = DayOfWeek.SATURDAY;
                break;
            case "S":
                d = DayOfWeek.SUNDAY;
                break;
        }

        return d;
    }

    // constructor
    public AppointmentMaker() {
        // create new appointment map
        this.appointments = new TreeMap<String, Appointment>();

        // create empty EnumMap with key DayOfWeek enumeration and
        // TreeMap<LocalTime,Boolean> value.
        this.week = new EnumMap<DayOfWeek, TreeMap<LocalTime, Boolean>>(DayOfWeek.class);

        // now we populate the EnumMap with defualt state i.e all slots are free.
        // iterate over each day of week
        for (DayOfWeek day : DayOfWeek.values()) {
            // create new daySlot TreeMap
            TreeMap<LocalTime, Boolean> daySlot = new TreeMap<LocalTime, Boolean>();

            // create new LocalTime object and initialize it to 00:00 and add it to daySlot
            // all values will be true as no slots are booked yet
            LocalTime time = LocalTime.of(0, 0);
            daySlot.put(time, true);

            // add every 10 min increment to the daySlot TreeMap
            for (int i = 1; i < 24 * 6; i++) {
                daySlot.put(time.plusMinutes(i * 10), true);
            }

            // add day and daySlot to the week EnumMap
            week.put(day, daySlot);
        }
    }

    // create mew appointment (synchronized)
    public synchronized void createAppointment(String stringDay, String strTime, Integer doctorId, String patientId)
            throws IllegalArgumentException {
        reader();
        // convert startTime to LocalTime object
        LocalTime startTime = LocalTime.parse(strTime);
        // get the doctor object
        Doctor doc = new NoticeBoard().getNotice(doctorId);
        // get start and end time for doctor consultation time
        String[] times = doc.getTime().split(";");
        LocalTime docStartTime = LocalTime.parse(times[0]);
        LocalTime docEndTime = LocalTime.parse(times[1]);
        // appointment time
        LocalTime appTime = LocalTime.of(0, 0);
        // convert stringDay to DayOfWeek enum
        DayOfWeek day = getDayOfWeek(stringDay);
        // create daysAvaliable EnumMap and set all values corresponding to day to be
        // false for doctor (availability)
        EnumMap<DayOfWeek, Boolean> daysAvailable = new EnumMap<DayOfWeek, Boolean>(DayOfWeek.class);
        for (DayOfWeek d : DayOfWeek.values()) {
            daysAvailable.put(d, false);
        }
        // change value of day in EnumMap to true if doctor is available that dat
        for (String dayStr : doc.getDaysAvailable().split(";")) {
            switch (dayStr) {
                case "M":
                    daysAvailable.replace(DayOfWeek.MONDAY, true);
                    break;
                case "T":
                    daysAvailable.replace(DayOfWeek.TUESDAY, true);
                    break;
                case "W":
                    daysAvailable.replace(DayOfWeek.WEDNESDAY, true);
                    break;
                case "Th":
                    daysAvailable.replace(DayOfWeek.THURSDAY, true);
                    break;
                case "F":
                    daysAvailable.replace(DayOfWeek.FRIDAY, true);
                    break;
                case "Sa":
                    daysAvailable.replace(DayOfWeek.SATURDAY, true);
                    break;
                case "S":
                    daysAvailable.replace(DayOfWeek.SUNDAY, true);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal dayStr argument.");
            }
        }

        // if appointment day does not match doc consultation days
        if (!(daysAvailable.get(day))) {
            System.out.println(
                    "Appointment creation unsuccessful. Please make sure appointment creation day matches with Doctor's availability");
            return;
        }
        // if appointment time does not match doc consultation time
        if (!(startTime.isAfter(docStartTime) && startTime.isBefore(docEndTime))) {
            System.out.println(
                    "Appointment creation unsuccessful. Please make sure appointment creation time matches with Doctor's availability");
            return;
        }
        // get timeslot instance for the DayOfWeek day
        TreeMap<LocalTime, Boolean> timeSlot = week.get(day);
        // iterate over time slots of given day in week and reserve first available time
        // slot
        // within the range of docStartTime and docEndTime
        for (Map.Entry<LocalTime, Boolean> e : timeSlot.subMap(docStartTime, docEndTime).entrySet()) {
            if (e.getValue()) {
                appTime = e.getKey();
                e.setValue(false);
                break;
            }
        }
        // change timeSlot to updated timeSlot
        week.replace(day, timeSlot);

        // create new appointment
        Appointment app = new Appointment(doctorId, patientId, stringDay, appTime.toString());
        appointments.put(patientId, app);
        writer();
        System.out.println("Appointment creation successful");
    }

    // show all Appointments in table form
    public void showAllAppointments() {
        String alignedFormat = "| %-15d | %-15s | %-15d | %-15s | %-15s |%n";
        System.out.format("");
        System.out.format(
                "+-----------------+-----------------+-----------------+-----------------+-----------------+%n");
        System.out.format(
                "|    Timestamp    |     BITS ID     |    Doctor ID    |     App Date    |     App Time    |%n");
        for (Map.Entry<String, Appointment> e : appointments.entrySet()) {
            Appointment app = e.getValue();
            System.out.format(alignedFormat, app.getTimeStamp(), app.getPatientId(), app.getDoctorId(),
                    app.getAppTime(), app.getAppTime());
        }
        System.out.format(
                "+-----------------+-----------------+-----------------+-----------------+-----------------+%n");
    }

    public synchronized void reader() {
        try {
            // reader for appointments
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader("appointments.csv"));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                appointments.put(data[1],
                        new Appointment(Long.valueOf(data[0]), data[1], Integer.valueOf(data[2]), data[3], data[4]));
            }
            csvReader.close();

            // reader for week
            // format is "day,LocalTime,Boolean"
            BufferedReader csvReader2 = new BufferedReader(new FileReader("slots.csv"));
            while ((row = csvReader2.readLine()) != null) {
                String[] data = row.split(",");
                TreeMap<LocalTime, Boolean> timeSlot = week.get(getDayOfWeek(data[0]));
                LocalTime time = LocalTime.parse(data[1]);
                Boolean val = Boolean.valueOf(data[2]);
                timeSlot.replace(time, val);
            }
            csvReader2.close();

        } catch (Exception e) {
            System.out.println("AppointmentMaker.reader()");
        }
    }

    public synchronized void writer() {
        try {
            // writer for appointments
            FileWriter csvWriter = new FileWriter("appointments.csv", true);
            for (Map.Entry<String, Appointment> e : appointments.entrySet()) {
                Appointment a = e.getValue();
                csvWriter.append(a.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();

            // writer for week
            // format is "day,LocalTime,Boolean"
            FileWriter csvWriter2 = new FileWriter("slots.csv", true);
            for (Map.Entry<DayOfWeek, TreeMap<LocalTime, Boolean>> e : week.entrySet()) {
                TreeMap<LocalTime, Boolean> slot = e.getValue();
                for (Map.Entry<LocalTime, Boolean> ee : slot.entrySet()) {
                    csvWriter2.append(
                            e.getKey().toString() + "," + ee.getKey().toString() + "," + ee.getValue().toString());
                    csvWriter2.append("\n");
                }
                csvWriter2.flush();
                csvWriter2.close();
            }
        } catch (Exception e) {
            System.out.println("AppointmentMaker.writer()");
        } finally {
            appointments.clear();
        }
    }
}
