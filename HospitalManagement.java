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
    
    public static String formatText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        
        String trimmed = text.trim();
        StringBuilder formatted = new StringBuilder();
        boolean capitalizeNext = true;
        
        for (int i = 0; i < trimmed.length(); i++) {
            char ch = trimmed.charAt(i);
            if (capitalizeNext && Character.isLetter(ch)) {
                formatted.append(Character.toUpperCase(ch));
                capitalizeNext = false;
            } else if (ch == ' ') {
                formatted.append(ch);
                capitalizeNext = true;
            } else {
                formatted.append(Character.toLowerCase(ch));
            }
        }
        
        return formatted.toString();
    }
    
    public static String validateAndFormatID(String prompt, String[] existingIDs, int count) {
        while (true) {
            try {
                String id = readNonEmpty(prompt).toUpperCase(); // Convert to uppercase
                // Check for duplicate IDs (case-insensitive)
                for (int i = 0; i < count; i++) {
                    if (existingIDs[i] != null && existingIDs[i].equalsIgnoreCase(id)) {
                        System.out.println("ID already exists! Please enter a unique ID.");
                        id = null;
                        break;
                    }
                }
                if (id != null) return id;
            } catch (Exception e) {
                System.out.println("Error reading ID: " + e.getMessage());
            }
        }
    }
    
    public static String validateAndReadString(String prompt, String fieldName) {
        while (true) {
            try {
                String input = readNonEmpty(prompt);
                if (input.matches(".*[0-9].*")) {
                    System.out.println(fieldName + " should not contain numbers.");
                    continue;
                }
                // Format text with proper capitalization
                return formatText(input);
            } catch (Exception e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    public static String validateDate(String prompt) {
        while (true) {
            try {
                String date = readNonEmpty(prompt);
                // Basic date format validation (YYYY-MM-DD)
                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
                    continue;
                }
                String[] parts = date.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                
                if (year < 2000 || year > 2030) {
                    System.out.println("Year should be between 2000 and 2030.");
                    continue;
                }
                if (month < 1 || month > 12) {
                    System.out.println("Month should be between 1 and 12.");
                    continue;
                }
                if (day < 1 || day > 31) {
                    System.out.println("Day should be between 1 and 31.");
                    continue;
                }
                return date;
            } catch (NumberFormatException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            } catch (Exception e) {
                System.out.println("Error reading date: " + e.getMessage());
            }
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
            try {
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
                    case 1: 
                        try {
                            doctorMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing doctor management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 2: 
                        try {
                            patientMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing patient management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 3: 
                        try {
                            medicineMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing medicine management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 4: 
                        try {
                            labMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing lab management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 5: 
                        try {
                            facilityMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing facility management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 6: 
                        try {
                            staffMenu(); 
                        } catch (Exception e) {
                            System.out.println("Error accessing staff management: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;
                    case 7:
                        try {
                            System.out.println("\nThank you for using Hospital Management System!");
                            return;
                        } catch (Exception e) {
                            System.out.println("Error during exit: " + e.getMessage());
                            return;
                        }
                }
            } catch (Exception e) {
                System.out.println("Error in main menu: " + e.getMessage());
                System.out.println("Returning to main menu...");
                pressEnterToContinue();
            }
        }
    }

    // ===== Doctors =====
    public static void doctorMenu() {
        while (true) {
            try {
                System.out.println("\n=== DOCTOR MANAGEMENT ===");
                System.out.println("1. Add New Doctor");
                System.out.println("2. Show All Doctors");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
                            if (doctorCount >= doctors.length) {
                                System.out.println("Doctor array is full!");
                                pressEnterToContinue();
                                break;
                            }
                            doctors[doctorCount] = new Doctor();
                            doctors[doctorCount].newDoctor();
                            System.out.println("Doctor added successfully!");
                            doctorCount++;
                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Doctor array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding doctor: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Doctor.showDoctorInfo(doctors, doctorCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying doctors: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in doctor menu: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ===== Patients =====
    public static void patientMenu() {
        while (true) {
            try {
                System.out.println("\n=== PATIENT MANAGEMENT ===");
                System.out.println("1. Add New Patient");
                System.out.println("2. Show All Patients");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
                            if (patientCount >= patients.length) {
                                System.out.println("Patient array is full!");
                                pressEnterToContinue();
                                break;
                            }
                            patients[patientCount] = new Patient();
                            patients[patientCount].newPatient();
                            System.out.println("Patient added successfully!");
                            patientCount++;
                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Patient array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding patient: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Patient.showPatientInfo(patients, patientCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying patients: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in patient menu: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ===== Medicines =====
    public static void medicineMenu() {
        while (true) {
            try {
                System.out.println("\n=== MEDICINE MANAGEMENT ===");
                System.out.println("1. Add New Medicine");
                System.out.println("2. Show All Medicines");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
                            if (medicineCount >= medicines.length) {
                                System.out.println("Medicine array is full!");
                                pressEnterToContinue();
                                break;
                            }
                            medicines[medicineCount] = new Medicine();
                            medicines[medicineCount].newMedicine();
                            System.out.println("Medicine added successfully!");
                            medicineCount++;
                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Medicine array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding medicine: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Medicine.findMedicine(medicines, medicineCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying medicines: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in medicine menu: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ===== Labs =====
    public static void labMenu() {
        while (true) {
            try {
                System.out.println("\n=== LAB MANAGEMENT ===");
                System.out.println("1. Add New Lab");
                System.out.println("2. Show All Labs");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
                            if (labCount >= labs.length) {
                                System.out.println("Lab array is full!");
                                pressEnterToContinue();
                                break;
                            }
                            labs[labCount] = new Lab();
                            labs[labCount].newLab();
                            System.out.println("Lab added successfully!");
                            labCount++;
                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Lab array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding lab: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Lab.labList(labs, labCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying labs: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in lab menu: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ===== Facilities =====
    public static void facilityMenu() {
        while (true) {
            try {
                System.out.println("\n=== FACILITY MANAGEMENT ===");
                System.out.println("1. Add New Facility");
                System.out.println("2. Show All Facilities");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
                            if (facilityCount >= facilities.length) {
                                System.out.println("Facility array is full!");
                                pressEnterToContinue();
                                break;
                            }
                            facilities[facilityCount] = new Facility();
                            facilities[facilityCount].newFacility();
                            System.out.println("Facility added successfully!");
                            facilityCount++;
                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Facility array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding facility: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Facility.showFacility(facilities, facilityCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying facilities: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in facility menu: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ===== Staff =====
    public static void staffMenu() {
        while (true) {
            try {
                System.out.println("\n=== STAFF MANAGEMENT ===");
                System.out.println("1. Add New Staff");
                System.out.println("2. Show All Staff");
                System.out.println("3. Return to Main Menu");

                int choice = readIntInRange("Select (1-3): ", 1, 3);
                switch (choice) {
                    case 1:
                        try {
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
                            System.out.println("Staff added successfully!");
                            staffs[staffCount++] = s;

                            pressEnterToContinue();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error: Staff array limit exceeded.");
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error adding staff: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 2:
                        try {
                            Staff.showStaffInfo(staffs, staffCount);
                            pressEnterToContinue();
                        } catch (Exception e) {
                            System.out.println("Error displaying staff: " + e.getMessage());
                            pressEnterToContinue();
                        }
                        break;

                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error in staff menu: " + e.getMessage());
                pressEnterToContinue();
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
