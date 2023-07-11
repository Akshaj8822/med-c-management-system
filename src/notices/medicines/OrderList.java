package notices.medicines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import interfaces.FileHandler;

public class OrderList implements FileHandler {
    private ArrayList<Order> orders;

    // create new empty ArrayList object
    public OrderList() {
        this.orders = new ArrayList<Order>();
    }

    // write to csv file
    public void writer() {
        try {
            FileWriter csvWriter = new FileWriter("order_list.csv", true);
            for (Order order : orders) {
                csvWriter.append(order.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("OrderList.writer()");
        } finally {
            orders.removeAll(orders);
        }
    }

    // read from csv file
    public void reader() {
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader("order_list.csv"));
            while ((row = csvReader.readLine()) != null) {
                orders.add(new Order(row));
            }
            csvReader.close();
        } catch (Exception e) {
            System.out.println("OrderList.reader()");
        }
    }

    // order placing function
    public void placeOrder(String bitsId, Integer itemId, Integer quantity, String payMode) {
        // create new order object
        Order newOrder = new Order(bitsId, itemId, quantity, payMode);

        // add newOrder to order list
        reader();
        orders.add(newOrder);
        writer();

        // create medicine list object
        MedicineList medList = new MedicineList();

        // get medicine object corresponding to itemId
        Medicine med = medList.getMedicine(itemId);

        // update the quantity of medicine
        med.setQuantity(med.getQuantity() - quantity);
        medList.updateMedicine(itemId, med);

        // update the revenue, due amount and purchase count
        if (payMode.equalsIgnoreCase("later")) {
            medList.setDueAmount(med.getPrice());
        }
        medList.setRevenue(med.getPrice());
        medList.setPurchaseCount(1);
    }

    // show summary of all orders
    public void orderSummary() {
        Double revenue = 0.0;
        Double dueAmount = 0.0;
        reader();
        MedicineList medList = new MedicineList();
        for (Order order : orders) {
            // revenue of one order is:
            // price * quantity
            revenue += medList.getMedicine(order.getItemId()).getPrice() * order.getQuantity();
            // dueAmount is updated only if Payment mode is "Later"
            dueAmount += order.getPayMode().equals("Later")
                    ? medList.getMedicine(order.getItemId()).getPrice() * order.getQuantity()
                    : 0.0;
        }
        System.out.format("---------------SUMMARY OF ORDERS---------------%n");
        System.out.format("The total number of orders placed: %d%n", orders.size());
        System.out.format("The total revenue generated is: %f%n", revenue);
        System.out.format("The total dues are: %f%n", dueAmount);
        writer();
    }
}
