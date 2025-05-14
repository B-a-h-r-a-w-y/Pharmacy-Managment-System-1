import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    private Map<String, Medicine> medicines = new HashMap<>();

    public void addMedicine(Medicine medicine) {
        if (medicine == null) throw new IllegalArgumentException("Medicine cannot be null");
        medicines.put(medicine.getMedicineId(), medicine);
    }

    public boolean removeMedicine(String medicineId) {
        return medicines.remove(medicineId) != null;
    }

    public boolean updateMedicine(Medicine medicine) {
        if (medicine == null) return false;
        if (!medicines.containsKey(medicine.getMedicineId())) return false;
        medicines.put(medicine.getMedicineId(), medicine);
        return true;
    }

    public Medicine getMedicine(String medicineId) {
        return medicines.get(medicineId);
    }

    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines.values());
    }

    public List<Medicine> getLowStockMedicines(int threshold) {
        return medicines.values().stream()
            .filter(m -> m.getStockQuantity() < threshold)
            .collect(Collectors.toList());
    }

    public List<Medicine> getExpiredMedicines() {
        Date now = new Date();
        return medicines.values().stream()
            .filter(m -> m.getExpiryDate().before(now))
            .collect(Collectors.toList());
    }

    public boolean sellMedicine(String medicineId, int quantity) {
        Medicine med = medicines.get(medicineId);
        if (med == null || med.getStockQuantity() < quantity) return false;
        med.reduceStock(quantity);
        return true;
    }
}