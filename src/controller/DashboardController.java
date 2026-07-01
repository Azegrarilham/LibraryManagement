package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;



public class DashboardController {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button btnBooks;
    @FXML
    private Button btnStudents;
    @FXML
    private Button btnLoans;
    @FXML
    private Button btnStatistics;
    @FXML
    public void initialize() {
        setActiveButton(null);
    }

    @FXML
    private void openBooks() {
        setActiveButton(btnBooks);
        loadPage("/view/books.fxml");
    }

    @FXML
    private void openStudents() {
        setActiveButton(btnStudents);
        loadPage("/view/students.fxml");
    }

    @FXML
    private void openLoans() {
        setActiveButton(btnLoans);
        loadPage("/view/loans.fxml");
    }

    @FXML
    private void openStatistics() {
        setActiveButton(btnStatistics);
        loadPage("/view/statistics.fxml");
    }

    private void setActiveButton(Button active) {
        Button[] navButtons = { btnBooks, btnStudents, btnLoans, btnStatistics };
        for (Button btn : navButtons) {
            btn.getStyleClass().remove("nav-button-active");
        }
        if (active != null) {
            active.getStyleClass().add("nav-button-active");
        }
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
