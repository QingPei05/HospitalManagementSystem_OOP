public class Pharmacist extends Staff {
    private String licenseNumber;

    public Pharmacist() {
        super();
        setDesignation("Pharmacist");
    }

    public Pharmacist(String id, String name, String sex, int salary, String licenseNumber) {
        super(id, HospitalManagement.formatText(name), "Pharmacist", sex, salary);  // Add formatText here
        this.licenseNumber = licenseNumber;
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Pharmacist --");
        
        // Get existing staff IDs for duplicate check
        String[] existingIDs = new String[HospitalManagement.getStaffCount()];
        Staff[] staffs = HospitalManagement.getStaffs();
        for (int i = 0; i < HospitalManagement.getStaffCount(); i++) {
            existingIDs[i] = staffs[i].getId();
        }
        
        int salary = HospitalManagement.readIntNonNegative("Enter Salary (>=1000 and <=50000): ");
        while (salary < 1000 || salary > 50000) {
            System.out.println("Salary should be between 1000 and 50000.");
            salary = HospitalManagement.readIntNonNegative("Enter Salary (1000-50000): ");
        }
        
        String id = HospitalManagement.validateAndFormatID("Enter Staff ID: ", existingIDs, HospitalManagement.getStaffCount());
        String name = HospitalManagement.validateAndReadString("Enter Name: ", "Name");
        String sex = HospitalManagement.readSex();
        String license = HospitalManagement.readNonEmpty("Enter License Number: ");
        setId(id);
        setName(name);
        setDesignation("Pharmacist");
        setSex(sex);
        setSalary(salary);
        this.licenseNumber = license;
    }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}
