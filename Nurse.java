public class Nurse extends Staff {
    private String shift; // Day / Night

    public Nurse() {
        super();
        setDesignation("Nurse");
    }

    public Nurse(String id, String name, String sex, int salary, String shift) {
        super(id, name, "Nurse", sex, salary);
        this.shift = HospitalManagement.normalizeShift(shift);
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Nurse --");
        String id = HospitalManagement.readNonEmpty("Enter Staff ID: ");
        String name = HospitalManagement.readNonEmpty("Enter Name: ");
        String sex = HospitalManagement.readSex();
        int salary = HospitalManagement.readIntNonNegative("Enter Salary (>=0): ");
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
