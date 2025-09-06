import java.util.Scanner;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;
    private String admitStatus;
    private int age;
    
    public Patient() {
        this.id = "";
        this.name = "";
        this.disease = "";
        this.sex = "";
        this.admitStatus = "";
        this.age = 0;
    }
    
    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }
    
    // Method to create new patient with user input
    public void newPatient() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Patient ID: ");
            this.id = scanner.nextLine();
            
            System.out.print("Enter Patient Name: ");
            this.name = scanner.nextLine();
            
            System.out.print("Enter Disease: ");
            this.disease = scanner.nextLine();
            
            System.out.print("Enter Sex (Female/Male): ");
            this.sex = scanner.nextLine();
            
            System.out.print("Enter Admit Status: ");
            this.admitStatus = scanner.nextLine();
            
            System.out.print("Enter Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input! \nPlease enter a valid age (integer): ");
                scanner.next();
                System.out.println();
            }
            this.age = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            System.out.println("\nPatient added successfully!");
            
        } catch (Exception e) {
            System.out.println("\nAn error occurred while adding patient: " + e.getMessage());
        }
    }
    
    // Method to display patient information
    public void showPatientInfo() {
        System.out.println(id + " " + name + " " + disease + " " + sex + " " + admitStatus + " " + age);
    }
    
    // Getter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisease() { return disease; }
    public String getSex() { return sex; }
    public String getAdmitStatus() { return admitStatus; }
    public int getAge() { return age; }
    
    // Setter methods
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setSex(String sex) { this.sex = sex; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }
    public void setAge(int age) { this.age = age; }
}