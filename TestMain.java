import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestMain {
    // Arrays for testing classes
    private static Doctor[] doctors = new Doctor[25];
    private static Patient[] patients = new Patient[100];
    private static Medicine[] medicines = new Medicine[100];
    
    // Counters to track number of entries
    private static int doctorCount = 0;
    private static int patientCount = 0;
    private static int medicineCount = 0;
    
    public static void main(String[] args) {
        // Display welcome message with current date and time
        displayWelcome();
        
        // Initialize sample data
        initializeSampleData();
        
        // Main menu
        mainMenu();
    }
    
    public static void displayWelcome() {
        System.out.println("===============================================");
        System.out.println("    Welcome to Hospital Management System     ");
        System.out.println("===============================================");
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date and Time: " + now.format(formatter));
        System.out.println("===============================================");
    }
    
    public static void initializeSampleData() {
        // Initialize doctors sample
        doctors[0] = new Doctor("132", "Chuah Qing Pei", "Cardiology", "9AM-5PM", "MD Cardiology", 101);
        doctors[1] = new Doctor("651", "Soo Che Leng", "Neurology", "10AM-6PM", "MD Neurology", 102);
        doctors[2] = new Doctor("003", "Dr. Sarah Wilson", "Pediatrics", "8AM-4PM", "MD Pediatrics", 103);
        doctorCount = 3;
        
        // Initialize patients sample
        patients[0] = new Patient("P001", "John Smith", "Hypertension", "Male", "Admitted", 45);
        patients[1] = new Patient("P002", "Mary Johnson", "Diabetes", "Female", "Outpatient", 52);
        patients[2] = new Patient("P003", "Robert Brown", "Fever", "Male", "Discharged", 28);
        patientCount = 3;
        
        // Initialize medicines sample
        medicines[0] = new Medicine("Paracetamol", "PharmaCorp", "2026-12-31", 10, 100);
        medicines[1] = new Medicine("Aspirin", "MediPharm", "2025-08-15", 15, 50);
        medicines[2] = new Medicine("Insulin", "HealthMed", "2025-06-30", 120, 25);
        medicineCount = 3;
    }
    
    // Method to pause and wait for user input
    public static void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    // Method for table columns
    public static String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) {
            return str;
        } else {
            return str.substring(0, maxLength - 3) + "...";
        }
    }
    
    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM MAIN MENU ===");
            System.out.println("1. Doctors");
            System.out.println("2. Patients");
            System.out.println("3. Medicine");
            System.out.println("4. Exit");
            System.out.print("Please select an option (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        doctorMenu();
                        break;
                    case 2:
                        patientMenu();
                        break;
                    case 3:
                        medicineMenu();
                        break;
                    case 4:
                        System.out.println("\nThank you for using Hospital Management System!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter 1-4.\n");
                        pressEnterToContinue();
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.\n");
                scanner.nextLine(); // consume invalid input
                pressEnterToContinue();
            }
        }
    }
    
    public static void doctorMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== DOCTOR MANAGEMENT ===");
            System.out.println("1. Add New Doctor");
            System.out.println("2. Display All Doctors");
            System.out.println("3. Return to Main Menu");
            System.out.print("Please select an option (1-3): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        if (doctorCount < doctors.length) {
                            System.out.println("\n=== ADD NEW DOCTOR ===");
                            doctors[doctorCount] = new Doctor();
                            doctors[doctorCount].newDoctor();
                            doctorCount++;
                            pressEnterToContinue();
                        } else {
                            System.out.println("\nDoctor array is full!");
                            pressEnterToContinue();
                        }
                        break;
                    case 2:
                        displayAllDoctors();
                        pressEnterToContinue();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid choice! Please enter 1-3.");
                        pressEnterToContinue();
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please enter a number.");
                scanner.nextLine(); // consume invalid input
                pressEnterToContinue();
            }
        }
    }
    
    public static void patientMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== PATIENT MANAGEMENT ===");
            System.out.println("1. Add New Patient");
            System.out.println("2. Display All Patients");
            System.out.println("3. Return to Main Menu");
            System.out.print("Please select an option (1-3): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        if (patientCount < patients.length) {
                            System.out.println("\n=== ADD NEW PATIENT ===");
                            patients[patientCount] = new Patient();
                            patients[patientCount].newPatient();
                            patientCount++;
                            pressEnterToContinue();
                        } else {
                            System.out.println("\nPatient array is full!");
                            pressEnterToContinue();
                        }
                        break;
                    case 2:
                        displayAllPatients();
                        pressEnterToContinue();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid choice! Please enter 1-3.");
                        pressEnterToContinue();
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please enter a number.");
                scanner.nextLine(); // consume invalid input
                pressEnterToContinue();
            }
        }
    }
    
    public static void medicineMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== MEDICINE MANAGEMENT ===");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Display All Medicines");
            System.out.println("3. Return to Main Menu");
            System.out.print("Please select an option (1-3): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        if (medicineCount < medicines.length) {
                            System.out.println("\n=== ADD NEW MEDICINE ===");
                            medicines[medicineCount] = new Medicine();
                            medicines[medicineCount].newMedicine();
                            medicineCount++;
                            pressEnterToContinue();
                        } else {
                            System.out.println("\nMedicine array is full!");
                            pressEnterToContinue();
                        }
                        break;
                    case 2:
                        displayAllMedicines();
                        pressEnterToContinue();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("\nInvalid choice! Please enter 1-3.");
                        pressEnterToContinue();
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input! Please enter a number.");
                scanner.nextLine(); // consume invalid input
                pressEnterToContinue();
            }
        }
    }
    
    public static void displayAllDoctors() {
        System.out.println("\n=== ALL DOCTORS ===");
        if (doctorCount == 0) {
            System.out.println("\nNo doctors found!");
        } else {
            System.out.printf("%-6s %-18s %-15s %-12s %-18s %-8s\n", 
                "ID", "Name", "Specialist", "Work Time", "Qualification", "Room No.");
            System.out.println("-------------------------------------------------------------------------------");
            for (int i = 0; i < doctorCount; i++) {
                System.out.printf("%-6s %-18s %-15s %-12s %-18s %-8d\n",
                    doctors[i].getId(),
                    truncateString(doctors[i].getName(), 18),
                    truncateString(doctors[i].getSpecialist(), 15),
                    truncateString(doctors[i].getWorkTime(), 12),
                    truncateString(doctors[i].getQualification(), 18),
                    doctors[i].getRoom());
            }
        }
        System.out.println();
    }
    
    public static void displayAllPatients() {
        System.out.println("\n=== ALL PATIENTS ===");
        if (patientCount == 0) {
            System.out.println("\nNo patients found!");
        } else {
            System.out.printf("%-6s %-18s %-15s %-8s %-15s %-5s\n", 
                "ID", "Name", "Disease", "Sex", "Admit Status", "Age");
            System.out.println("-----------------------------------------------------------------------");
            for (int i = 0; i < patientCount; i++) {
                System.out.printf("%-6s %-18s %-15s %-8s %-15s %-5d\n",
                    patients[i].getId(),
                    truncateString(patients[i].getName(), 18),
                    truncateString(patients[i].getDisease(), 15),
                    truncateString(patients[i].getSex(), 8),
                    truncateString(patients[i].getAdmitStatus(), 15),
                    patients[i].getAge());
            }
        }
        System.out.println();
    }
    
    public static void displayAllMedicines() {
        System.out.println("\n=== ALL MEDICINES ===");
        if (medicineCount == 0) {
            System.out.println("\nNo medicines found!");
        } else {
            System.out.printf("%-18s %-18s %-15s %-8s\n", 
                "Name", "Manufacturer", "Expiry Date", "Cost");
            System.out.println("-----------------------------------------------------------");
            for (int i = 0; i < medicineCount; i++) {
                System.out.printf("%-18s %-18s %-15s %-8d\n",
                    truncateString(medicines[i].getName(), 18),
                    truncateString(medicines[i].getManufacturer(), 18),
                    truncateString(medicines[i].getExpiryDate(), 15),
                    medicines[i].getCost());
            }
        }
        System.out.println();
    }
}