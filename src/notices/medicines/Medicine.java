package notices.medicines;

import java.util.Comparator;

public class Medicine implements Comparator<Medicine> {
    private Integer itemId;
    private String itemName;
    private Double price;
    private Integer quantity;

    public Medicine(Integer itemId, String itemName, Double price, Integer quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    // getters
    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
    }

    public String toString() {
        return itemId.toString() + ',' + itemName + ',' + price.toString() + ',' + quantity.toString();
    }

    // Overridden equals method of Comparator
    public boolean equals(Medicine m) {
        if (m == null)
            return false;
        if (this.getItemId() != m.getItemId())
            return false;
        if (!(m.getItemName().equals(this.getItemName())))
            return false;
        if (this.getPrice() != m.getPrice())
            return false;
        if (this.getQuantity() != m.getQuantity())
            return false;

        return true;
    }

    // Overridden compare method of Comparator
    public int compare(Medicine m1, Medicine m2) {
        return m1.getItemId().compareTo(m2.getItemId());
    }
}
