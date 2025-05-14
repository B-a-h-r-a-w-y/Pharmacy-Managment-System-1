import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PharmacyGUI extends JFrame {
    private Inventory inventory;
    private List<User> users;
    private List<Supplier> suppliers;
    private List<Sale> sales;

    public PharmacyGUI(Inventory inventory, List<User> users, List<Supplier> suppliers, List<Sale> sales) {
        this.inventory = inventory;
        this.users = users;
        this.suppliers = suppliers;
        this.sales = sales;

        setTitle("Pharmacy Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Inventory Tab
        tabbedPane.addTab("Inventory", new InventoryPanel(inventory));
        
        // Sales Tab
        tabbedPane.addTab("Sales", new SalesPanel(inventory, sales));
        
        // Prescriptions Tab
        tabbedPane.addTab("Prescriptions", new PrescriptionPanel());
        
        // Suppliers Tab
        tabbedPane.addTab("Suppliers", new SuppliersPanel(suppliers));

        add(tabbedPane, BorderLayout.CENTER);
    }
}

class InventoryPanel extends JPanel {
    public InventoryPanel(Inventory inventory) {
        setLayout(new BorderLayout());
        
        // Table to display medicines
        String[] columnNames = {"ID", "Name", "Category", "Price", "Stock", "Expiry Date"};
        Object[][] data = inventory.getAllMedicines().stream()
            .map(m -> new Object[]{
                m.getMedicineId(),
                m.getName(),
                m.getCategory(),
                m.getPrice(),
                m.getStockQuantity(),
                m.getExpiryDate()
            })
            .toArray(Object[][]::new);
        
        JTable table = new JTable(data, columnNames);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Medicine"));
        buttonPanel.add(new JButton("Remove Medicine"));
        buttonPanel.add(new JButton("Check Expired"));
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

class SalesPanel extends JPanel {
    public SalesPanel(Inventory inventory, List<Sale> sales) {
        // Implementation for sales management
    }
}

class PrescriptionPanel extends JPanel {
    public PrescriptionPanel() {
        // Implementation for prescription management
    }
}

class SuppliersPanel extends JPanel {
    public SuppliersPanel(List<Supplier> suppliers) {
        // Implementation for supplier management
    }
}