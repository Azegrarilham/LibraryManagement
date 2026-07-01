package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import model.Book;
import service.BookService;
import util.DataManager;

public class BookController implements Initializable {

    @FXML
    private TextField txtCode, txtTitle, txtAuthor, txtCategory, txtYear, txtCopies, txtSearch;

    @FXML
    private ComboBox<String> cmbSearchBy;

    @FXML
    private TableView<Book> tblBooks;

    @FXML
    private TableColumn<Book, String> colCode;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, Integer> colYear;

    @FXML
    private TableColumn<Book, Integer> colCopies;

    private BookService bookService = DataManager.bookService;
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tblBooks.setItems(bookList);
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        // Load books from the service
        bookList.setAll(bookService.getAllBooks());
        tblBooks.setItems(bookList);
        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedBook) -> {
            if (selectedBook != null) {
                txtCode.setText(selectedBook.getCode());
                txtTitle.setText(selectedBook.getTitle());
                txtAuthor.setText(selectedBook.getAuthor());
                txtCategory.setText(selectedBook.getCategory());
                txtYear.setText(String.valueOf(selectedBook.getPublicationYear()));
                txtCopies.setText(String.valueOf(selectedBook.getAvailableCopies()));
            }
        });
    }

    @FXML
    private void addBook() {

        if (txtCode.getText().isEmpty() ||
                txtTitle.getText().isEmpty() ||
                txtAuthor.getText().isEmpty() ||
                txtCategory.getText().isEmpty() ||
                txtYear.getText().isEmpty() ||
                txtCopies.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please fill in all fields.");

            return;
        }

        try {

            int year = Integer.parseInt(txtYear.getText());
            int copies = Integer.parseInt(txtCopies.getText());

            Book book = new Book(
                    txtCode.getText(),
                    txtTitle.getText(),
                    txtAuthor.getText(),
                    txtCategory.getText(),
                    year,
                    copies);

            bookService.addBook(book);
            bookList.setAll(bookService.getAllBooks());

            showAlert(Alert.AlertType.INFORMATION,
                    "Success",
                    "Book added successfully.");

            clearFields();

        } catch (NumberFormatException e) {

            showAlert(Alert.AlertType.ERROR,
                    "Invalid Input",
                    "Year and Copies must be numbers.");
        }
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
    private void clearFields() {

        txtCode.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtCategory.clear();
        txtYear.clear();
        txtCopies.clear();
    }

    @FXML
    private void updateBook() {

        Book updatedBook = new Book(
                txtCode.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtCategory.getText(),
                Integer.parseInt(txtYear.getText()),
                Integer.parseInt(txtCopies.getText()));

        if (bookService.updateBook(updatedBook)) {
            tblBooks.refresh();
            clearFields();
        }
    }

    @FXML
    private void deleteBook() {

        Book selectedBook = tblBooks.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {

            showAlert(Alert.AlertType.WARNING,
                    "No Selection",
                    "Please select a book.");

            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setTitle("Delete Book");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this book?");

        if (confirm.showAndWait().get() == ButtonType.OK) {

            bookService.deleteBook(selectedBook.getCode());

            bookList.setAll(bookService.getAllBooks());

            clearFields();

            showAlert(Alert.AlertType.INFORMATION,
                    "Deleted",
                    "Book deleted successfully.");
        }
    }

    @FXML
    private void searchBook() {
        if (cmbSearchBy.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("Please select a search criterion.");
            alert.showAndWait();
            return;
        }
        if (cmbSearchBy.getValue().equals("Title")) {
            bookList.setAll(bookService.searchByTitle(txtSearch.getText()));
        } else {
            bookList.setAll(bookService.searchByAuthor(txtSearch.getText()));
        }
        if (bookList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No books found.");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearForm() {
        clearFields();
    }
}
