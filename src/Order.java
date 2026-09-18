public class Order {

    private int id;
    private int buyerId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private String orderStatus;

    // Empty constructor
    public Order() {
    }

    // Constructor
    public Order(
            int buyerId,
            int productId,
            int quantity,
            double totalPrice,
            String orderStatus) {

        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    // Get ID
    public int getId() {
        return id;
    }

    // Set ID
    public void setId(int id) {
        this.id = id;
    }

    // Get Buyer ID
    public int getBuyerId() {
        return buyerId;
    }

    // Set Buyer ID
    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    // Get Product ID
    public int getProductId() {
        return productId;
    }

    // Set Product ID
    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Get Quantity
    public int getQuantity() {
        return quantity;
    }

    // Set Quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Get Total Price
    public double getTotalPrice() {
        return totalPrice;
    }

    // Set Total Price
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Get Order Status
    public String getOrderStatus() {
        return orderStatus;
    }

    // Set Order Status
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}