// gui/AdminDashboard.java
package gui;

import utils.MedicineManager;
import utils.UserManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.List;

public class AdminDashboard extends JFrame {
    
    /**
     * Load all users from UserManager into the table model
     * @param model The table model to populate
     */
    private void loadUsersTable(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);
        
        // Get all users and add them to the table
        String[][] users = UserManager.getAllUsers();
        for (String[] user : users) {
            model.addRow(user);
        }
    }
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create top panel with title and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // Close this dashboard
            new LoginScreen().setVisible(true); // Show login screen
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(logoutButton);
        
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Inventory Panel
        JPanel inventoryPanel = new JPanel(new BorderLayout());

// === Medicine Table ===
        String[] medColumns = {"Name", "Quantity", "Price", "Expiry"};
        DefaultTableModel medModel = new DefaultTableModel(medColumns, 0);
        JTable medTable = new JTable(medModel);
        JScrollPane medScroll = new JScrollPane(medTable);

// === Form Inputs ===
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField expiryField = new JTextField();

        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:")); formPanel.add(quantityField);
        formPanel.add(new JLabel("Price:")); formPanel.add(priceField);
        formPanel.add(new JLabel("Expiry Date (yyyy-mm-dd):")); formPanel.add(expiryField);

// === Action Buttons ===
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");

        JPanel medicineButtonPanel = new JPanel();
        medicineButtonPanel.add(addBtn); medicineButtonPanel.add(editBtn); medicineButtonPanel.add(delBtn);

// === Load Existing Medicines ===
        List<String[]> allMeds = MedicineManager.loadAll();  // Make sure you have this method
        for (String[] med : allMeds) medModel.addRow(med);

// === Add Button ===
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                LocalDate exp = LocalDate.parse(expiryField.getText().trim());

                // Validate name
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❌ Medicine name cannot be empty.");
                    return;
                }
                
                // Check if medicine with this name already exists
                if (MedicineManager.medicineExists(name)) {
                    JOptionPane.showMessageDialog(this, 
                        "❌ A medicine named '" + name + "' already exists.\nPlease use a unique name.", 
                        "Duplicate Medicine", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // All validation passed, add the medicine
                MedicineManager.addMedicine(name, qty, price, exp);
                medModel.addRow(new String[]{name, String.valueOf(qty), String.valueOf(price), exp.toString()});
                
                // Success message and clear fields
                JOptionPane.showMessageDialog(this, "✅ Medicine added successfully.");
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
                expiryField.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid quantity or price. Please enter numbers only.");
            } catch (DateTimeException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid date format. Use yyyy-mm-dd format.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

// === Edit Button ===
        editBtn.addActionListener(e -> {
            int row = medTable.getSelectedRow();
            if (row == -1) return;

            try {
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                LocalDate exp = LocalDate.parse(expiryField.getText().trim());

                MedicineManager.updateMedicine(row, name, qty, price, exp);
                medModel.setValueAt(name, row, 0);
                medModel.setValueAt(String.valueOf(qty), row, 1);
                medModel.setValueAt(String.valueOf(price), row, 2);
                medModel.setValueAt(exp.toString(), row, 3);
                JOptionPane.showMessageDialog(this, "✅ Updated.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input.");
            }
        });

// === Delete Button ===
        delBtn.addActionListener(e -> {
            int row = medTable.getSelectedRow();
            if (row == -1) return;
            MedicineManager.deleteMedicine(row);
            medModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "🗑️ Deleted.");
        });

// === Populate form on table click ===
        medTable.getSelectionModel().addListSelectionListener(e -> {
            int row = medTable.getSelectedRow();
            if (row != -1) {
                nameField.setText(medModel.getValueAt(row, 0).toString());
                quantityField.setText(medModel.getValueAt(row, 1).toString());
                priceField.setText(medModel.getValueAt(row, 2).toString());
                expiryField.setText(medModel.getValueAt(row, 3).toString());
            }
        });

        inventoryPanel.add(medScroll, BorderLayout.CENTER);
        inventoryPanel.add(formPanel, BorderLayout.NORTH);
        inventoryPanel.add(medicineButtonPanel, BorderLayout.SOUTH);

        tabs.addTab("Inventory", inventoryPanel); // Add inventory tab


        // Manage Users Tab
        JPanel userPanel = new JPanel(new BorderLayout());
        
        // === User Form Panel (Top) ===
        JPanel userFormPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        userFormPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Pharmacist", "Customer"});
        
        userFormPanel.add(new JLabel("Username:"));     userFormPanel.add(usernameField);
        userFormPanel.add(new JLabel("Password:"));      userFormPanel.add(passwordField);
        userFormPanel.add(new JLabel("Role:"));          userFormPanel.add(roleBox);
        
        // === User Table (Center) ===
        String[] userColumns = {"Username", "Password", "Role"};
        DefaultTableModel userModel = new DefaultTableModel(userColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        JTable userTable = new JTable(userModel);
        JScrollPane userScroll = new JScrollPane(userTable);
        
        // Load existing users into the table
        loadUsersTable(userModel);
        
        // === Action Buttons (Bottom) ===
        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addUserBtn = new JButton("Add User");
        JButton editUserBtn = new JButton("Update User");
        JButton deleteUserBtn = new JButton("Delete User");
        JButton clearFormBtn = new JButton("Clear Form");
        
        userButtonPanel.add(addUserBtn); 
        userButtonPanel.add(editUserBtn); 
        userButtonPanel.add(deleteUserBtn);
        userButtonPanel.add(clearFormBtn);
        
        JLabel userFeedback = new JLabel("");
        userFeedback.setHorizontalAlignment(JLabel.CENTER);
        userButtonPanel.add(userFeedback);
        
        // Fill the form when a user is selected
        userTable.getSelectionModel().addListSelectionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row != -1) {
                usernameField.setText(userModel.getValueAt(row, 0).toString());
                passwordField.setText(userModel.getValueAt(row, 1).toString());
                String role = userModel.getValueAt(row, 2).toString();
                roleBox.setSelectedItem(role.equals("Admin") ? "Admin" : role);
                
                // Disable role dropdown for Admin users
                roleBox.setEnabled(!role.equals("Admin"));
            }
        });
        
        // Clear form fields
        clearFormBtn.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            roleBox.setSelectedIndex(0);
            roleBox.setEnabled(true);
            userTable.clearSelection();
            userFeedback.setText("");
        });
        
        // Add User Button
        addUserBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pw = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (user.isBlank() || pw.isBlank()) {
                userFeedback.setText("❌ Username and password required.");
                return;
            }

            boolean success = UserManager.createUser(user, pw, role);
            if (success) {
                userFeedback.setText("✅ User created.");
                loadUsersTable(userModel); // Refresh the table
                clearFormBtn.doClick(); // Clear the form
            } else {
                userFeedback.setText("❌ Username already exists.");
            }
        });
        
        // Edit User Button
        editUserBtn.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) {
                userFeedback.setText("❌ Select a user to edit.");
                return;
            }
            
            String user = usernameField.getText().trim();
            String pw = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();
            
            if (user.isBlank() || pw.isBlank()) {
                userFeedback.setText("❌ Username and password required.");
                return;
            }
            
            boolean success = UserManager.updateUser(row, user, pw, role);
            if (success) {
                userFeedback.setText("✅ User updated.");
                loadUsersTable(userModel); // Refresh the table
                clearFormBtn.doClick(); // Clear the form
            } else {
                userFeedback.setText("❌ Update failed. Username may exist or you're trying to change admin role.");
            }
        });
        
        // Delete User Button
        deleteUserBtn.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) {
                userFeedback.setText("❌ Select a user to delete.");
                return;
            }
            
            String username = userModel.getValueAt(row, 0).toString();
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete user '" + username + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = UserManager.deleteUser(row);
                if (success) {
                    userFeedback.setText("✅ User deleted.");
                    loadUsersTable(userModel); // Refresh the table
                    clearFormBtn.doClick(); // Clear the form
                } else {
                    userFeedback.setText("❌ Cannot delete Admin user.");
                }
            }
        });
        
        userPanel.add(userFormPanel, BorderLayout.NORTH);
        userPanel.add(userScroll, BorderLayout.CENTER);
        userPanel.add(userButtonPanel, BorderLayout.SOUTH);

        tabs.addTab("Manage Users", userPanel);
        
        // Sales History Tab
        JPanel salesPanel = new JPanel(new BorderLayout());
        
        // Create text area for sales display
        JTextArea salesTextArea = new JTextArea();
        salesTextArea.setEditable(false);
        salesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane salesScrollPane = new JScrollPane(salesTextArea);
        
        // Add buttons panel
        JButton refreshButton = new JButton("Refresh Sales Data");
        JButton resetButton = new JButton("Reset Sales History");
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(resetButton);
        
        // Add confirmation label
        JLabel confirmationLabel = new JLabel("");
        confirmationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationLabel.setForeground(new Color(0, 128, 0)); // Green text
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.add(confirmationLabel, BorderLayout.CENTER);
        confirmPanel.add(buttonsPanel, BorderLayout.EAST);
        
        // Function to load sales data
        Runnable loadSalesData = () -> {
            String salesData = utils.PurchaseManager.getPurchasesAsString();
            if (salesData.trim().isEmpty()) {
                salesTextArea.setText("No sales recorded yet.");
            } else {
                salesTextArea.setText("SALES HISTORY\n\n" + salesData);
            }
        };
        
        // Load sales data initially
        loadSalesData.run();
        
        // Refresh button action
        refreshButton.addActionListener(e -> {
            loadSalesData.run();
            confirmationLabel.setText("");
        });
        
        // Reset button action
        resetButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to reset all sales history?\nThis action cannot be undone!",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = utils.PurchaseManager.resetPurchaseHistory();
                if (success) {
                    confirmationLabel.setText("✅ Sales history has been reset successfully");
                    salesTextArea.setText("No sales recorded yet.");
                } else {
                    confirmationLabel.setText("❌ Failed to reset sales history");
                }
            }
        });
        
        salesPanel.add(salesScrollPane, BorderLayout.CENTER);
        salesPanel.add(confirmPanel, BorderLayout.SOUTH);
        
        
        tabs.addTab("Sales History", salesPanel);
        add(tabs, BorderLayout.CENTER);
    }
}