public class Facility {
    private String facility;

    public Facility() {}

    public Facility(String facility) {
        this.facility = HospitalManagement.formatText(facility);
    }

    public void newFacility() {
        System.out.println("\n-- New Facility --");
        this.facility = HospitalManagement.validateAndReadString("Enter Facility: ", "Facility");
    }

    public static void showFacility(Facility[] facilities, int count) {
        System.out.println("\n=== ALL FACILITIES ===");
        if (count == 0) {
            System.out.println("No facilities found!");
            return;
        }
        System.out.printf("%-20s\n", "Facility");
        HospitalManagement.printSeparator(20);
        for (int i = 0; i < count; i++) {
            System.out.printf("%-20s\n",
                    HospitalManagement.truncateString(facilities[i].getFacility(), 20));
        }
    }

    public String getFacility() { return facility; }
    public void setFacility(String facility) { this.facility = HospitalManagement.formatText(facility); }
}
