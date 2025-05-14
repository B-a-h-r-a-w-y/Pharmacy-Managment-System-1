import java.util.*;
import java.util.stream.Collectors;

public class Prescription {
    private String prescriptionId;
    private String patientId;
    private String doctorName;
    private String doctorLicense;
    private Date dateIssued;
    private Date dateExpires;
    private List<PrescriptionItem> items;
    private String instructions;
    private boolean isFulfilled;

    public Prescription(String prescriptionId, String patientId, 
                       String doctorName, String doctorLicense,
                       Date dateIssued, int validityDays,
                       List<PrescriptionItem> items, String instructions) {
        if (prescriptionId == null || prescriptionId.isEmpty()) throw new IllegalArgumentException("Prescription ID required");
        if (patientId == null || patientId.isEmpty()) throw new IllegalArgumentException("Patient ID required");
        if (doctorName == null || doctorName.isEmpty()) throw new IllegalArgumentException("Doctor name required");
        if (doctorLicense == null || doctorLicense.isEmpty()) throw new IllegalArgumentException("Doctor license required");
        if (dateIssued == null) throw new IllegalArgumentException("Issue date required");
        if (validityDays <= 0) throw new IllegalArgumentException("Validity must be positive");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("At least one item required");

        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.doctorLicense = doctorLicense;
        this.dateIssued = dateIssued;
        this.dateExpires = new Date(dateIssued.getTime() + (long) validityDays * 24 * 60 * 60 * 1000);
        this.items = new ArrayList<>(items);
        this.instructions = instructions;
        this.isFulfilled = false;
    }

    public boolean validate() {
        // Check if prescription is expired
        if (new Date().after(dateExpires)) {
            return false;
        }
        
        // Check if already fulfilled
        if (isFulfilled) {
            return false;
        }
        
        // All items must have positive quantities
        return items.stream().allMatch(item -> item.getQuantity() > 0);
    }

    public void markAsFulfilled() {
        if (!validate()) {
            throw new IllegalStateException("Cannot fulfill invalid prescription");
        }
        this.isFulfilled = true;
    }

    public void addItem(Medicine medicine, int quantity, String dosage) {
        items.add(new PrescriptionItem(medicine, quantity, dosage));
    }

    public void printPrescription() {
        System.out.println("=== PRESCRIPTION ===");
        System.out.println("ID: " + prescriptionId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor: " + doctorName + " (License: " + doctorLicense + ")");
        System.out.println("Issued: " + dateIssued);
        System.out.println("Expires: " + dateExpires);
        System.out.println("\nMedications:");
        items.forEach(item -> System.out.printf("- %s: %d %s (%s)%n",
            item.getMedicine().getName(),
            item.getQuantity(),
            item.getMedicine().getCategory(),
            item.getDosage()));
        if (instructions != null && !instructions.isEmpty()) {
            System.out.println("\nInstructions: " + instructions);
        }
        System.out.println("\nStatus: " + (isFulfilled ? "FULFILLED" : "PENDING"));
    }

    // Getters
    public String getPrescriptionId() { return prescriptionId; }
    public String getPatientId() { return patientId; }
    public String getDoctorName() { return doctorName; }
    public String getDoctorLicense() { return doctorLicense; }
    public Date getDateIssued() { return dateIssued; }
    public Date getDateExpires() { return dateExpires; }
    public List<PrescriptionItem> getItems() { return items; }
    public String getInstructions() { return instructions; }
    public boolean isFulfilled() { return isFulfilled; }
}

class PrescriptionItem {
    private Medicine medicine;
    private int quantity;
    private String dosage;

    public PrescriptionItem(Medicine medicine, int quantity, String dosage) {
        if (medicine == null) throw new IllegalArgumentException("Medicine required");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.medicine = medicine;
        this.quantity = quantity;
        this.dosage = dosage;
    }

    // Getters
    public Medicine getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }
    public String getDosage() { return dosage; }
}