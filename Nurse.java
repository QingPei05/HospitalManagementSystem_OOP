public class Nurse extends Staff {
    private String shift; // Day / Night

    public Nurse() {
        super();
        setDesignation("Nurse");
    }

    public Nurse(String id, String name, String sex, int salary, String shift) {
        super(id, HospitalManagement.formatText(name), "Nurse", sex, salary);  // Add formatText here
        this.shift = HospitalManagement.normalizeShift(shift);
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Nurse --");
        
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
        String shiftOk = HospitalManagement.readShift();  
        
        setId(id);
        setName(name);
        setDesignation("Nurse");
        setSex(sex);
        setSalary(salary);
        this.shift = shiftOk;
    }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = HospitalManagement.normalizeShift(shift); }
}
