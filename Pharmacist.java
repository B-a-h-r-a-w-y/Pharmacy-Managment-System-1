public class Pharmacist extends User {
    public Pharmacist(String userId, String name) {
        super(userId, name, "Pharmacist");
    }
    
    // Prescription handling
    public void processPrescription(Prescription p) {
        System.out.println("Processed prescription for " + p.getPatientName());
    }
    
    // Medicine dispensing
    public void dispenseMedicine(Medicine m) {
        System.out.println("Dispensed: " + m.getName());
    }
}