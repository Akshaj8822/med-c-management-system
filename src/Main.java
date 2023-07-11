import java.io.Console;
import java.io.IOException;

import appointments.AppointmentThread;
import notices.Doctor;
import notices.medicines.OrderList;
import persons.Admin;
import persons.Manager;
import persons.Student;

public class Main {

    public static void main(String[] args) throws IOException {
        AdminMode adminMode = new AdminMode();
        UserMode userMode = new UserMode();
        String mode = "";
        Console c = System.console();
        do {
            // home message
            System.out.println("------------------WELCOME------------------");
            System.out.println("Welcome to MedC Management System.");
            System.out.println("Which mode do you want to use?[User/Admin]");
            mode = c.readLine();

            if (mode.equalsIgnoreCase("user")) {
                int n = 0;
                while (n != 3) {
                    System.out.println("------------------USER MODE------------------");
                    System.out.println("Welcome User");
                    System.out.println("What task do you wish to do?");
                    System.out.println("1.Create Account");
                    System.out.println("2.Login");
                    System.out.println("3.Go Back");
                    n = Integer.parseInt(c.readLine());

                    if (n == 1) {

                        String name;
                        String bitsId;
                        String bitsEmail;
                        String mobileNo;
                        System.out.println("------------------Create New Account------------------");
                        System.out.println("Enter the following details: ");
                        System.out.println("Enter name: ");
                        name = c.readLine();
                        System.out.println("Enter BITS ID: ");
                        bitsId = c.readLine();
                        System.out.println("Enter BITS Email: ");
                        bitsEmail = c.readLine();
                        System.out.println("Enter mobile number: ");
                        mobileNo = c.readLine();
                        Boolean status = userMode.createNewStudent(name, bitsId, bitsEmail, mobileNo);
                        if (status)
                            System.out.println("successful");
                        else
                            System.out.println("Unsuccessful");

                    } else if (n == 2) {
                        String username, password;
                        System.out.println("------------------Login------------------");
                        System.out.println("Enter username: ");
                        username = c.readLine();
                        System.out.println("Enter password: ");
                        password = c.readLine();

                        Student student = userMode.login(username, password);

                        int a = 0;
                        while (a != 4) {
                            System.out.println("------------------Welcome Student------------------");
                            System.out.println("What do you want to do?");
                            System.out.println("1.Book Appointment");
                            System.out.println("2.Show Notice Board");
                            System.out.println("3.Purchase Medicine");
                            System.out.println("4.Logout");

                            if (a == 1) {
                                String day;
                                String time;
                                Integer doctorId;
                                String patientId = student.getBitsId();
                                System.out.println("------------------Welcome Student------------------");
                                System.out.println("Enter doctorId");
                                doctorId = Integer.parseInt(c.readLine());
                                System.out.println("Enter day of appointment");
                                day = c.readLine();
                                System.out.println("Enter appointment time");
                                time = c.readLine();
                                try {
                                    Thread t = new Thread(new AppointmentThread(day, time, doctorId, patientId));
                                    t.run();
                                } catch (Exception e) {
                                    System.out.println("Main.main()");
                                }

                            } else if (a == 2) {
                                student.seeAllNotices();
                            } else if (a == 3) {
                                String bitsId = student.getBitsId();
                                Integer itemId;
                                Integer quantity;
                                String payMode;
                                System.out.println("------------------Place Order------------------");
                                System.out.println("Enter item ID");
                                itemId = Integer.parseInt(c.readLine());
                                System.out.println("Enter quantity");
                                quantity = Integer.parseInt(c.readLine());
                                System.out.println("Enter payment mode [Cash/Later]");
                                payMode = c.readLine();
                                OrderList ol = new OrderList();
                                ol.placeOrder(bitsId, itemId, quantity, payMode);
                            } else if (a == 4) {
                                userMode.logout();
                                break;
                            } else {
                                System.out.println("Enter correct integer");
                            }

                        }
                    } else if (n == 3) {
                        break;
                    } else {
                        System.out.println("Enter correct integer");
                    }
                }

            } else if (mode.equalsIgnoreCase("admin")) {
                int n = 0;
                while (n != 5) {
                    System.out.println("------------------ADMIN MODE------------------");
                    System.out.println("Welcome Admin");
                    System.out.println("What task do you wish to do?");
                    System.out.println("1.See all student records");
                    System.out.println("2.See all appointments");
                    System.out.println("3.Update Notice Board");
                    System.out.println("4.See Summary");
                    System.out.println("5.Go Back");
                    n = Integer.parseInt(c.readLine());

                    if (n == 1) {
                        Admin admin = adminMode.loginAdmin("admin", "admin");
                        admin.seeAllStudents();
                    } else if (n == 2) {
                        Admin admin = adminMode.loginAdmin("admin", "admin");
                        admin.showAllAppointments();
                    } else if (n == 3) {
                        Admin admin = adminMode.loginAdmin("admin", "admin");
                        int a = 0;
                        while (a != 3) {
                            System.out.println("------------------Notice Manager------------------");
                            System.out.println("Welcome. What do you wish to do?");
                            System.out.println("1.See All Notices");
                            System.out.println("2.Add New Notice");
                            System.out.println("3.Go Back");
                            a = Integer.parseInt(c.readLine());

                            if (a == 1) {
                                admin.seeAllNotices();
                            } else if (a == 2) {
                                String doctorName;
                                Integer doctorId;
                                String consultationType;
                                String time; // format "Start_time;End_time"
                                String daysAvailable;
                                System.out.println("-----------------");
                                System.out.println("Enter Doctor Name: ");
                                doctorName = c.readLine();
                                System.out.println("Enter Doctor ID: ");
                                doctorId = Integer.parseInt(c.readLine());
                                System.out.println("Enter Doctor Consultation Type: ");
                                consultationType = c.readLine();
                                System.out.println("Enter Doctor's Available Time: ");
                                time = c.readLine();
                                System.out.println("Enter Doctor's Days Available: ");
                                daysAvailable = c.readLine();
                                admin.addNotice(
                                        new Doctor(doctorName, doctorId, consultationType, time, daysAvailable));
                                System.out.println("New record created.");
                            } else if (a == 3) {
                                adminMode.logout();
                                break;
                            } else {
                                System.out.println("Enter correct integer");
                            }
                        }
                    } else if (n == 4) {
                        Manager manager = adminMode.loginManager("manager", "manager");
                        manager.seeSummary();
                    } else if (n == 5) {
                        break;
                    } else {
                        System.out.println("Enter correct integer");
                    }

                }
            } else {
                System.out.println("Please enter only [User/Admin]");
            }
        } while (true);
    }
}