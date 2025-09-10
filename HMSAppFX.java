import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

// Row classes for TableView
class DoctorRow {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty specialist;
    private final SimpleStringProperty workTime;
    private final SimpleStringProperty qualification;
    private final SimpleStringProperty roomFormatted;

    public DoctorRow(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.specialist = new SimpleStringProperty(specialist);
        this.workTime = new SimpleStringProperty(workTime);
        this.qualification = new SimpleStringProperty(qualification);
        this.roomFormatted = new SimpleStringProperty(String.format("%03d", room));
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getSpecialist() { return specialist.get(); }
    public String getWorkTime() { return workTime.get(); }
    public String getQualification() { return qualification.get(); }
    public String getRoomFormatted() { return roomFormatted.get(); }
}

class PatientRow {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty disease;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty admitStatus;
    private final SimpleIntegerProperty age;

    public PatientRow(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.disease = new SimpleStringProperty(disease);
        this.sex = new SimpleStringProperty(sex);
        this.admitStatus = new SimpleStringProperty(admitStatus);
        this.age = new SimpleIntegerProperty(age);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDisease() { return disease.get(); }
    public String getSex() { return sex.get(); }
    public String getAdmitStatus() { return admitStatus.get(); }
    public int getAge() { return age.get(); }
}

class MedicineRow {
    private final SimpleStringProperty name;
    private final SimpleStringProperty manufacturer;
    private final SimpleStringProperty expiryDate;
    private final SimpleIntegerProperty cost;

    public MedicineRow(String name, String manufacturer, String expiryDate, int cost) {
        this.name = new SimpleStringProperty(name);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.expiryDate = new SimpleStringProperty(expiryDate);
        this.cost = new SimpleIntegerProperty(cost);
    }

    public String getName() { return name.get(); }
    public String getManufacturer() { return manufacturer.get(); }
    public String getExpiryDate() { return expiryDate.get(); }
    public int getCost() { return cost.get(); }
}

class LabRow {
    private final SimpleStringProperty lab;
    private final SimpleIntegerProperty cost;

    public LabRow(String lab, int cost) {
        this.lab = new SimpleStringProperty(lab);
        this.cost = new SimpleIntegerProperty(cost);
    }

    public String getLab() { return lab.get(); }
    public int getCost() { return cost.get(); }
}

class FacilityRow {
    private final SimpleStringProperty facility;

    public FacilityRow(String facility) {
        this.facility = new SimpleStringProperty(facility);
    }

    public String getFacility() { return facility.get(); }
}

class StaffRow {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty designation;
    private final SimpleStringProperty shift;
    private final SimpleStringProperty licenseNumber;
    private final SimpleStringProperty clearanceLevel;
    private final SimpleStringProperty sex;
    private final SimpleIntegerProperty salary;

    public StaffRow(String id, String name, String designation, String shift, String licenseNumber, String clearanceLevel, String sex, int salary) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.designation = new SimpleStringProperty(designation);
        this.shift = new SimpleStringProperty(shift != null ? shift : "-");
        this.licenseNumber = new SimpleStringProperty(licenseNumber != null ? licenseNumber : "-");
        this.clearanceLevel = new SimpleStringProperty(clearanceLevel != null ? clearanceLevel : "-");
        this.sex = new SimpleStringProperty(sex);
        this.salary = new SimpleIntegerProperty(salary);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDesignation() { return designation.get(); }
    public String getShift() { return shift.get(); }
    public String getLicenseNumber() { return licenseNumber.get(); }
    public String getClearanceLevel() { return clearanceLevel.get(); }
    public String getSex() { return sex.get(); }
    public int getSalary() { return salary.get(); }
}

// Custom exception for validation
class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}

public class HMSAppFX extends Application {

    // ===== Table view models  =====
    private final ObservableList<DoctorRow>    doctors    = FXCollections.observableArrayList();
    private final ObservableList<PatientRow>   patients   = FXCollections.observableArrayList();
    private final ObservableList<MedicineRow>  medicines  = FXCollections.observableArrayList();
    private final ObservableList<LabRow>       labs       = FXCollections.observableArrayList();
    private final ObservableList<FacilityRow>  facilities = FXCollections.observableArrayList();
    private final ObservableList<StaffRow>     staffs     = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        HospitalManagement.initializeSampleData();
        reloadAllFromHM();

        // Create main container with background
        VBox mainContainer = new VBox();
        mainContainer.setStyle(
            "-fx-background: linear-gradient(to bottom, #e3f2fd 0%, #bbdefb 50%, #90caf9 100%);" +
            "-fx-padding: 20;"
        );

        // Create title header
        Label titleLabel = new Label("Hospital Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#1565c0"));
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");
        
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 20, 0));

        TabPane tabs = new TabPane(
                createStyledTab("👨‍⚕️ Doctors",   doctorsPane()),
                createStyledTab("🏥 Patients",  patientsPane()),
                createStyledTab("💊 Medicine",  medicinesPane()),
                createStyledTab("🔬 Labs",      labsPane()),
                createStyledTab("🏢 Facilities",facilitiesPane()),
                createStyledTab("👥 Staff",     staffPane())
        );
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.setStyle(
            "-fx-background-color: rgba(255,255,255,0.9);" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);"
        );

        mainContainer.getChildren().addAll(titleBox, tabs);
        VBox.setVgrow(tabs, Priority.ALWAYS);

        Scene scene = new Scene(mainContainer, 1200, 700);
        stage.setTitle("Hospital Management System - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    private Tab createStyledTab(String title, Region content) {
        Tab tab = new Tab(title);
        
        // Create content wrapper with styling
        VBox wrapper = new VBox(content);
        wrapper.setStyle(
            "-fx-background-color: rgba(255,255,255,0.95);" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 15;"
        );
        
        ScrollPane scrollPane = new ScrollPane(wrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        tab.setContent(scrollPane);
        return tab;
    }

    // ===== Helper methods for table columns =====
    private <T> TableColumn<T, String> col(String name, java.util.function.Function<T, String> getter) {
        TableColumn<T, String> col = new TableColumn<>(name);
        col.setCellValueFactory(cellData -> new SimpleStringProperty(getter.apply(cellData.getValue())));
        return col;
    }

    private <T> TableColumn<T, Integer> colInt(String name, java.util.function.Function<T, Integer> getter) {
        TableColumn<T, Integer> col = new TableColumn<>(name);
        col.setCellValueFactory(cellData -> new SimpleIntegerProperty(getter.apply(cellData.getValue())).asObject());
        return col;
    }

    // ===== Data reload methods =====
    private void reloadAllFromHM() {
        reloadDoctors();
        reloadPatients();
        reloadMedicines();
        reloadLabs();
        reloadFacilities();
        reloadStaffs();
    }

    private void reloadDoctors() {
        doctors.clear();
        Doctor[] docs = HospitalManagement.getDoctors();
        int count = HospitalManagement.getDoctorCount();
        for (int i = 0; i < count; i++) {
            Doctor d = docs[i];
            doctors.add(new DoctorRow(d.getId(), d.getName(), d.getSpecialist(), 
                        d.getWorkTime(), d.getQualification(), d.getRoom()));
        }
    }

    private void reloadPatients() {
        patients.clear();
        Patient[] pts = HospitalManagement.getPatients();
        int count = HospitalManagement.getPatientCount();
        for (int i = 0; i < count; i++) {
            Patient p = pts[i];
            patients.add(new PatientRow(p.getId(), p.getName(), p.getDisease(), 
                        p.getSex(), p.getAdmitStatus(), p.getAge()));
        }
    }

    private void reloadMedicines() {
        medicines.clear();
        Medicine[] meds = HospitalManagement.getMedicines();
        int count = HospitalManagement.getMedicineCount();
        for (int i = 0; i < count; i++) {
            Medicine m = meds[i];
            medicines.add(new MedicineRow(m.getName(), m.getManufacturer(), 
                         m.getExpiryDate(), m.getCost()));
        }
    }

    private void reloadLabs() {
        labs.clear();
        Lab[] labArr = HospitalManagement.getLabs();
        int count = HospitalManagement.getLabCount();
        for (int i = 0; i < count; i++) {
            Lab l = labArr[i];
            labs.add(new LabRow(l.getLab(), l.getCost()));
        }
    }

    private void reloadFacilities() {
        facilities.clear();
        Facility[] facArr = HospitalManagement.getFacilities();
        int count = HospitalManagement.getFacilityCount();
        for (int i = 0; i < count; i++) {
            Facility f = facArr[i];
            facilities.add(new FacilityRow(f.getFacility()));
        }
    }

    private void reloadStaffs() {
        staffs.clear();
        Staff[] staffArr = HospitalManagement.getStaffs();
        int count = HospitalManagement.getStaffCount();
        for (int i = 0; i < count; i++) {
            Staff s = staffArr[i];
            String shift = null;
            String license = null;
            String clearance = null;
            
            if (s instanceof Nurse) {
                shift = ((Nurse) s).getShift();
            } else if (s instanceof Pharmacist) {
                license = ((Pharmacist) s).getLicenseNumber();
            } else if (s instanceof SecurityStaff) {
                clearance = ((SecurityStaff) s).getClearanceLevel();
            }
            
            String designation = s.getDesignation();
            if (designation == null || designation.trim().isEmpty()) {
                if (s instanceof Nurse) designation = "Nurse";
                else if (s instanceof Pharmacist) designation = "Pharmacist";
                else if (s instanceof SecurityStaff) designation = "Security";
                else designation = "Staff";
            }
            
            staffs.add(new StaffRow(s.getId(), s.getName(), designation, 
                      shift, license, clearance, s.getSex(), s.getSalary()));
        }
    }

    // ====================== Doctors ======================
    private BorderPane doctorsPane() {
        TableView<DoctorRow> tv = new TableView<>(doctors);
        styleTable(tv);
        tv.getColumns().addAll(
                col("ID", DoctorRow::getId),
                col("Name", DoctorRow::getName),
                col("Specialist", DoctorRow::getSpecialist),
                col("Work Time", DoctorRow::getWorkTime),
                col("Qualification", DoctorRow::getQualification),
                col("Room No.", DoctorRow::getRoomFormatted)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId    = createStyledTextField("ID", 90);
        TextField tfName  = createStyledTextField("Name", 140);
        TextField tfSpec  = createStyledTextField("Specialist", 140);
        TextField tfWork  = createStyledTextField("Work Time", 120);
        TextField tfQual  = createStyledTextField("Qualification", 160);
        TextField tfRoom  = createStyledTextField("Room No. (1-999)", 120);
        Button add = createStyledButton("Add Doctor", "#4caf50");
        
        // Auto-uppercase for ID
        tfId.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(newVal.toUpperCase())) {
                tfId.setText(newVal.toUpperCase());
            }
        });
        
        add.setOnAction(e -> {
            clearInvalidStates(tfId, tfName, tfSpec, tfWork, tfQual, tfRoom);
            try {
                String id   = validateNonEmpty(tfId.getText(), "ID", tfId);
                validateDuplicateId(id, "Doctor", tfId);
                String name = validateAndFormatName(tfName.getText(), "Name", tfName);
                String sp   = validateAndFormatName(tfSpec.getText(), "Specialist", tfSpec);
                String wt   = validateNonEmpty(tfWork.getText(), "Work Time", tfWork);
                String q    = validateAndFormatName(tfQual.getText(), "Qualification", tfQual);
                int room    = validateRoomNumber(tfRoom.getText(), tfRoom);
                
                Doctor d = new Doctor(id, name, sp, wt, q, room);
                if (!HospitalManagement.addDoctor(d)) {
                    showWarning("Doctor array is full in HospitalManagement.");
                    return;
                }
                reloadDoctors(); 
                tv.refresh();
                clearFields(tfId, tfName, tfSpec, tfWork, tfQual, tfRoom);
                showSuccess("Doctor added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfId, tfName, tfSpec, tfWork, tfQual, tfRoom, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ====================== Patients ======================
    private BorderPane patientsPane() {
        TableView<PatientRow> tv = new TableView<>(patients);
        styleTable(tv);
        tv.getColumns().addAll(
                col("ID", PatientRow::getId),
                col("Name", PatientRow::getName),
                col("Disease", PatientRow::getDisease),
                col("Sex", PatientRow::getSex),
                col("Admit Status", PatientRow::getAdmitStatus),
                colInt("Age", PatientRow::getAge)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId   = createStyledTextField("ID", 90);
        TextField tfName = createStyledTextField("Name", 140);
        TextField tfDis  = createStyledTextField("Disease", 140);
        ComboBox<String> cbSex = createStyledComboBox("Sex (M/F)", "M", "F");
        TextField tfAdmit= createStyledTextField("Admit Status", 140);
        TextField tfAge  = createStyledTextField("Age (0-150)", 90);
        Button add = createStyledButton("Add Patient", "#2196f3");
        
        // Auto-uppercase for ID
        tfId.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(newVal.toUpperCase())) {
                tfId.setText(newVal.toUpperCase());
            }
        });
        
        add.setOnAction(e -> {
            clearInvalidStates(tfId, tfName, tfDis, tfAdmit, tfAge);
            clearInvalidComboBox(cbSex);
            try {
                String id   = validateNonEmpty(tfId.getText(), "ID", tfId);
                validateDuplicateId(id, "Patient", tfId);
                String name = validateAndFormatName(tfName.getText(), "Name", tfName);
                String dis  = validateAndFormatName(tfDis.getText(), "Disease", tfDis);
                String sex  = validateSex(cbSex.getValue(), cbSex);
                String adm  = validateAndFormatName(tfAdmit.getText(), "Admit Status", tfAdmit);
                int age     = validateAge(tfAge.getText(), tfAge);
                
                Patient p = new Patient(id, name, dis, sex, adm, age);
                if (!HospitalManagement.addPatient(p)) {
                    showWarning("Patient array is full in HospitalManagement.");
                    return;
                }
                reloadPatients(); 
                tv.refresh();
                clearFields(tfId, tfName, tfDis, tfAdmit, tfAge); 
                cbSex.getSelectionModel().clearSelection();
                showSuccess("Patient added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfId, tfName, tfDis, cbSex, tfAdmit, tfAge, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ====================== Medicine ======================
    private BorderPane medicinesPane() {
        TableView<MedicineRow> tv = new TableView<>(medicines);
        styleTable(tv);
        tv.getColumns().addAll(
                col("Name", MedicineRow::getName),
                col("Manufacturer", MedicineRow::getManufacturer),
                col("Expiry Date", MedicineRow::getExpiryDate),
                colInt("Cost", MedicineRow::getCost)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfName = createStyledTextField("Name", 160);
        TextField tfManu = createStyledTextField("Manufacturer", 160);
        TextField tfExp  = createStyledTextField("Expiry Date (YYYY-MM-DD)", 200);
        TextField tfCost = createStyledTextField("Cost (1-10000)", 100);
        Button add = createStyledButton("Add Medicine", "#ff9800");
        
        add.setOnAction(e -> {
            clearInvalidStates(tfName, tfManu, tfExp, tfCost);
            try {
                String name = validateAndFormatName(tfName.getText(), "Name", tfName);
                String manu = validateAndFormatName(tfManu.getText(), "Manufacturer", tfManu);
                String exp  = validateDate(tfExp.getText(), tfExp);
                int cost    = validateCost(tfCost.getText(), 1, 10000, tfCost);
                
                Medicine m = new Medicine(name, manu, exp, cost, 0);
                if (!HospitalManagement.addMedicine(m)) {
                    showWarning("Medicine array is full in HospitalManagement.");
                    return;
                }
                reloadMedicines(); 
                tv.refresh();
                clearFields(tfName, tfManu, tfExp, tfCost);
                showSuccess("Medicine added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfName, tfManu, tfExp, tfCost, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ====================== Labs ======================
    private BorderPane labsPane() {
        TableView<LabRow> tv = new TableView<>(labs);
        styleTable(tv);
        tv.getColumns().addAll(
                col("Lab", LabRow::getLab), 
                colInt("Cost", LabRow::getCost)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfLab  = createStyledTextField("Lab", 200);
        TextField tfCost = createStyledTextField("Cost (1-5000)", 120);
        Button add = createStyledButton("Add Lab", "#9c27b0");
        
        add.setOnAction(e -> {
            clearInvalidStates(tfLab, tfCost);
            try {
                String lab = validateAndFormatName(tfLab.getText(), "Lab", tfLab);
                int cost   = validateCost(tfCost.getText(), 1, 5000, tfCost);
                
                Lab l = new Lab(lab, cost);
                if (!HospitalManagement.addLab(l)) {
                    showWarning("Lab array is full in HospitalManagement.");
                    return;
                }
                reloadLabs(); 
                tv.refresh();
                clearFields(tfLab, tfCost);
                showSuccess("Lab added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfLab, tfCost, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ====================== Facilities ======================
    private BorderPane facilitiesPane() {
        TableView<FacilityRow> tv = new TableView<>(facilities);
        styleTable(tv);
        tv.getColumns().addAll(col("Facility", FacilityRow::getFacility));
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfFac = createStyledTextField("Facility", 260);
        Button add = createStyledButton("Add Facility", "#795548");
        
        add.setOnAction(e -> {
            clearInvalidStates(tfFac);
            try {
                String f = validateAndFormatName(tfFac.getText(), "Facility", tfFac);
                
                Facility fac = new Facility(f);
                if (!HospitalManagement.addFacility(fac)) {
                    showWarning("Facility array is full in HospitalManagement.");
                    return;
                }
                reloadFacilities(); 
                tv.refresh();
                clearFields(tfFac);
                showSuccess("Facility added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfFac, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ====================== Staff ======================
    private BorderPane staffPane() {
        TableView<StaffRow> tv = new TableView<>(staffs);
        styleTable(tv);
        tv.getColumns().addAll(
                col("ID", StaffRow::getId),
                col("Name", StaffRow::getName),
                col("Designation", StaffRow::getDesignation),
                col("Shift", StaffRow::getShift),
                col("License Number", StaffRow::getLicenseNumber),
                col("Clearance Level", StaffRow::getClearanceLevel),
                col("Sex", StaffRow::getSex),
                colInt("Salary", StaffRow::getSalary)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId   = createStyledTextField("ID", 90);
        TextField tfName = createStyledTextField("Name", 140);
        ComboBox<String> cbDes = createStyledComboBox("Designation", "Nurse", "Pharmacist", "Security", "Generic Staff");
        TextField tfCustomDes = createStyledTextField("Custom Designation", 150);
        tfCustomDes.setDisable(true); // Initially disabled
        ComboBox<String> cbSex = createStyledComboBox("Sex (M/F)", "M", "F");
        TextField tfSalary = createStyledTextField("Salary (1000-50000)", 110);

        TextField tfShift = createStyledTextField("Shift: Day/Night", 130);
        TextField tfLic   = createStyledTextField("License Number", 160);
        TextField tfClr   = createStyledTextField("Clearance: L1/L2/L3 or Low/Medium/High", 260);

        // Auto-uppercase for ID
        tfId.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(newVal.toUpperCase())) {
                tfId.setText(newVal.toUpperCase());
            }
        });

        // Set initial states and handle designation changes
        cbDes.valueProperty().addListener((obs, o, n) -> {
            String v = n == null ? "" : n;
            tfShift.setDisable(!"Nurse".equals(v));
            tfLic.setDisable(!"Pharmacist".equals(v));
            tfClr.setDisable(!"Security".equals(v));
            tfCustomDes.setDisable(!"Generic Staff".equals(v));
            if ("Generic Staff".equals(v)) {
                tfCustomDes.clear();
            }
        });
        cbDes.getSelectionModel().select("Generic Staff");

        Button add = createStyledButton("Add Staff", "#607d8b");
        
        add.setOnAction(e -> {
            clearInvalidStates(tfId, tfName, tfSalary, tfShift, tfLic, tfClr, tfCustomDes);
            clearInvalidComboBox(cbDes, cbSex);
            try {
                String id   = validateNonEmpty(tfId.getText(), "ID", tfId);
                validateDuplicateId(id, "Staff", tfId);
                String name = validateAndFormatName(tfName.getText(), "Name", tfName);
                String des  = validateNonEmpty(cbDes.getValue(), "Designation", null);
                String sex  = validateSex(cbSex.getValue(), cbSex);
                int salary  = validateSalary(tfSalary.getText(), tfSalary);

                Staff s;
                switch (des) {
                    case "Nurse":
                        String shift = validateShift(tfShift.getText(), tfShift);
                        s = new Nurse(id, name, sex, salary, shift);
                        break;
                    case "Pharmacist":
                        String lic = validateNonEmpty(tfLic.getText(), "License Number", tfLic);
                        s = new Pharmacist(id, name, sex, salary, lic);
                        break;
                    case "Security":
                        String clr = validateClearanceLevel(tfClr.getText(), tfClr);
                        s = new SecurityStaff(id, name, sex, salary, clr);
                        break;
                    case "Generic Staff":
                        String customDes = validateAndFormatName(tfCustomDes.getText(), "Custom Designation", tfCustomDes);
                        s = new Staff(id, name, customDes, sex, salary);
                        break;
                    default:
                        s = new Staff(id, name, "Staff", sex, salary);
                }

                if (!HospitalManagement.addStaff(s)) { 
                    showWarning("Staff array is full in HospitalManagement."); 
                    return; 
                }
                reloadStaffs(); 
                tv.refresh();

                clearFields(tfId, tfName, tfSalary, tfShift, tfLic, tfClr, tfCustomDes);
                cbDes.getSelectionModel().select("Generic Staff");
                cbSex.getSelectionModel().clearSelection();
                showSuccess("Staff added successfully!");
            } catch (ValidationException ex) { 
                showError(ex.getMessage()); 
            }
        });

        VBox formContainer = createFormContainer(tfId, tfName, cbDes, tfCustomDes, cbSex, tfSalary, tfShift, tfLic, tfClr, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(formContainer);
        return root;
    }

    // ===== Enhanced UI helpers =====
    private TextField createStyledTextField(String prompt, double prefW) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(prefW);
        tf.setStyle(
            "-fx-background-color: rgba(255,255,255,0.9);" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 13;"
        );
        
        // Store original style
        tf.getProperties().put("originalStyle", tf.getStyle());
        
        // Add focus effects
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            String currentStyle = (String) tf.getProperties().get("originalStyle");
            if (newVal) {
                tf.setStyle(currentStyle + "-fx-border-color: #2196f3; -fx-border-width: 2;");
            } else {
                tf.setStyle(currentStyle);
            }
        });
        
        return tf;
    }

    private ComboBox<String> createStyledComboBox(String prompt, String... items) {
        ComboBox<String> cb = new ComboBox<>(FXCollections.observableArrayList(items));
        cb.setPromptText(prompt);
        cb.setPrefWidth(110);
        cb.setStyle(
            "-fx-background-color: rgba(255,255,255,0.9);" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 1;" +
            "-fx-font-size: 13;"
        );
        
        // Store original style
        cb.getProperties().put("originalStyle", cb.getStyle());
        
        return cb;
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setMinWidth(120);
        btn.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 10 20;" +
            "-fx-font-size: 13;" +
            "-fx-font-weight: bold;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);"
        );
        
        // Add hover effects
        btn.setOnMouseEntered(e -> {
            btn.setStyle(btn.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(btn.getStyle().replace("-fx-scale-x: 1.05; -fx-scale-y: 1.05;", ""));
        });
        
        return btn;
    }

    private VBox createFormContainer(javafx.scene.Node... nodes) {
        // Create rows of form elements (max 4 per row for better layout)
        VBox container = new VBox(10);
        HBox currentRow = new HBox(10);
        int itemsInRow = 0;
        
        for (javafx.scene.Node node : nodes) {
            if (itemsInRow >= 4 || (node instanceof Button)) {
                if (currentRow.getChildren().size() > 0) {
                    currentRow.setAlignment(Pos.CENTER_LEFT);
                    container.getChildren().add(currentRow);
                }
                currentRow = new HBox(10);
                itemsInRow = 0;
            }
            
            currentRow.getChildren().add(node);
            if (node instanceof TextField || node instanceof ComboBox) {
                HBox.setHgrow(node, Priority.SOMETIMES);
                itemsInRow++;
            } else if (node instanceof Button) {
                HBox buttonRow = new HBox(currentRow);
                buttonRow.setAlignment(Pos.CENTER);
                container.getChildren().add(buttonRow);
                currentRow = new HBox(10);
                itemsInRow = 0;
            }
        }
        
        if (currentRow.getChildren().size() > 0) {
            currentRow.setAlignment(Pos.CENTER_LEFT);
            container.getChildren().add(currentRow);
        }
        
        container.setPadding(new Insets(15));
        container.setStyle(
            "-fx-background-color: rgba(250,250,250,0.9);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );
        
        return container;
    }

    private void styleTable(TableView<?> table) {
        table.setStyle(
            "-fx-background-color: rgba(255,255,255,0.95);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e0e0e0;" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );
    }

    // ===== Invalid state management =====
    private void setInvalidState(TextField tf) {
        if (tf != null) {
            tf.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #f44336;" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-padding: 8;" +
                "-fx-font-size: 13;"
            );
        }
    }
    
    private void clearInvalidState(TextField tf) {
        if (tf != null) {
            String originalStyle = (String) tf.getProperties().get("originalStyle");
            if (originalStyle != null) {
                tf.setStyle(originalStyle);
            }
        }
    }
    
    private void setInvalidState(ComboBox<String> cb) {
        if (cb != null) {
            cb.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #f44336;" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-font-size: 13;"
            );
        }
    }
    
    private void clearInvalidState(ComboBox<String> cb) {
        if (cb != null) {
            String originalStyle = (String) cb.getProperties().get("originalStyle");
            if (originalStyle != null) {
                cb.setStyle(originalStyle);
            }
        }
    }
    
    private void clearInvalidStates(TextField... fields) {
        for (TextField tf : fields) {
            clearInvalidState(tf);
        }
    }
    
    private void clearInvalidComboBox(ComboBox<String>... comboBoxes) {
        for (ComboBox<String> cb : comboBoxes) {
            clearInvalidState(cb);
        }
    }

    // ===== Enhanced Validation Methods =====
    private String validateNonEmpty(String value, String fieldName, TextField field) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            if (field != null) setInvalidState(field);
            throw new ValidationException(fieldName + " cannot be empty.");
        }
        if (field != null) clearInvalidState(field);
        return value.trim();
    }

    private String validateAndFormatName(String value, String fieldName, TextField field) throws ValidationException {
        String trimmed = validateNonEmpty(value, fieldName, field);
        if (trimmed.matches(".*[0-9].*")) {
            if (field != null) setInvalidState(field);
            throw new ValidationException(fieldName + " should not contain numbers.");
        }
        if (field != null) clearInvalidState(field);
        return HospitalManagement.formatText(trimmed);
    }

    private int validateAge(String value, TextField field) throws ValidationException {
        try {
            int age = Integer.parseInt(validateNonEmpty(value, "Age", field));
            if (age < 0 || age > 150) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Age must be between 0 and 150.");
            }
            if (field != null) clearInvalidState(field);
            return age;
        } catch (NumberFormatException e) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Age must be a valid number.");
        }
    }

    private int validateRoomNumber(String value, TextField field) throws ValidationException {
        try {
            int room = Integer.parseInt(validateNonEmpty(value, "Room Number", field));
            if (room < 1 || room > 999) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Room number must be between 1 and 999.");
            }
            if (field != null) clearInvalidState(field);
            return room;
        } catch (NumberFormatException e) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Room number must be a valid number.");
        }
    }

    private int validateCost(String value, int min, int max, TextField field) throws ValidationException {
        try {
            int cost = Integer.parseInt(validateNonEmpty(value, "Cost", field));
            if (cost < min || cost > max) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Cost must be between " + min + " and " + max + ".");
            }
            if (field != null) clearInvalidState(field);
            return cost;
        } catch (NumberFormatException e) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Cost must be a valid number.");
        }
    }

    private int validateSalary(String value, TextField field) throws ValidationException {
        try {
            int salary = Integer.parseInt(validateNonEmpty(value, "Salary", field));
            if (salary < 1000 || salary > 50000) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Salary must be between 1000 and 50000.");
            }
            if (field != null) clearInvalidState(field);
            return salary;
        } catch (NumberFormatException e) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Salary must be a valid number.");
        }
    }

    private String validateSex(String value, ComboBox<String> field) throws ValidationException {
        if (value == null || (!value.equals("M") && !value.equals("F"))) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Please select M or F for sex.");
        }
        if (field != null) clearInvalidState(field);
        return value;
    }

    private String validateShift(String value, TextField field) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Shift cannot be empty for Nurse.");
        }
        String normalized = HospitalManagement.normalizeShift(value.trim());
        if (!normalized.equals("Day") && !normalized.equals("Night")) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Shift must be Day or Night.");
        }
        if (field != null) clearInvalidState(field);
        return normalized;
    }

    private String validateClearanceLevel(String value, TextField field) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Clearance Level cannot be empty for Security.");
        }
        String normalized = HospitalManagement.normalizeClearanceLevel(value.trim());
        if (!normalized.equals("Low") && !normalized.equals("Medium") && !normalized.equals("High")) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Clearance Level must be Low, Medium, High, L1, L2, or L3.");
        }
        if (field != null) clearInvalidState(field);
        return normalized;
    }

    private String validateDate(String value, TextField field) throws ValidationException {
        try {
            String date = validateNonEmpty(value, "Date", field);
            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Date format must be YYYY-MM-DD.");
            }
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            
            if (year < 2000 || year > 2030) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Year must be between 2000 and 2030.");
            }
            if (month < 1 || month > 12) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Month must be between 1 and 12.");
            }
            if (day < 1 || day > 31) {
                if (field != null) setInvalidState(field);
                throw new ValidationException("Day must be between 1 and 31.");
            }
            if (field != null) clearInvalidState(field);
            return date;
        } catch (NumberFormatException e) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private void validateDuplicateId(String id, String entityType, TextField field) throws ValidationException {
        boolean isDuplicate = false;
        
        if ("Doctor".equals(entityType)) {
            Doctor[] docs = HospitalManagement.getDoctors();
            int count = HospitalManagement.getDoctorCount();
            for (int i = 0; i < count; i++) {
                if (docs[i].getId().equalsIgnoreCase(id)) {
                    isDuplicate = true;
                    break;
                }
            }
        } else if ("Patient".equals(entityType)) {
            Patient[] pts = HospitalManagement.getPatients();
            int count = HospitalManagement.getPatientCount();
            for (int i = 0; i < count; i++) {
                if (pts[i].getId().equalsIgnoreCase(id)) {
                    isDuplicate = true;
                    break;
                }
            }
        } else if ("Staff".equals(entityType)) {
            Staff[] stfs = HospitalManagement.getStaffs();
            int count = HospitalManagement.getStaffCount();
            for (int i = 0; i < count; i++) {
                if (stfs[i].getId().equalsIgnoreCase(id)) {
                    isDuplicate = true;
                    break;
                }
            }
        }
        
        if (isDuplicate) {
            if (field != null) setInvalidState(field);
            throw new ValidationException("ID already exists. Please enter a unique ID.");
        }
    }

    // ===== Alert helpers =====
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===== Clear fields helper =====
    private void clearFields(TextField... fields) {
        for (TextField tf : fields) {
            if (tf != null) {
                tf.clear();
            }
        }
    }

    // ===== Main method for JavaFX =====
    public static void main(String[] args) {
        launch(args);
    }
}