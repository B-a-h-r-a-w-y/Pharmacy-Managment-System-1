public class Admin extends User {
    public Admin(String userId, String name) {
        super(userId, name, "Admin");
    }
    // Testing Bending Feature
    // Medicine management
    public void addMedicine(Medicine medicine) {
        System.out.println("Added medicine: " + medicine.getName());
    }
    
    public void updateMedicine(Medicine medicine) {
        System.out.println("Updated medicine: " + medicine.getName());
    }
    
    // Employee management
    public void addEmployee(User employee) {
        System.out.println("Added employee: " + employee.name);
    }
    
    // Sales reporting
    public void viewSalesReport() {
        System.out.println("Generated sales report");
    }
}
