public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;         // M/F
    private String admitStatus; // e.g. Admitted/Outpatient/Discharged
    private int age;

    public Patient() {}

    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }

    public void newPatient() {
        System.out.println("\n-- New Patient --");
        this.id = HospitalManagement.readNonEmpty("Enter Patient ID: ");
        this.name = HospitalManagement.readNonEmpty("Enter Name: ");
        this.disease = HospitalManagement.readNonEmpty("Enter Disease: ");
        this.sex = HospitalManagement.readSex();
        this.admitStatus = HospitalManagement.readNonEmpty("Enter Admit Status: ");
        this.age = HospitalManagement.readIntNonNegative("Enter Age (>=0): ");
    }

    public static void showPatientInfo(Patient[] patients, int count) {
        System.out.println("\n=== ALL PATIENTS ===");
        if (count == 0) {
            System.out.println("No patients found!");
            return;
        }
        System.out.printf("%-6s %-18s %-15s %-8s %-15s %-5s\n",
                "ID", "Name", "Disease", "Sex", "Admit Status", "Age");
        HospitalManagement.printSeparator(6, 18, 15, 8, 15, 5);
        for (int i = 0; i < count; i++) {
            System.out.printf("%-6s %-18s %-15s %-8s %-15s %-5d\n",
                    patients[i].getId(),
                    HospitalManagement.truncateString(patients[i].getName(), 18),
                    HospitalManagement.truncateString(patients[i].getDisease(), 15),
                    HospitalManagement.truncateString(patients[i].getSex(), 8),
                    HospitalManagement.truncateString(patients[i].getAdmitStatus(), 15),
                    patients[i].getAge());
        }
    }

    // Getters/Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisease() { return disease; }
    public String getSex() { return sex; }
    public String getAdmitStatus() { return admitStatus; }
    public int getAge() { return age; }
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setSex(String sex) { this.sex = sex; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }
    public void setAge(int age) { this.age = age; }
}
