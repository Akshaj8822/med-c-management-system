package notices.medicines;

public class Order {
    private String bitsId;
    private Integer itemId;
    private Integer quantity;
    private String payMode;

    public Order(String bitsId, Integer itemId, Integer quantity, String payMode) {
        this.bitsId = bitsId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.payMode = payMode;
    }

    public Order(String rawData) {
        String[] data = rawData.split(",");
        this.bitsId = data[0];
        this.itemId = Integer.valueOf(data[1]);
        this.quantity = Integer.valueOf(data[2]);
        this.payMode = data[3];
    }

    // getters
    public String getPayMode() {
        return payMode;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // overriden toString() method
    @Override
    public String toString() {
        return bitsId + "," + itemId.toString() + "," + quantity.toString() + "," + payMode;
    }
}
