package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


public class DashboardController {

    @FXML
    private AnchorPane contentPane;

    // @FXML
    // public void initialize() {
    //     loadPage("/view/books.fxml");
    // }

    @FXML
    private void openBooks() {
        loadPage("/view/books.fxml");
    }

    @FXML
    private void openStudents() {
        loadPage("/view/students.fxml");
    }

    @FXML
    private void openLoans() {
        loadPage("/view/loans.fxml");
    }

    @FXML
    private void openStatistics() {
        loadPage("/view/statistics.fxml");
    }

    private void loadPage(String fxml) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Logout() {
        // Implement logout logic here
        this.contentPane.getScene().getWindow().hide(); // Close the current window
        System.out.println("Logging out...");
    }
}
