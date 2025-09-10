import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HospitalManagement {
    // ===== Storage =====
    private static Doctor[] doctors = new Doctor[25];
    private static Patient[] patients = new Patient[100];
    private static Medicine[] medicines = new Medicine[100];
    private static Lab[] labs = new Lab[20];
    private static Facility[] facilities = new Facility[20];
    private static Staff[] staffs = new Staff[100];

    // ===== Counters =====
    private static int doctorCount = 0;
    private static int patientCount = 0;
    private static int medicineCount = 0;
    private static int labCount = 0;
    private static int facilityCount = 0;
    private static int staffCount = 0;

    // One-and-only scanner
    public static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        displayWelcome();
        initializeSampleData();
        mainMenu();
    }

    // ===== Welcome =====
    public static void displayWelcome() {
        System.out.println("===============================================");
        System.out.println("    Welcome to Hospital Management System     ");
        System.out.println("===============================================");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Date and Time: " + now.format(f));
        System.out.println("===============================================");
    }

    // ===== Samples =====
    public static void initializeSampleData() {
        // Doctors (first two must be group members + last 3 digits)
        doctors[0] = new Doctor("132", "Chuah Qing Pei", "Cardiology", "9AM-5PM", "MD Cardiology", 101);
        doctors[1] = new Doctor("651", "Soo Che Leng", "Neurology", "10AM-6PM", "MD Neurology", 102);
        doctors[2] = new Doctor("003", "Dr. Sarah Wilson", "Pediatrics", "8AM-4PM", "MD Pediatrics", 103);
        doctorCount = 3;

        // Patients
        patients[0] = new Patient("P001", "John Smith", "Hypertension", "M", "Admitted", 45);
        patients[1] = new Patient("P002", "Mary Johnson", "Diabetes", "F", "Outpatient", 52);
        patients[2] = new Patient("P003", "Robert Brown", "Fever", "M", "Discharged", 28);
        patientCount = 3;

        // Medicines
        medicines[0] = new Medicine("Paracetamol", "PharmaCorp", "2026-12-31", 10, 100);
        medicines[1] = new Medicine("Aspirin", "MediPharm", "2025-08-15", 15, 50);
        medicines[2] = new Medicine("Insulin", "HealthMed", "2025-06-30", 120, 25);
        medicineCount = 3;

        // Labs
        labs[0] = new Lab("X-Ray", 150);
        labs[1] = new Lab("MRI", 1200);
        labs[2] = new Lab("Blood Test", 80);
        labCount = 3;

        // Facilities
        facilities[0] = new Facility("Ambulance");
        facilities[1] = new Facility("Cafeteria");
        facilities[2] = new Facility("Emergency Room");
        facilityCount = 3;

        // Staff (polymorphism)
        staffs[0] = new Nurse("S001", "Grace", "F", 3000, "Night");
        staffs[1] = new Nurse("S002", "Hannah", "F", 2800, "Day");
        staffs[2] = new Nurse("S003", "James", "M", 3100, "Night");

        staffs[3] = new Pharmacist("S004", "Henry", "M", 4000, "MY-PL-8892");
        staffs[4] = new Pharmacist("S005", "Rachel", "F", 4200, "MY-PL-7751");
        staffs[5] = new Pharmacist("S006", "Daniel", "M", 3900, "MY-PL-6630");

        staffs[6] = new SecurityStaff("S007", "Isabella", "F", 2500, "L2");
        staffs[7] = new SecurityStaff("S008", "Thomas", "M", 2600, "L1");
        staffs[8] = new SecurityStaff("S009", "Sophia", "F", 2700, "L2");

        staffCount = 9;
    }

    // ===== Input helpers (unified prompts & validation) =====
    public static String readShift() {
        while (true) {
            System.out.print("Enter Shift (Day/Night): ");
            String s = SC.nextLine().trim().toLowerCase();
            if (s.startsWith("d")) return "Day";
            if (s.startsWith("n")) return "Night";
            System.out.println("Invalid. Please enter Day or Night.");
        }
    }

    public static String readClearanceLevelStrict() {
        while (true) {
            System.out.print("Enter Clearance Level (Low/Medium/High or L1/L2/L3): ");
            String s = SC.nextLine().trim().toUpperCase();
            if ("L1".equals(s) || "LOW".equals(s))    return "Low";
            if ("L2".equals(s) || "MEDIUM".equals(s)) return "Medium";
            if ("L3".equals(s) || "HIGH".equals(s))   return "High";
            System.out.println("Invalid. Please enter Low/Medium/High or L1/L2/L3.");
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.println("Please enter a number in [" + min + ", " + max + "].");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public static int readIntNonNegative(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < 0) {
                    System.out.println("Must be >= 0.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Input cannot be empty.");
        }
    }

    public static String readSex() {
        while (true) {
            System.out.print("Enter Sex (M/F): ");
            String s = SC.nextLine().trim();
            if (s.equalsIgnoreCase("M") || s.equalsIgnoreCase("F")) return s.toUpperCase();
            System.out.println("Invalid. Please enter M or F.");
        }
    }

    public static String normalizeShift(String s) {
        String v = (s == null ? "" : s.trim().toLowerCase());
        if (v.startsWith("d")) return "Day";
        if (v.startsWith("n")) return "Night";
        return "Day";
    }

    /** Map L1/L2/L3 or Low/Medium/High -> Low/Medium/High */
    public static String normalizeClearanceLevel(String s) {
        if (s == null) return ""
        		+ "";
        String v = s.trim().toUpperCase();
        switch (v) {
            case "L1": case "LOW":    return "Low";
            case "L2": case "MEDIUM": return "Medium";
            case "L3": case "HIGH":   return "High";
            default:
                if (v.isEmpty()) return "-";
                return v.substring(0,1).toUpperCase() + v.substring(1).toLowerCase();
        }
    }

    // ===== Formatting helpers (used by class show methods) =====
    public static String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    public static void printSeparator(int... widths) {
        int totalWidth = 0;
        for (int w : widths) totalWidth += w;
        int spaces = widths.length - 1;
        int lineLength = totalWidth + spaces;
        for (int i = 0; i < lineLength; i++) System.out.print("-");
        System.out.println();
    }

    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        SC.nextLine();
    }

    // ===== Main Menu =====
    public static void mainMenu() {
        while (true) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM MAIN MENU ===");
            System.out.println("1. Doctors");
            System.out.println("2. Patients");
            System.out.println("3. Medicine");
            System.out.println("4. Laboratories");
            System.out.println("5. Facilities");
            System.out.println("6. Staff");
            System.out.println("7. Exit");

            int choice = readIntInRange("Please select an option (1-7): ", 1, 7);
            switch (choice) {
                case 1: doctorMenu(); break;
                case 2: patientMenu(); break;
                case 3: medicineMenu(); break;
                case 4: labMenu(); break;
                case 5: facilityMenu(); break;
                case 6: staffMenu(); break;
                case 7:
                    System.out.println("\nThank you for using Hospital Management System!");
                    return;
            }
        }
    }

    // ===== Doctors =====
    public static void doctorMenu() {
        while (true) {
            System.out.println("\n=== DOCTOR MANAGEMENT ===");
            System.out.println("1. Add New Doctor");
            System.out.println("2. Show All Doctors");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (doctorCount >= doctors.length) {
                        System.out.println("Doctor array is full!");
                        pressEnterToContinue();
                        break;
                    }
                    doctors[doctorCount] = new Doctor();
                    doctors[doctorCount].newDoctor();
                    System.out.println("Added.");
                    doctorCount++;
                    pressEnterToContinue();
                    break;

                case 2:
                    Doctor.showDoctorInfo(doctors, doctorCount); 
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }

    // ===== Patients =====
    public static void patientMenu() {
        while (true) {
            System.out.println("\n=== PATIENT MANAGEMENT ===");
            System.out.println("1. Add New Patient");
            System.out.println("2. Show All Patients");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (patientCount >= patients.length) {
                        System.out.println("Patient array is full!");
                        pressEnterToContinue();
                        break;
                    }
                    patients[patientCount] = new Patient();
                    patients[patientCount].newPatient();
                    System.out.println("Added.");
                    patientCount++;
                    pressEnterToContinue();
                    break;

                case 2:
                    Patient.showPatientInfo(patients, patientCount);
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }

    // ===== Medicines =====
    public static void medicineMenu() {
        while (true) {
            System.out.println("\n=== MEDICINE MANAGEMENT ===");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Show All Medicines");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (medicineCount >= medicines.length) {
                        System.out.println("Medicine array is full!");
                        pressEnterToContinue();
                        break;
                    }
                    medicines[medicineCount] = new Medicine();
                    medicines[medicineCount].newMedicine();
                    System.out.println("Added.");
                    medicineCount++;
                    pressEnterToContinue();
                    break;

                case 2:
                    Medicine.findMedicine(medicines, medicineCount); 
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }

    // ===== Labs =====
    public static void labMenu() {
        while (true) {
            System.out.println("\n=== LAB MANAGEMENT ===");
            System.out.println("1. Add New Lab");
            System.out.println("2. Show All Labs");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (labCount >= labs.length) {
                        System.out.println("Lab array is full!");
                        pressEnterToContinue();
                        break;
                    }
                    labs[labCount] = new Lab();
                    labs[labCount].newLab();
                    System.out.println("Added.");
                    labCount++;
                    pressEnterToContinue();
                    break;

                case 2:
                    Lab.labList(labs, labCount); 
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }

    // ===== Facilities =====
    public static void facilityMenu() {
        while (true) {
            System.out.println("\n=== FACILITY MANAGEMENT ===");
            System.out.println("1. Add New Facility");
            System.out.println("2. Show All Facilities");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (facilityCount >= facilities.length) {
                        System.out.println("Facility array is full!");
                        pressEnterToContinue();
                        break;
                    }
                    facilities[facilityCount] = new Facility();
                    facilities[facilityCount].newFacility();
                    System.out.println("Added.");
                    facilityCount++;
                    pressEnterToContinue();
                    break;

                case 2:
                    Facility.showFacility(facilities, facilityCount); 
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }

    // ===== Staff =====
    public static void staffMenu() {
        while (true) {
            System.out.println("\n=== STAFF MANAGEMENT ===");
            System.out.println("1. Add New Staff");
            System.out.println("2. Show All Staff");
            System.out.println("3. Return to Main Menu");

            int choice = readIntInRange("Select (1-3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (staffCount >= staffs.length) {
                        System.out.println("Staff array is full!");
                        pressEnterToContinue();
                        break;
                    }

                    System.out.println("\nAdd which type?");
                    System.out.println("1) Nurse   2) Pharmacist   3) Security   4) Generic Staff");
                    int t = readIntInRange("Select (1-4): ", 1, 4);

                    Staff s;
                    if (t == 1) s = new Nurse();
                    else if (t == 2) s = new Pharmacist();
                    else if (t == 3) s = new SecurityStaff();
                    else s = new Staff();

                    s.newStaff();
                    System.out.println("Added.");
                    staffs[staffCount++] = s;

                    pressEnterToContinue();
                    break;

                case 2:
                    Staff.showStaffInfo(staffs, staffCount); 
                    pressEnterToContinue();
                    break;

                case 3:
                    return;
            }
        }
    }
    
 // ---- Expose data to JavaFX ----
    public static Doctor[]   getDoctors()       { return doctors; }
    public static int        getDoctorCount()   { return doctorCount; }
    public static Patient[]  getPatients()      { return patients; }
    public static int        getPatientCount()  { return patientCount; }
    public static Medicine[] getMedicines()     { return medicines; }
    public static int        getMedicineCount() { return medicineCount; }
    public static Lab[]      getLabs()          { return labs; }
    public static int        getLabCount()      { return labCount; }
    public static Facility[] getFacilities()    { return facilities; }
    public static int        getFacilityCount() { return facilityCount; }
    public static Staff[]    getStaffs()        { return staffs; }
    public static int        getStaffCount()    { return staffCount; }

    // ---- Append helpers used by JavaFX ----
    public static boolean addDoctor(Doctor d)         { if (doctorCount   >= doctors.length)   return false; doctors[doctorCount++]     = d; return true; }
    public static boolean addPatient(Patient p)       { if (patientCount  >= patients.length)  return false; patients[patientCount++]   = p; return true; }
    public static boolean addMedicine(Medicine m)     { if (medicineCount >= medicines.length) return false; medicines[medicineCount++] = m; return true; }
    public static boolean addLab(Lab l)               { if (labCount      >= labs.length)      return false; labs[labCount++]           = l; return true; }
    public static boolean addFacility(Facility f)     { if (facilityCount >= facilities.length) return false; facilities[facilityCount++] = f; return true; }
    public static boolean addStaff(Staff s)           { if (staffCount    >= staffs.length)    return false; staffs[staffCount++]       = s; return true; }

}
