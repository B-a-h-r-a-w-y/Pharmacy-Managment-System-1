public class Customer extends User {
    public Customer(String userId, String name) {
        super(userId, name, "Customer");
    }
    
    // Medicine viewing
    public void viewMedicines(List<Medicine> medicines) {
        System.out.println("Available medicines:");
        medicines.forEach(m -> System.out.println("- " + m.getName() + " ($" + m.getPrice() + ")"));
    }
    
    // Purchasing
    public void makePurchase(Sale sale) {
        sale.generateInvoice();
    }
}