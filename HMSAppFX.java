import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

        TabPane tabs = new TabPane(
                tab("Doctors",   doctorsPane()),
                tab("Patients",  patientsPane()),
                tab("Medicine",  medicinesPane()),
                tab("Labs",      labsPane()),
                tab("Facilities",facilitiesPane()),
                tab("Staff",     staffPane())
        );
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabs, 1024, 600);
        stage.setTitle("Hospital Management (JavaFX)");
        stage.setScene(scene);
        stage.show();
    }

    private Tab tab(String title, Region content) {
        Tab t = new Tab(title);
        t.setContent(new StackPane(content));
        return t;
    }

    // ====================== Doctors ======================
    private BorderPane doctorsPane() {
        TableView<DoctorRow> tv = new TableView<>(doctors);
        tv.getColumns().addAll(
                col("ID", DoctorRow::id),
                col("Name", DoctorRow::name),
                col("Specialist", DoctorRow::specialist),
                col("Work Time", DoctorRow::workTime),
                col("Qualification", DoctorRow::qualification),
                colInt("Room No.", DoctorRow::room)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId    = tf("ID", 90);
        TextField tfName  = tf("Name", 140);
        TextField tfSpec  = tf("Specialist", 140);
        TextField tfWork  = tf("Work Time", 120);
        TextField tfQual  = tf("Qualification", 160);
        TextField tfRoom  = tf("Room No. (>=0)", 120);
        Button add = new Button("Add Doctor");
        add.setMinWidth(110); 
        add.setOnAction(e -> {
            try {
                String id   = mustNonEmpty(tfId.getText(), "ID");
                String name = mustNonEmpty(tfName.getText(), "Name");
                String sp   = mustNonEmpty(tfSpec.getText(), "Specialist");
                String wt   = mustNonEmpty(tfWork.getText(), "Work Time");
                String q    = mustNonEmpty(tfQual.getText(), "Qualification");
                int room    = mustNonNegativeInt(tfRoom.getText(), "Room No.");
                Doctor d = new Doctor(id, name, sp, wt, q, room);
                if (!HospitalManagement.addDoctor(d)) warn("Doctor array is full in HospitalManagement.");
                reloadDoctors(); tv.refresh();
                clear(tfId, tfName, tfSpec, tfWork, tfQual, tfRoom);
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfId, tfName, tfSpec, tfWork, tfQual, tfRoom, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ====================== Patients ======================
    private BorderPane patientsPane() {
        TableView<PatientRow> tv = new TableView<>(patients);
        tv.getColumns().addAll(
                col("ID", PatientRow::id),
                col("Name", PatientRow::name),
                col("Disease", PatientRow::disease),
                col("Sex", PatientRow::sex),
                col("Admit Status", PatientRow::admitStatus),
                colInt("Age", PatientRow::age)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId   = tf("ID", 90);
        TextField tfName = tf("Name", 140);
        TextField tfDis  = tf("Disease", 140);
        ComboBox<String> cbSex = new ComboBox<>(FXCollections.observableArrayList("M", "F"));
        cbSex.setPromptText("Sex (M/F)");
        cbSex.setPrefWidth(110);
        TextField tfAdmit= tf("Admit Status", 140);
        TextField tfAge  = tf("Age (>=0)", 90);
        Button add = new Button("Add Patient");
        add.setMinWidth(110);
        add.setOnAction(e -> {
            try {
                String id   = mustNonEmpty(tfId.getText(), "ID");
                String name = mustNonEmpty(tfName.getText(), "Name");
                String dis  = mustNonEmpty(tfDis.getText(), "Disease");
                String sex  = mustSex(cbSex.getValue());
                String adm  = mustNonEmpty(tfAdmit.getText(), "Admit Status");
                int age     = mustNonNegativeInt(tfAge.getText(), "Age");
                Patient p = new Patient(id, name, dis, sex, adm, age);
                if (!HospitalManagement.addPatient(p)) warn("Patient array is full in HospitalManagement.");
                reloadPatients(); tv.refresh();
                clear(tfId, tfName, tfDis, tfAdmit, tfAge); cbSex.getSelectionModel().clearSelection();
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfId, tfName, tfDis, cbSex, tfAdmit, tfAge, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ====================== Medicine ======================
    private BorderPane medicinesPane() {
        TableView<MedicineRow> tv = new TableView<>(medicines);
        tv.getColumns().addAll(
                col("Name", MedicineRow::name),
                col("Manufacturer", MedicineRow::manufacturer),
                col("Expiry Date", MedicineRow::expiryDate),
                colInt("Cost", MedicineRow::cost)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfName = tf("Name", 160);
        TextField tfManu = tf("Manufacturer", 160);
        TextField tfExp  = tf("Expiry Date (YYYY-MM-DD)", 200);
        TextField tfCost = tf("Cost (>=0)", 100);
        Button add = new Button("Add Medicine");
        add.setMinWidth(120);
        add.setOnAction(e -> {
            try {
                String name = mustNonEmpty(tfName.getText(), "Name");
                String manu = mustNonEmpty(tfManu.getText(), "Manufacturer");
                String exp  = mustNonEmpty(tfExp.getText(), "Expiry Date");
                int cost    = mustNonNegativeInt(tfCost.getText(), "Cost");
                Medicine m = new Medicine(name, manu, exp, cost, 0);
                if (!HospitalManagement.addMedicine(m)) warn("Medicine array is full in HospitalManagement.");
                reloadMedicines(); tv.refresh();
                clear(tfName, tfManu, tfExp, tfCost);
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfName, tfManu, tfExp, tfCost, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ====================== Labs ======================
    private BorderPane labsPane() {
        TableView<LabRow> tv = new TableView<>(labs);
        tv.getColumns().addAll(col("Lab", LabRow::lab), colInt("Cost", LabRow::cost));
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfLab  = tf("Lab", 200);
        TextField tfCost = tf("Cost (>=0)", 120);
        Button add = new Button("Add Lab");
        add.setMinWidth(100);
        add.setOnAction(e -> {
            try {
                String lab = mustNonEmpty(tfLab.getText(), "Lab");
                int cost   = mustNonNegativeInt(tfCost.getText(), "Cost");
                Lab l = new Lab(lab, cost);
                if (!HospitalManagement.addLab(l)) warn("Lab array is full in HospitalManagement.");
                reloadLabs(); tv.refresh();
                clear(tfLab, tfCost);
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfLab, tfCost, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ====================== Facilities ======================
    private BorderPane facilitiesPane() {
        TableView<FacilityRow> tv = new TableView<>(facilities);
        tv.getColumns().addAll(col("Facility", FacilityRow::facility));
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfFac = tf("Facility", 260);
        Button add = new Button("Add Facility");
        add.setMinWidth(120);
        add.setOnAction(e -> {
            try {
                String f = mustNonEmpty(tfFac.getText(), "Facility");
                Facility fac = new Facility(f);
                if (!HospitalManagement.addFacility(fac)) warn("Facility array is full in HospitalManagement.");
                reloadFacilities(); tv.refresh();
                clear(tfFac);
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfFac, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ====================== Staff ======================
    private BorderPane staffPane() {
        TableView<StaffRow> tv = new TableView<>(staffs);
        tv.getColumns().addAll(
                col("ID", StaffRow::id),
                col("Name", StaffRow::name),
                col("Designation", StaffRow::designation),
                col("Shift", StaffRow::shift),
                col("License Number", StaffRow::licenseNumber),
                col("Clearance Level", StaffRow::clearanceLevel),
                col("Sex", StaffRow::sex),
                colInt("Salary", StaffRow::salary)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField tfId   = tf("ID", 90);
        TextField tfName = tf("Name", 140);
        ComboBox<String> cbDes = new ComboBox<>(FXCollections.observableArrayList("Nurse", "Pharmacist", "Security", "Staff"));
        cbDes.setPromptText("Designation");
        cbDes.setPrefWidth(130);
        ComboBox<String> cbSex = new ComboBox<>(FXCollections.observableArrayList("M", "F"));
        cbSex.setPromptText("Sex (M/F)");
        cbSex.setPrefWidth(100);
        TextField tfSalary = tf("Salary (>=0)", 110);

        TextField tfShift = tf("Shift: Day/Night", 130);
        TextField tfLic   = tf("License Number", 160);
        TextField tfClr   = tf("Clearance: L1/L2/L3 or Low/Medium/High", 260);

        cbDes.valueProperty().addListener((obs, o, n) -> {
            String v = n == null ? "" : n;
            tfShift.setDisable(!"Nurse".equals(v));
            tfLic.setDisable(!"Pharmacist".equals(v));
            tfClr.setDisable(!"Security".equals(v));
        });
        cbDes.getSelectionModel().select("Staff");

        Button add = new Button("Add Staff");
        add.setMinWidth(110); 
        add.setOnAction(e -> {
            try {
                String id   = mustNonEmpty(tfId.getText(), "ID");
                String name = mustNonEmpty(tfName.getText(), "Name");
                String des  = mustNonEmpty(cbDes.getValue(), "Designation");
                String sex  = mustSex(cbSex.getValue());
                int salary  = mustNonNegativeInt(tfSalary.getText(), "Salary");

                Staff s;
                switch (des) {
                    case "Nurse":
                        String shift = mustShift(tfShift.getText());
                        s = new Nurse(id, name, sex, salary, shift);
                        break;
                    case "Pharmacist":
                        String lic = mustNonEmpty(tfLic.getText(), "License Number");
                        s = new Pharmacist(id, name, sex, salary, lic);
                        break;
                    case "Security":
                        String clr = normalizeClearanceStrict(tfClr.getText());
                        s = new SecurityStaff(id, name, sex, salary, clr);
                        break;
                    default:
                        s = new Staff(id, name, "Staff", sex, salary);
                }

                if (!HospitalManagement.addStaff(s)) { warn("Staff array is full in HospitalManagement."); return; }
                reloadStaffs(); tv.refresh();

                clear(tfId, tfName, tfSalary, tfShift, tfLic, tfClr);
                cbDes.getSelectionModel().select("Staff");
                cbSex.getSelectionModel().clearSelection();
            } catch (IllegalArgumentException ex) { error(ex.getMessage()); }
        });

        HBox form = row(tfId, tfName, cbDes, cbSex, tfSalary, tfShift, tfLic, tfClr, add);
        BorderPane root = new BorderPane(tv);
        root.setBottom(form);
        return root;
    }

    // ===== UI helpers =====
    private TextField tf(String prompt, double prefW) {
        TextField t = new TextField();
        t.setPromptText(prompt);
        t.setPrefWidth(prefW);
        return t;
    }

    private HBox row(javafx.scene.Node... nodes) {
        HBox h = new HBox(8, nodes);
        h.setPadding(new Insets(10));
        for (javafx.scene.Node n : nodes) {
            if (n instanceof Button) {
                ((Button) n).setMinWidth(Region.USE_PREF_SIZE);
            } else if (n instanceof TextField || n instanceof ComboBox) {
                HBox.setHgrow(n, Priority.SOMETIMES);
            }
        }
        return h;
    }

    // ===== Loaders =====
    private void reloadAllFromHM() {
        reloadDoctors(); reloadPatients(); reloadMedicines(); reloadLabs(); reloadFacilities(); reloadStaffs();
    }

    private void reloadDoctors() {
        doctors.clear();
        Doctor[] arr = HospitalManagement.getDoctors();
        int n = HospitalManagement.getDoctorCount();
        for (int i = 0; i < n; i++) {
            Doctor d = arr[i];
            doctors.add(new DoctorRow(d.getId(), d.getName(), d.getSpecialist(), d.getWorkTime(), d.getQualification(), d.getRoom()));
        }
    }

    private void reloadPatients() {
        patients.clear();
        Patient[] arr = HospitalManagement.getPatients();
        int n = HospitalManagement.getPatientCount();
        for (int i = 0; i < n; i++) {
            Patient p = arr[i];
            patients.add(new PatientRow(p.getId(), p.getName(), p.getDisease(), p.getSex(), p.getAdmitStatus(), p.getAge()));
        }
    }

    private void reloadMedicines() {
        medicines.clear();
        Medicine[] arr = HospitalManagement.getMedicines();
        int n = HospitalManagement.getMedicineCount();
        for (int i = 0; i < n; i++) {
            Medicine m = arr[i];
            medicines.add(new MedicineRow(m.getName(), m.getManufacturer(), m.getExpiryDate(), m.getCost()));
        }
    }

    private void reloadLabs() {
        labs.clear();
        Lab[] arr = HospitalManagement.getLabs();
        int n = HospitalManagement.getLabCount();
        for (int i = 0; i < n; i++) {
            Lab l = arr[i];
            labs.add(new LabRow(l.getLab(), l.getCost()));
        }
    }

    private void reloadFacilities() {
        facilities.clear();
        Facility[] arr = HospitalManagement.getFacilities();
        int n = HospitalManagement.getFacilityCount();
        for (int i = 0; i < n; i++) {
            Facility f = arr[i];
            facilities.add(new FacilityRow(f.getFacility()));
        }
    }

    private void reloadStaffs() {
        staffs.clear();
        Staff[] arr = HospitalManagement.getStaffs();
        int n = HospitalManagement.getStaffCount();
        for (int i = 0; i < n; i++) {
            Staff s = arr[i];

            String designation =
                (s.getDesignation() == null || s.getDesignation().trim().isEmpty())
                    ? (s instanceof Nurse ? "Nurse" : s instanceof Pharmacist ? "Pharmacist" : s instanceof SecurityStaff ? "Security" : "Staff")
                    : s.getDesignation();

            String shift = "-";
            String license = "-";
            String clearance = "-";

            if (s instanceof Nurse) shift = ((Nurse) s).getShift();
            else if (s instanceof Pharmacist) license = ((Pharmacist) s).getLicenseNumber();
            else if (s instanceof SecurityStaff) clearance = normalizeClearanceStrict(((SecurityStaff) s).getClearanceLevel());

            staffs.add(new StaffRow(s.getId(), s.getName(), designation, shift, license, clearance, s.getSex(), s.getSalary()));
        }
    }

    // ===== Validation =====
    private static String mustNonEmpty(String v, String label) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(label + " cannot be empty.");
        return v.trim();
    }
    private static int mustNonNegativeInt(String v, String label) {
        try {
            int x = Integer.parseInt(mustNonEmpty(v, label));
            if (x < 0) throw new IllegalArgumentException(label + " must be >= 0.");
            return x;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + " must be a valid number.");
        }
    }
    private static String mustSex(String v) {
        if ("M".equalsIgnoreCase(v)) return "M";
        if ("F".equalsIgnoreCase(v)) return "F";
        throw new IllegalArgumentException("Invalid Sex. Please choose M or F.");
    }
    private static String mustShift(String v) {
        String s = mustNonEmpty(v, "Shift").toLowerCase();
        if (s.startsWith("d")) return "Day";
        if (s.startsWith("n")) return "Night";
        throw new IllegalArgumentException("Invalid Shift. Please enter Day or Night.");
    }
    private static String normalizeClearanceStrict(String v) {
        String s = mustNonEmpty(v, "Clearance").toUpperCase();
        if ("L1".equals(s) || "LOW".equals(s)) return "Low";
        if ("L2".equals(s) || "MEDIUM".equals(s)) return "Medium";
        if ("L3".equals(s) || "HIGH".equals(s)) return "High";
        throw new IllegalArgumentException("Invalid Clearance. Use L1/L2/L3 or Low/Medium/High.");
    }

    private static void error(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
    private static void warn(String msg)  { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private static void clear(TextField... fields) { for (TextField f : fields) f.clear(); }

    // ===== Small table helpers =====
    private <T> TableColumn<T, String> col(String title, java.util.function.Function<T, String> f) {
        TableColumn<T, String> c = new TableColumn<>(title);
        c.setCellValueFactory(d -> new SimpleStringProperty(f.apply(d.getValue())));
        return c;
    }
    private <T> TableColumn<T, Number> colInt(String title, java.util.function.ToIntFunction<T> f) {
        TableColumn<T, Number> c = new TableColumn<>(title);
        c.setCellValueFactory(d -> new SimpleIntegerProperty(f.applyAsInt(d.getValue())));
        return c;
    }

    // ===== Row DTOs =====
    public static class DoctorRow {
        final String id, name, specialist, workTime, qualification; final int room;
        DoctorRow(String id, String name, String sp, String wt, String q, int room) {
            this.id=id; this.name=name; this.specialist=sp; this.workTime=wt; this.qualification=q; this.room=room;
        }
        String id(){return id;} String name(){return name;} String specialist(){return specialist;}
        String workTime(){return workTime;} String qualification(){return qualification;} int room(){return room;}
    }
    public static class PatientRow {
        final String id, name, disease, sex, admitStatus; final int age;
        PatientRow(String id, String name, String d, String sex, String a, int age){
            this.id=id; this.name=name; this.disease=d; this.sex=sex; this.admitStatus=a; this.age=age;
        }
        String id(){return id;} String name(){return name;} String disease(){return disease;}
        String sex(){return sex;} String admitStatus(){return admitStatus;} int age(){return age;}
    }
    public static class MedicineRow {
        final String name, manufacturer, expiryDate; final int cost;
        MedicineRow(String n,String m,String e,int c){name=n; manufacturer=m; expiryDate=e; cost=c;}
        String name(){return name;} String manufacturer(){return manufacturer;} String expiryDate(){return expiryDate;} int cost(){return cost;}
    }
    public static class LabRow {
        final String lab; final int cost;
        LabRow(String l,int c){lab=l; cost=c;}
        String lab(){return lab;} int cost(){return cost;}
    }
    public static class FacilityRow {
        final String facility;
        FacilityRow(String f){facility=f;}
        String facility(){return facility;}
    }
    public static class StaffRow {
        final String id,name,designation,shift,licenseNumber,clearanceLevel,sex; final int salary;
        StaffRow(String id,String name,String des,String shift,String lic,String clr,String sex,int sal){
            this.id=id; this.name=name; this.designation=des; this.shift=shift; this.licenseNumber=lic; this.clearanceLevel=clr; this.sex=sex; this.salary=sal;
        }
        String id(){return id;} String name(){return name;} String designation(){return designation;}
        String shift(){return shift;} String licenseNumber(){return licenseNumber;} String clearanceLevel(){return clearanceLevel;}
        String sex(){return sex;} int salary(){return salary;}
    }

    public static void main(String[] args) { launch(args); }
}
