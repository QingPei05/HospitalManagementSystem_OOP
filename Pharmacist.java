public class Pharmacist extends Staff {
    private String licenseNumber;

    public Pharmacist() {
        super();
        setDesignation("Pharmacist");
    }

    public Pharmacist(String id, String name, String sex, int salary, String licenseNumber) {
        super(id, name, "Pharmacist", sex, salary);
        this.licenseNumber = licenseNumber;
    }

    @Override
    public void newStaff() {
        System.out.println("\n-- New Pharmacist --");
        String id = HospitalManagement.readNonEmpty("Enter Staff ID: ");
        String name = HospitalManagement.readNonEmpty("Enter Name: ");
        String sex = HospitalManagement.readSex();
        int salary = HospitalManagement.readIntNonNegative("Enter Salary (>=0): ");
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
