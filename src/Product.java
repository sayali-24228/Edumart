public class Product {

    private int id;
    private int sellerId;
    private String name;
    private String description;
    private String category;
    private double price;
    private String conditionType;
    private String imagePath;

    // Empty constructor
    public Product() {
    }

    // Constructor
    public Product(
            int sellerId,
            String name,
            String description,
            String category,
            double price,
            String conditionType,
            String imagePath) {

        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.conditionType = conditionType;
        this.imagePath = imagePath;
    }

    // Get ID
    public int getId() {
        return id;
    }

    // Set ID
    public void setId(int id) {
        this.id = id;
    }

    // Get Seller ID
    public int getSellerId() {
        return sellerId;
    }

    // Set Seller ID
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    // Get Name
    public String getName() {
        return name;
    }

    // Set Name
    public void setName(String name) {
        this.name = name;
    }

    // Get Description
    public String getDescription() {
        return description;
    }

    // Set Description
    public void setDescription(String description) {
        this.description = description;
    }

    // Get Category
    public String getCategory() {
        return category;
    }

    // Set Category
    public void setCategory(String category) {
        this.category = category;
    }

    // Get Price
    public double getPrice() {
        return price;
    }

    // Set Price
    public void setPrice(double price) {
        this.price = price;
    }

    // Get Condition
    public String getConditionType() {
        return conditionType;
    }

    // Set Condition
    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    // Get Image Path
    public String getImagePath() {
        return imagePath;
    }

    // Set Image Path
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}