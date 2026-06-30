package controller;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Student;
import java.net.URL;
import service.StudentService;

public class StudentController implements Initializable {
    @FXML private ComboBox<String> cmbSearchBy;
    @FXML private TextField txtSearch;
    @FXML private Button btnSearch;

    @FXML private TextField txtCne;
    @FXML private TextField txtFName;
    @FXML private TextField txtLName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtMajor;


    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @FXML private TableView<Student> tblStudents;
    @FXML private TableColumn<Student, String> colCne;
    @FXML private TableColumn<Student, String> colfName;
    @FXML private TableColumn<Student, String> collName;
    @FXML private TableColumn<Student, String> colEmail;

    @FXML private TableColumn<Student, String> colMajor;


    private StudentService studentService = new StudentService();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();


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
 @FXML
    private void addStudent() {

        if (txtCne.getText().isEmpty()
                || txtFName.getText().isEmpty()
                || txtLName.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtMajor.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("A student with this CNE already exists.");
            alert.showAndWait();
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

        if (studentService.deleteStudent(txtCne.getText())) {
            studentList.setAll(studentService.getAllStudents());
            clearFields();
        }
    }

    @FXML
    private void searchStudent() {
        if (cmbSearchBy.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("Please select a search criterion.");
            alert.showAndWait();
            return;
        }
        if (cmbSearchBy.getValue().equals("CNE")) {
            studentList.setAll(studentService.findStudentByCne(txtSearch.getText()));
        } else {
            studentList.setAll(studentService.searchByMajor(txtSearch.getText()));
        }
        if (studentList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No students found.");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearForm() {
        clearFields();
    }
}
