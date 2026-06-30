package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button btnBooks;

    @FXML
    private void openBooks() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/books.fxml"));

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnBooks.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Books Management");
    }
}
