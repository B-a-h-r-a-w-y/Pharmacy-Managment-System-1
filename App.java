import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
    private static Inventory inventory = new Inventory();
    private static List<User> users = new ArrayList<>();
    private static List<Supplier> suppliers = new ArrayList<>();
    private static List<Sale> sales = new ArrayList<>();

    public static void main(String[] args) {
        initializeSampleData();
        
        // Start with GUI
        SwingUtilities.invokeLater(() -> {
            PharmacyGUI gui = new PharmacyGUI(inventory, users, suppliers, sales);
            gui.setVisible(true);
        });
    }

    private static void initializeSampleData() {
        // Sample medicines
        Medicine med1 = new Medicine("M001", "Paracetamol", "Painkiller", 10.0, 100, 
            new Date(System.currentTimeMillis() + 86400000 * 30)); // Expires in 30 days
        Medicine med2 = new Medicine("M002", "Amoxicillin", "Antibiotic", 25.0, 50, 
            new Date(System.currentTimeMillis() + 86400000 * 60)); // Expires in 60 days
        
        inventory.addMedicine(med1);
        inventory.addMedicine(med2);

        // Sample users
        users.add(new Admin("A001", "Admin", "admin@pharmacy.com", inventory));
        users.add(new Pharmacist("P001", "Pharmacist", "pharmacist@pharmacy.com", inventory));
        users.add(new Customer("C001", "Customer", "customer@pharmacy.com"));

        // Sample supplier
        suppliers.add(new Supplier("S001", "MedSupplier Inc.", "contact@medsupplier.com"));
    }
}