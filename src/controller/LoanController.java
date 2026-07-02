package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Loan;
import model.Student;
import service.BookService;
import service.LoanService;
import service.StudentService;
import util.DataManager;

public class LoanController implements Initializable {

    @FXML
    private TextField txtLoanNumber;

    @FXML
    private ComboBox<Student> cmbStudent;

    @FXML
    private ComboBox<Book> cmbBook;

    @FXML
    private DatePicker dpBorrowDate;

    @FXML
    private DatePicker dpReturnDate;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableView<Loan> tblLoans;

    @FXML
    private TableColumn<Loan, String> colLoanNumber;

    @FXML
    private TableColumn<Loan, Student> colStudent;

    @FXML
    private TableColumn<Loan, Book> colBook;

    @FXML
    private TableColumn<Loan, LocalDate> colBorrowDate;

    @FXML
    private TableColumn<Loan, LocalDate> colReturnDate;

    @FXML
    private TableColumn<Loan, String> colStatus;
    @FXML
    private ComboBox<String> cmbFilter;

    private final LoanService loanService = DataManager.loanService;
    private final StudentService studentService = DataManager.studentService;
    private final BookService bookService = DataManager.bookService;

    private ObservableList<Loan> loanList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbStudent.setItems(
                FXCollections.observableArrayList(studentService.getAllStudents()));

        cmbBook.setItems(
                FXCollections.observableArrayList(bookService.getAllBooks()));

        cmbStatus.getItems().addAll("Borrowed", "Returned");
        cmbStatus.setValue("Borrowed");
        txtLoanNumber.setText(loanService.generateLoanNumber());
        colLoanNumber.setCellValueFactory(new PropertyValueFactory<>("loanNumber"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("student"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        txtLoanNumber.setEditable(false);
        loanList.setAll(loanService.getAllLoans());
        tblLoans.setItems(loanList);

        tblLoans.getSelectionModel().selectedItemProperty().addListener((obs, oldLoan, loan) -> {

            if (loan != null) {

                txtLoanNumber.setText(loan.getLoanNumber());
                cmbStudent.setValue(loan.getStudent());
                cmbBook.setValue(loan.getBook());
                dpBorrowDate.setValue(loan.getBorrowDate());
                dpReturnDate.setValue(loan.getReturnDate());
                cmbStatus.setValue(loan.getStatus());
            }
            txtLoanNumber.setDisable(true);
            cmbStudent.setDisable(true);
            cmbBook.setDisable(true);
            dpBorrowDate.setDisable(true);
            cmbStatus.setDisable(true);
        });
    }

    @FXML
    private void borrowBook() {
        if (txtLoanNumber.getText().isEmpty()
                || cmbStudent.getValue() == null
                || cmbBook.getValue() == null
                || dpBorrowDate.getValue() == null
                || dpReturnDate.getValue() == null) {

            showAlert(Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please complete all fields.");

            return;
        }

        Book selectedBook = cmbBook.getValue();

        if (selectedBook.getAvailableCopies() <= 0) {

            showAlert(Alert.AlertType.ERROR,
                    "Unavailable",
                    "No copies of this book are available.");

            return;
        }

        Loan loan = new Loan(
                txtLoanNumber.getText(),
                cmbStudent.getValue(),
                selectedBook,
                dpBorrowDate.getValue(),
                dpReturnDate.getValue(),
                cmbStatus.getValue());

        loanService.addLoan(loan);

        selectedBook.setAvailableCopies(selectedBook.getAvailableCopies() - 1);

        bookService.saveBooks(); // We'll add this next

        loanList.setAll(loanService.getAllLoans());

        tblLoans.refresh();

        clearForm();

        showAlert(Alert.AlertType.INFORMATION,
                "Success",
                "Book borrowed successfully.");
    }



    @FXML
    private void updateLoan() {

        Loan selectedLoan = tblLoans.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING,
                    "Update",
                    "Please select a loan.");
            return;
        }

        // Update return date
        selectedLoan.setReturnDate(dpReturnDate.getValue());

        // Automatically return the book
        if (selectedLoan.getStatus().equals("Borrowed")) {

            Book book = selectedLoan.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookService.saveBooks();

            selectedLoan.setStatus("Returned");
        }

        loanService.saveLoans();

        tblLoans.refresh();

        showAlert(Alert.AlertType.INFORMATION,
                "Success",
                "Book returned successfully.");

        clearForm();
    }

    @FXML
    private void deleteLoan() {
        Loan loan = tblLoans.getSelectionModel().getSelectedItem();

        if (loan == null) {

            showAlert(Alert.AlertType.WARNING,
                    "Select Loan",
                    "Please select a loan.");

            return;
        }

        loanService.deleteLoan(loan);

        loanList.setAll(loanService.getAllLoans());

        clearForm();

        showAlert(Alert.AlertType.INFORMATION,
                "Deleted",
                "Loan deleted.");
    }

    @FXML
    private void searchLoan() {
        switch (cmbFilter.getValue()) {

            case "Borrowed":
                loanList.setAll(loanService.getBorrowedLoans());
                break;

            case "Returned":
                loanList.setAll(loanService.getReturnedLoans());
                break;

            default:
                loanList.setAll(loanService.getAllLoans());
                break;
        }

        if (loanList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Search",
                    "No loans found.");
        }
    }

    @FXML
    private void clearForm() {
        txtLoanNumber.setText(loanService.generateLoanNumber());
        cmbStudent.getSelectionModel().clearSelection();
        cmbBook.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
        dpBorrowDate.setValue(null);
        dpReturnDate.setValue(null);
        tblLoans.getSelectionModel().clearSelection();
        cmbStudent.setDisable(false);
        cmbBook.setDisable(false);
        dpBorrowDate.setDisable(false);
        cmbStatus.setDisable(false);
        txtLoanNumber.setDisable(true);
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

}
