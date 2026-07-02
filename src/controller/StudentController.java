package controller;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import java.net.URL;

import service.LoanService;
import service.StudentService;
import util.DataManager;

public class StudentController implements Initializable {
    @FXML
    private ComboBox<String> cmbSearchBy;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtCne;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMajor;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;

    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> colCne;
    @FXML
    private TableColumn<Student, String> colfName;
    @FXML
    private TableColumn<Student, String> collName;
    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, String> colMajor;

    private StudentService studentService = DataManager.studentService;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private final LoanService loanService = DataManager.loanService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colCne.setCellValueFactory(new PropertyValueFactory<>("cne"));
        colfName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        collName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMajor.setCellValueFactory(new PropertyValueFactory<>("major"));
        tblStudents.setItems(studentList);
        studentList.setAll(studentService.getAllStudents());

        tblStudents.setItems(studentList);
        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedStudent) -> {
            if (selectedStudent != null) {
                txtCne.setText(selectedStudent.getCne());
                txtFName.setText(selectedStudent.getFirstName());
                txtLName.setText(selectedStudent.getLastName());
                txtEmail.setText(selectedStudent.getEmail());
                txtMajor.setText(selectedStudent.getMajor());
            }
        });
    }

    private void showAlert(Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private void addStudent() {

        if (txtCne.getText().isEmpty()
                || txtFName.getText().isEmpty()
                || txtLName.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtMajor.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please fill in all fields.");
            return;
        }
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert(Alert.AlertType.WARNING,
                    "Invalid Email",
                    "Please enter a valid email address.");
            return;
        }
        Student student = new Student(
                txtCne.getText(),
                txtFName.getText(),
                txtLName.getText(),
                txtEmail.getText(),
                txtMajor.getText());
        if (studentService.addStudent(student)) {
            studentList.setAll(studentService.getAllStudents());
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "A student with this CNE already exists.");
        }
    }

    private void clearFields() {

        txtCne.clear();
        txtFName.clear();
        txtLName.clear();
        txtEmail.clear();
        txtMajor.clear();
    }

    @FXML
    private void updateStudent() {

        Student updatedStudent = new Student(
                txtCne.getText(),
                txtFName.getText(),
                txtLName.getText(),
                txtEmail.getText(),
                txtMajor.getText());

        if (studentService.updateStudent(updatedStudent)) {
            tblStudents.refresh();
            clearFields();
        }
    }

    @FXML
    private void deleteStudent() {

        Student selectedStudent = tblStudents.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {

            showAlert(Alert.AlertType.WARNING,
                    "No Selection",
                    "Please select a student.");

            return;
        }
        if (loanService.hasActiveLoan(selectedStudent)) {

            showAlert(Alert.AlertType.ERROR,
                    "Delete Student",
                    "This student cannot be deleted because they have an active loan.");

            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setTitle("Delete Student");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this student?");

        if (confirm.showAndWait().get() == ButtonType.OK) {

            studentService.deleteStudent(selectedStudent.getCne());

            studentList.setAll(studentService.getAllStudents());

            clearFields();

            showAlert(Alert.AlertType.INFORMATION,
                    "Deleted",
                    "Student deleted successfully.");
        }
    }

    @FXML
    private void searchStudent() {
        if (cmbSearchBy.getValue() == null) {
            showAlert(Alert.AlertType.WARNING,
                    "Search",
                    "Please select a search criterion.");
            return;
        }
        if (cmbSearchBy.getValue().equals("CNE")) {
            studentList.setAll(studentService.findStudentByCne(txtSearch.getText()));
        } else {
            studentList.setAll(studentService.searchByMajor(txtSearch.getText()));
        }
        if (studentList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Search",
                    "No students found.");
        }
    }

    @FXML
    private void clearForm() {
        clearFields();
    }
}
