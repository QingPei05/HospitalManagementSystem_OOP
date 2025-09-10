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
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }

    public void newDoctor() {
        System.out.println("\n-- New Doctor --");
        this.id = HospitalManagement.readNonEmpty("Enter Doctor ID: ");
        this.name = HospitalManagement.readNonEmpty("Enter Name: ");
        this.specialist = HospitalManagement.readNonEmpty("Enter Specialist: ");
        this.workTime = HospitalManagement.readNonEmpty("Enter Work Time (Ex. 8am-10am): ");
        this.qualification = HospitalManagement.readNonEmpty("Enter Qualification: ");
        this.room = HospitalManagement.readIntNonNegative("Enter Room No (>=0): ");
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
            System.out.printf("%-6s %-18s %-15s %-12s %-18s %-8d\n",
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
    public void setName(String name) { this.name = name; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }
    public void setWorkTime(String workTime) { this.workTime = workTime; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setRoom(int room) { this.room = room; }
}
