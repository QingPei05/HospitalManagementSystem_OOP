public class Staff {
    private String id;
    private String name;
    private String designation; // e.g., Staff / Nurse / Pharmacist / Security / Admin Clerk
    private String sex;         // M / F
    private int salary;         // >= 0

    public Staff() {}

    public Staff(String id, String name, String designation, String sex, int salary) {
        this.id = id;
        this.name = HospitalManagement.formatText(name);
        this.designation = HospitalManagement.formatText(designation);
        this.sex = sex;
        this.salary = salary;
    }

    public void newStaff() {
        System.out.println("\n-- New Staff --");
        
        // Get existing staff IDs for duplicate check
        String[] existingIDs = new String[HospitalManagement.getStaffCount()];
        Staff[] staffs = HospitalManagement.getStaffs();
        for (int i = 0; i < HospitalManagement.getStaffCount(); i++) {
            existingIDs[i] = staffs[i].getId();
        }
        
        this.id = HospitalManagement.validateAndFormatID("Enter Staff ID: ", existingIDs, HospitalManagement.getStaffCount());
        this.name = HospitalManagement.validateAndReadString("Enter Name: ", "Name");
        this.designation = HospitalManagement.validateAndReadString("Enter Designation: ", "Designation");
        this.sex = HospitalManagement.readSex();
        
        // Validate salary range
        while (true) {
            this.salary = HospitalManagement.readIntNonNegative("Enter Salary (1000-50000): ");
            if (this.salary >= 1000 && this.salary <= 50000) {
                break;
            }
            System.out.println("Salary should be between 1000 and 50000.");
        }
    }

    public static void showStaffInfo(Staff[] staffs, int count) {
        System.out.println("\n=== ALL STAFF ===");
        if (count == 0) {
            System.out.println("No staff found!");
            return;
        }

        System.out.printf("%-6s %-18s %-15s %-8s %-16s %-18s %-6s %-8s\n",
                "ID", "Name", "Designation", "Shift", "License Number", "Clearance Level", "Sex", "Salary");
        HospitalManagement.printSeparator(6, 18, 15, 8, 16, 18, 6, 8);

        for (int i = 0; i < count; i++) {
            Staff st = staffs[i];

            String designationToShow =
                (st.getDesignation() == null || st.getDesignation().trim().isEmpty())
                    ? ((st instanceof Nurse) ? "Nurse"
                      : (st instanceof Pharmacist) ? "Pharmacist"
                      : (st instanceof SecurityStaff) ? "Security" : "Staff")
                    : st.getDesignation();

            String shift = "-";
            String licenseNumber = "-";
            String clearanceLevel = "-";

            if (st instanceof Nurse) {
                shift = ((Nurse) st).getShift();
            } else if (st instanceof Pharmacist) {
                licenseNumber = ((Pharmacist) st).getLicenseNumber();
            } else if (st instanceof SecurityStaff) {
                clearanceLevel = HospitalManagement.normalizeClearanceLevel(((SecurityStaff) st).getClearanceLevel());
            }

            System.out.printf("%-6s %-18s %-15s %-8s %-16s %-18s %-6s %-8d\n",
                    st.getId(),
                    HospitalManagement.truncateString(st.getName(), 18),
                    HospitalManagement.truncateString(designationToShow, 15),
                    HospitalManagement.truncateString(shift, 8),
                    HospitalManagement.truncateString(licenseNumber, 16),
                    HospitalManagement.truncateString(clearanceLevel, 18),
                    HospitalManagement.truncateString(st.getSex(), 6),
                    st.getSalary());
        }
    }

    // Getters/Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }
    public String getSex() { return sex; }
    public int getSalary() { return salary; }
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = HospitalManagement.formatText(name); }
    public void setDesignation(String designation) { this.designation = HospitalManagement.formatText(designation); }
    public void setSex(String sex) { this.sex = sex; }
    public void setSalary(int salary) { this.salary = salary; }
}
