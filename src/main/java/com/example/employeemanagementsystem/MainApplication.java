package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/employeemanagementsystem/login.fxml"));
            Parent loginRoot = loader.load();

            Scene loginScene = new Scene(loginRoot, 500, 400);
            loginScene.getStylesheets().add(getClass().getResource("/com/example/employeemanagementsystem/login.css").toExternalForm());

            primaryStage.setTitle("Login");
            primaryStage.setScene(loginScene);
            primaryStage.show();

            // Give the controller access to the primary stage
            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}