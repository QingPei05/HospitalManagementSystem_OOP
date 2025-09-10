public class SecurityStaff extends Staff {
    private String clearanceLevel; // Low / Medium / High

    public SecurityStaff() {
        super();
        setDesignation("Security");
    }

    public SecurityStaff(String id, String name, String sex, int salary, String clearanceLevel) {
        super(id, HospitalManagement.formatText(name), "Security", sex, salary);  // Add formatText here
        this.clearanceLevel = HospitalManagement.normalizeClearanceLevel(clearanceLevel);
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Security Staff --");
        
        // Get existing staff IDs for duplicate check
        String[] existingIDs = new String[HospitalManagement.getStaffCount()];
        Staff[] staffs = HospitalManagement.getStaffs();
        for (int i = 0; i < HospitalManagement.getStaffCount(); i++) {
            existingIDs[i] = staffs[i].getId();
        }
        
        String id = HospitalManagement.validateAndFormatID("Enter Staff ID: ", existingIDs, HospitalManagement.getStaffCount());
        String name = HospitalManagement.validateAndReadString("Enter Name: ", "Name");
        String sex = HospitalManagement.readSex();
        int salary = HospitalManagement.readIntNonNegative("Enter Salary (>=0): ");
        String clOk = HospitalManagement.readClearanceLevelStrict();  
        
        setId(id);
        setName(name);
        setDesignation("Security");
        setSex(sex);
        setSalary(salary);
        this.clearanceLevel = clOk;   
    }

    public String getClearanceLevel() { return clearanceLevel; }
    public void setClearanceLevel(String clearanceLevel) {
        this.clearanceLevel = HospitalManagement.normalizeClearanceLevel(clearanceLevel);
    }
}
