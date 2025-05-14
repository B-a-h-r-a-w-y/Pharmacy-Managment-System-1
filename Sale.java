import java.util.Date;
import java.util.List;

public class Sale {
    private String saleId;
    private String customerName;
    private List<Medicine> medicines;
    private double totalAmount;
    private Date date;

    public Sale(String saleId, String customerName, List<Medicine> medicines) {
        if (saleId == null || saleId.isEmpty()) throw new IllegalArgumentException("Sale ID required");
        if (customerName == null || customerName.isEmpty()) throw new IllegalArgumentException("Customer name required");
        if (medicines == null || medicines.isEmpty()) throw new IllegalArgumentException("At least one medicine required");

        this.saleId = saleId;
        this.customerName = customerName;
        this.medicines = medicines;
        this.date = new Date();
        this.totalAmount = calculateTotal();
    }

    private double calculateTotal() {
        return medicines.stream().mapToDouble(Medicine::getPrice).sum();
    }

    public void generateInvoice() {
        System.out.println("=== INVOICE ===");
        System.out.println("Sale ID: " + saleId);
        System.out.println("Date: " + date);
        System.out.println("Customer: " + customerName);
        System.out.println("\nItems:");
        medicines.forEach(m -> 
            System.out.printf("- %s: $%.2f%n", m.getName(), m.getPrice()));
        System.out.printf("%nTotal: $%.2f%n", totalAmount);
    }

    // Getters
    public String getSaleId() { return saleId; }
    public String getCustomerName() { return customerName; }
    public List<Medicine> getMedicines() { return medicines; }
    public double getTotalAmount() { return totalAmount; }
    public Date getDate() { return date; }
}