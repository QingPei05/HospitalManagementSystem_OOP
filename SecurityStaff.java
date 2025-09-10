public class SecurityStaff extends Staff {
    private String clearanceLevel; // Low / Medium / High

    public SecurityStaff() {
        super();
        setDesignation("Security");
    }

    public SecurityStaff(String id, String name, String sex, int salary, String clearanceLevel) {
        super(id, name, "Security", sex, salary);
        this.clearanceLevel = HospitalManagement.normalizeClearanceLevel(clearanceLevel);
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Security Staff --");
        String id = HospitalManagement.readNonEmpty("Enter Staff ID: ");
        String name = HospitalManagement.readNonEmpty("Enter Name: ");
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
