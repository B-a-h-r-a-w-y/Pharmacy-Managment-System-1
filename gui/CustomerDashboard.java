package gui;

import javax.swing.*;
import java.awt.*;
import utils.FileManager;

public class CustomerDashboard extends JFrame {
    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main layout
        setLayout(new BorderLayout(10, 10));
        
        // Top panel with welcome message and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome Customer!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // Close this dashboard
            new LoginScreen().setVisible(true); // Show login screen
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(logoutButton);
        
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Medicine list
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setText("🛒 Available Medicines to Buy:\n\n" + FileManager.getMedicineListAsString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add all components to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}