import java.util.Date;

public class Medicine {
    private String medicineId;
    private String name;
    private double price;
    private int stock;
    private Date expiryDate;
    
    public Medicine(String id, String name, double price, int stock, Date expiry) {
        this.medicineId = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expiryDate = expiry;
    }
    
    // Inventory management
    public boolean isExpired() {
        return new Date().after(expiryDate);
    }
    
    public void updateStock(int quantity) {
        stock += quantity;
    }
    
    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}