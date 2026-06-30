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

    private BookService bookService = new BookService();
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        cmbSearchBy.getItems().addAll("Title", "Author");
        cmbSearchBy.setPromptText("Search by");

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

        if (txtCode.getText().isEmpty()
                || txtTitle.getText().isEmpty()
                || txtAuthor.getText().isEmpty()
                || txtCategory.getText().isEmpty()
                || txtYear.getText().isEmpty()
                || txtCopies.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        Book book = new Book(
                txtCode.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtCategory.getText(),
                Integer.parseInt(txtYear.getText()),
                Integer.parseInt(txtCopies.getText()));
        if (bookService.addBook(book)) {
            bookList.setAll(bookService.getAllBooks());
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("A book with this code already exists.");
            alert.showAndWait();
        }
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

        if (bookService.deleteBook(txtCode.getText())) {
            bookList.setAll(bookService.getAllBooks());
            clearFields();
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
    }

    @FXML
    private void clearForm() {
        clearFields();
    }
}
