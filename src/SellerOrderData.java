import java.sql.Timestamp;

public class SellerOrderData {

    private int orderId;
    private int productId;

    private String productName;

    private String buyerName;
    private String buyerEmail;

    private int quantity;

    private double totalPrice;

    private String status;

    private Timestamp orderDate;


    // ==========================================
    // ORDER ID
    // ==========================================

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    // ==========================================
    // PRODUCT ID
    // ==========================================

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    // ==========================================
    // PRODUCT NAME
    // ==========================================

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    // ==========================================
    // BUYER NAME
    // ==========================================

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }


    // ==========================================
    // BUYER EMAIL
    // ==========================================

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }


    // ==========================================
    // QUANTITY
    // ==========================================

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // ==========================================
    // TOTAL PRICE
    // ==========================================

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    // ==========================================
    // STATUS
    // ==========================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ==========================================
    // ORDER DATE
    // ==========================================

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}