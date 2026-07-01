package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import service.BookService;
import service.StudentService;
import service.LoanService;
import util.DataManager;

public class StatisticsController {

    @FXML
    private Label lblLastUpdated;

    @FXML
    private Label lblTotalBooks;
    @FXML
    private Label lblAvailableBooks;
    @FXML
    private Label lblBorrowedBooks;

    @FXML
    private Label lblTotalStudents;
    @FXML
    private Label lblTotalLoans;
    @FXML
    private Label lblReturnedLoans;
    @FXML
    private Label lblActiveLoans;

    @FXML
    private Button btnRefresh;
    private final BookService bookService = DataManager.bookService;
    private final StudentService studentService = DataManager.studentService;
    private final LoanService loanService = DataManager.loanService;
    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void refresh() {
        lblTotalBooks.setText(String.valueOf(bookService.getTotalBooks()));
        lblAvailableBooks.setText(String.valueOf(bookService.getAvailableBooks()));
        lblBorrowedBooks.setText(String.valueOf(bookService.getBorrowedBooks()));

        lblTotalStudents.setText(String.valueOf(studentService.getTotalStudents()));

        lblTotalLoans.setText(String.valueOf(loanService.getTotalLoans()));
        lblReturnedLoans.setText(String.valueOf(loanService.getReturnedLoansCount()));
        lblActiveLoans.setText(String.valueOf(loanService.getActiveLoans()));

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm:ss"));

        lblLastUpdated.setText("Last updated: " + timestamp);
    }
}
