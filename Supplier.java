import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Supplier {
    private String supplierId;
    private String name;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String address;
    private List<SupplyOrder> orderHistory;
    private List<String> suppliedMedicines;

    public Supplier(String supplierId, String name, String contactPerson, 
                   String email, String phoneNumber, String address) {
        if (supplierId == null || supplierId.isEmpty()) throw new IllegalArgumentException("Supplier ID required");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name required");
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email required");
        if (!email.contains("@")) throw new IllegalArgumentException("Invalid email format");

        this.supplierId = supplierId;
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderHistory = new ArrayList<>();
        this.suppliedMedicines = new ArrayList<>();
    }

    public SupplyOrder placeOrder(String medicineName, int quantity, double unitPrice) {
        SupplyOrder order = new SupplyOrder(
            "ORD-" + System.currentTimeMillis(),
            medicineName,
            quantity,
            unitPrice,
            new java.util.Date()
        );
        orderHistory.add(order);
        if (!suppliedMedicines.contains(medicineName)) {
            suppliedMedicines.add(medicineName);
        }
        return order;
    }

    public void viewOrderHistory() {
        System.out.println("Order History for " + name + ":");
        System.out.printf("%-15s %-20s %-10s %-10s %-12s%n", 
            "Order ID", "Medicine", "Quantity", "Unit Price", "Date");
        for (SupplyOrder order : orderHistory) {
            System.out.printf("%-15s %-20s %-10d $%-9.2f %-12s%n",
                order.getOrderId(),
                order.getMedicineName(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getOrderDate().toString().substring(0, 10));
        }
    }

    public void updateContactInfo(String contactPerson, String email, String phoneNumber, String address) {
        if (contactPerson != null && !contactPerson.isEmpty()) this.contactPerson = contactPerson;
        if (email != null && !email.isEmpty()) {
            if (!email.contains("@")) throw new IllegalArgumentException("Invalid email format");
            this.email = email;
        }
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters
    public String getSupplierId() { return supplierId; }
    public String getName() { return name; }
    public String getContactPerson() { return contactPerson; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public List<SupplyOrder> getOrderHistory() { return orderHistory; }
    public List<String> getSuppliedMedicines() { return suppliedMedicines; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return supplierId.equals(supplier.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }
}

class SupplyOrder {
    private String orderId;
    private String medicineName;
    private int quantity;
    private double unitPrice;
    private java.util.Date orderDate;

    public SupplyOrder(String orderId, String medicineName, int quantity, double unitPrice, java.util.Date orderDate) {
        this.orderId = orderId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderDate = orderDate;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public java.util.Date getOrderDate() { return orderDate; }
    public double getTotalPrice() { return quantity * unitPrice; }
}