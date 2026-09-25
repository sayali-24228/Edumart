public class UnifiedListing {

    public static final String SELL = "SELL";
    public static final String DONATE = "DONATE";
    public static final String EXCHANGE = "EXCHANGE";

    private int id;
    private String listingType;
    private int ownerId;
    private String ownerName;
    private String name;
    private String description;
    private String category;
    private double price;
    private String conditionType;
    private String imagePath;
    private String wantedItem;
    private String wantedCategory;
    private String additionalRequirements;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getListingType() { return listingType; }
    public void setListingType(String listingType) { this.listingType = listingType; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getConditionType() { return conditionType; }
    public void setConditionType(String conditionType) { this.conditionType = conditionType; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getWantedItem() { return wantedItem; }
    public void setWantedItem(String wantedItem) { this.wantedItem = wantedItem; }

    public String getWantedCategory() { return wantedCategory; }
    public void setWantedCategory(String wantedCategory) { this.wantedCategory = wantedCategory; }

    public String getAdditionalRequirements() { return additionalRequirements; }
    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }
}
