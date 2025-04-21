package com.example.employeemanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("1".equals(username) && "1".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/employeemanagementsystem/MainView.fxml"));
                Parent mainRoot = loader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow(); // get current stage
                Scene mainScene = new Scene(mainRoot);

                // Optional: Fade transition
                mainRoot.setOpacity(0);
                stage.setScene(mainScene);
                stage.setTitle("Employee Management System");

                javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), mainRoot);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Failed to load the main application.");
            }
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        // This method can be used to set the primary stage if needed
        // For example, to close the login window after successful login
        primaryStage.setOnCloseRequest(event -> {
            // Handle close request if needed
        });
    }
}
