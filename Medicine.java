public class Medicine {
    private String name;
    private String manufacturer;
    private String expiryDate; // keep as string
    private int cost;
    private int count;

    public Medicine() {}

    public Medicine(String name, String manufacturer, String expiryDate, int cost, int count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.cost = cost;
        this.count = count;
    }

    public void newMedicine() {
        System.out.println("\n-- New Medicine --");
        this.name = HospitalManagement.readNonEmpty("Enter Medicine Name: ");
        this.manufacturer = HospitalManagement.readNonEmpty("Enter Manufacturer: ");
        this.expiryDate = HospitalManagement.readNonEmpty("Enter Expiry Date (YYYY-MM-DD): ");
        this.cost = HospitalManagement.readIntNonNegative("Enter Cost (>=0): ");
        this.count = HospitalManagement.readIntNonNegative("Enter Unit Count (>=0): ");
    }

    public static void findMedicine(Medicine[] meds, int count) {
        System.out.println("\n=== ALL MEDICINES ===");
        if (count == 0) {
            System.out.println("No medicines found!");
            return;
        }
        System.out.printf("%-18s %-18s %-15s %-8s\n",
                "Name", "Manufacturer", "Expiry Date", "Cost");
        HospitalManagement.printSeparator(18, 18, 15, 8);
        for (int i = 0; i < count; i++) {
            System.out.printf("%-18s %-18s %-15s %-8d\n",
                    HospitalManagement.truncateString(meds[i].getName(), 18),
                    HospitalManagement.truncateString(meds[i].getManufacturer(), 18),
                    HospitalManagement.truncateString(meds[i].getExpiryDate(), 15),
                    meds[i].getCost());
        }
    }

    // Getters/Setters
    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public String getExpiryDate() { return expiryDate; }
    public int getCost() { return cost; }
    public int getCount() { return count; }
    public void setName(String name) { this.name = name; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setCost(int cost) { this.cost = cost; }
    public void setCount(int count) { this.count = count; }
}
