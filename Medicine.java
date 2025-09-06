import java.util.Scanner;

public class Medicine {
    private String name;
    private String manufacturer;
    private String expiryDate;
    private int cost;
    private int count;
    
    public Medicine() {
        this.name = "";
        this.manufacturer = "";
        this.expiryDate = "";
        this.cost = 0;
        this.count = 0;
    }
    
    public Medicine(String name, String manufacturer, String expiryDate, int cost, int count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.cost = cost;
        this.count = count;
    }
    
    // Method to create new medicine with user input
    public void newMedicine() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Medicine Name: ");
            this.name = scanner.nextLine();
            
            System.out.print("Enter Manufacturer: ");
            this.manufacturer = scanner.nextLine();
            
            System.out.print("Enter Expiry Date (Ex. 2026-12-31): ");
            this.expiryDate = scanner.nextLine();
            
            System.out.print("Enter Cost: RM");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input! \nPlease enter a valid cost (integer): RM");
                scanner.next();
                System.out.println();
            }
            this.cost = scanner.nextInt();
            
            System.out.print("Enter Number of Units: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input! \nPlease enter a valid number of units (integer): ");
                scanner.next();
                System.out.println();
            }
            this.count = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            System.out.println("\nMedicine added successfully!");
            
        } catch (Exception e) {
            System.out.println("\nAn error occurred while adding medicine: " + e.getMessage() + "\n");
        }
    }
    
    // Method to display medicine information
    public void findMedicine() {
        System.out.println(name + " " + manufacturer + " " + expiryDate + " " + cost);
    }
    
    // Getter methods
    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public String getExpiryDate() { return expiryDate; }
    public int getCost() { return cost; }
    public int getCount() { return count; }
    
    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setCost(int cost) { this.cost = cost; }
    public void setCount(int count) { this.count = count; }
}