public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;
    private int room;

    public Doctor() {}

    public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = id;
        this.name = HospitalManagement.formatText(name);
        this.specialist = HospitalManagement.formatText(specialist);
        this.workTime = workTime;
        this.qualification = HospitalManagement.formatText(qualification);
        this.room = room;
    }

    public void newDoctor() {
        System.out.println("\n-- New Doctor --");
        
        // Get existing doctor IDs for duplicate check
        String[] existingIDs = new String[HospitalManagement.getDoctorCount()];
        Doctor[] doctors = HospitalManagement.getDoctors();
        for (int i = 0; i < HospitalManagement.getDoctorCount(); i++) {
            existingIDs[i] = doctors[i].getId();
        }
        
        this.id = HospitalManagement.validateAndFormatID("Enter Doctor ID: ", existingIDs, HospitalManagement.getDoctorCount());
        this.name = HospitalManagement.validateAndReadString("Enter Name: ", "Name");
        this.specialist = HospitalManagement.validateAndReadString("Enter Specialist: ", "Specialist");
        this.workTime = HospitalManagement.readNonEmpty("Enter Work Time (Ex. 8am-10am): ");
        this.qualification = HospitalManagement.validateAndReadString("Enter Qualification: ", "Qualification");
        
        // Validate room number range
        while (true) {
            this.room = HospitalManagement.readIntNonNegative("Enter Room No (1-999): ");
            if (this.room >= 1 && this.room <= 999) {
                break;
            }
            System.out.println("Room number should be between 1 and 999.");
        }
    }

    public static void showDoctorInfo(Doctor[] doctors, int count) {
        System.out.println("\n=== ALL DOCTORS ===");
        if (count == 0) {
            System.out.println("No doctors found!");
            return;
        }
        System.out.printf("%-6s %-18s %-15s %-12s %-18s %-8s\n",
                "ID", "Name", "Specialist", "Work Time", "Qualification", "Room No.");
        HospitalManagement.printSeparator(6, 18, 15, 12, 18, 8);
        for (int i = 0; i < count; i++) {
            System.out.printf("%-6s %-18s %-15s %-12s %-18s %03d\n",
                    doctors[i].getId(),
                    HospitalManagement.truncateString(doctors[i].getName(), 18),
                    HospitalManagement.truncateString(doctors[i].getSpecialist(), 15),
                    HospitalManagement.truncateString(doctors[i].getWorkTime(), 12),
                    HospitalManagement.truncateString(doctors[i].getQualification(), 18),
                    doctors[i].getRoom());
        }
    }

    // Getters/Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialist() { return specialist; }
    public String getWorkTime() { return workTime; }
    public String getQualification() { return qualification; }
    public int getRoom() { return room; }
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = HospitalManagement.formatText(name); }
    public void setSpecialist(String specialist) { this.specialist = HospitalManagement.formatText(specialist); }
    public void setWorkTime(String workTime) { this.workTime = workTime; }
    public void setQualification(String qualification) { this.qualification = HospitalManagement.formatText(qualification); }
    public void setRoom(int room) { this.room = room; }
}
