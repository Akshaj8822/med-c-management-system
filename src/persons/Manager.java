package persons;

import notices.medicines.OrderList;

/* MAMAGER
 * The Manager Class extends User and has the store management functionalities.
 * The manager can see summary of all purchases.
 */

public class Manager extends User {
    public Manager() {
        super("manager", "manager", "password");
    }

    // see summary of all orders as store manager
    public void seeSummary() {
        OrderList ol = new OrderList();
        ol.orderSummary();
    }
}
