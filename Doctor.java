import java.util.Scanner;

public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;
    private int room;
    
    public Doctor() {
        this.id = "";
        this.name = "";
        this.specialist = "";
        this.workTime = "";
        this.qualification = "";
        this.room = 0;
    }
    
    public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }
    
    // Method to create new doctor with user input
    public void newDoctor() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Doctor ID: ");
            this.id = scanner.nextLine();
            
            System.out.print("Enter Doctor Name: ");
            this.name = scanner.nextLine();
            
            System.out.print("Enter Specialization: ");
            this.specialist = scanner.nextLine();
            
            System.out.print("Enter Work Time (Ex. 9AM-5PM): ");
            this.workTime = scanner.nextLine();
            
            System.out.print("Enter Qualification: ");
            this.qualification = scanner.nextLine();
            
            System.out.print("Enter Room Number: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input! \nPlease enter a valid room number (integer): ");
                scanner.next();
                System.out.println();
            }
            this.room = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            System.out.println("\nDoctor added successfully!");
            
        } catch (Exception e) {
            System.out.println("\nAn error occurred while adding doctor: " + e.getMessage() + "\n");
        }
    }
    
    // Method to display doctor information
    public void showDoctorInfo() {
        System.out.println(id + " " + name + " " + specialist + " " + workTime + " " + qualification + " " + room);
    }
    
    // Getter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialist() { return specialist; }
    public String getWorkTime() { return workTime; }
    public String getQualification() { return qualification; }
    public int getRoom() { return room; }
    
    // Setter methods
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }
    public void setWorkTime(String workTime) { this.workTime = workTime; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setRoom(int room) { this.room = room; }
}