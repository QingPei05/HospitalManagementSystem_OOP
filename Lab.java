public class Lab {
    private String lab; // lab name
    private int cost;

    public Lab() {}

    public Lab(String lab, int cost) {
    	this.lab = HospitalManagement.formatText(lab);
        this.cost = cost;
    }

    public void newLab() {
        System.out.println("\n-- New Lab --");
        this.lab = HospitalManagement.validateAndReadString("Enter Lab: ", "Lab");
        
        // Validate cost range
        while (true) {
            this.cost = HospitalManagement.readIntNonNegative("Enter Cost (1-100000): ");
            if (this.cost >= 1 && this.cost <= 100000) {
                break;
            }
            System.out.println("Lab cost should be between 1 and 100000.");
        }
    }

    public static void labList(Lab[] labs, int count) {
        System.out.println("\n=== ALL LABS ===");
        if (count == 0) {
            System.out.println("No labs found!");
            return;
        }
        System.out.printf("%-18s %-8s\n", "Lab", "Cost");
        HospitalManagement.printSeparator(18, 8);
        for (int i = 0; i < count; i++) {
            System.out.printf("%-18s %-8d\n",
                    HospitalManagement.truncateString(labs[i].getLab(), 18),
                    labs[i].getCost());
        }
    }

    public String getLab() { return lab; }
    public int getCost() { return cost; }
    public void setLab(String lab) { this.lab = HospitalManagement.formatText(lab); }
    public void setCost(int cost) { this.cost = cost; }
}
