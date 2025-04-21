package com.example.employeemanagementsystem.controllers;
//import com.company.ems.model.Employee;
//import com.company.ems.dao.EmployeeDatabase;

import com.example.employeemanagementsystem.database.EmployeeDatabase;
import com.example.employeemanagementsystem.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for the main application view.
 */
public class MainController {


    @FXML private TableColumn<Employee<Integer>, Integer> idColumn;
    @FXML private TableColumn<Employee<Integer>, String> nameColumn;
    @FXML private TableColumn<Employee<Integer>, String> deptColumn;
    @FXML private TableColumn<Employee<Integer>, Double> salaryColumn;
    @FXML private TableColumn<Employee<Integer>, Double> ratingColumn;
    @FXML private TableColumn<Employee<Integer>, Integer> experienceColumn;
    @FXML private TableColumn<Employee<Integer>, Boolean> activeColumn;



    @FXML
    private TableView<Employee<Integer>> employeeTableView;

    @FXML
    private TextField searchNameField;

    @FXML
    private TextField searchDeptField;

    @FXML
    private Slider ratingSlider;

    @FXML
    private ComboBox<String> sortComboBox;

    private EmployeeDatabase<Integer> database;
    private ObservableList<Employee<Integer>> employeeList;
    private int nextEmployeeId = 1;

    /**
     * Initializes the controller.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the database and list
        database = new EmployeeDatabase<>();
        employeeList = FXCollections.observableArrayList();
        employeeTableView.setItems(employeeList);

        // Initialize the sort combo box
        sortComboBox.getItems().addAll("By Experience", "By Salary", "By Performance");
        sortComboBox.setValue("By Experience");

        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEmployeeId()));
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        deptColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDepartment()));
        salaryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSalary()));
        ratingColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPerformanceRating()));
        experienceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getYearsOfExperience()));
        activeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().isActive()));


        // Add sample data
        addSampleData();

        // Update the list view
        updateEmployeeList();
    }


    @FXML
    private BorderPane rootPane;


    @FXML
    private void handleLogout() {
        // Show confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Logout Confirmation");
        confirmAlert.setHeaderText("Are you sure you want to logout?");
        confirmAlert.setContentText("Click OK to logout, or Cancel to stay.");

        // Show the dialog and wait for the user's response
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Load LoginView
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/employeemanagementsystem/login.fxml"));
                    Parent loginRoot = loader.load();

                    // Fade out current scene
                    Stage stage = (Stage) rootPane.getScene().getWindow();
                    Scene newScene = new Scene(loginRoot);

                    // Optional: request focus on username field
                    loginRoot.lookup("#usernameField").requestFocus();

                    // Optional animation (simple fade effect)
                    loginRoot.setOpacity(0);
                    stage.setScene(newScene);
                    stage.setTitle("Login");

                    javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), loginRoot);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Shows dialog for adding a new employee.
     */
    @FXML
    private void showAddEmployeeDialog() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/employeemanagementsystem/EmployeeDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Employee");
            dialog.setHeaderText("Enter employee details");

            // Get the controller
            EmployeeDialogController controller = loader.getController();

            // Show the dialog and process the result
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Employee<Integer> newEmployee = controller.getEmployee(nextEmployeeId++);
                if (newEmployee != null) {
                    database.addEmployee(newEmployee);
                    updateEmployeeList();
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Could not load dialog: " + e.getMessage());
        }
    }

    /**
     * Removes the selected employee.
     */
    @FXML
    private void removeSelectedEmployee() {
        Employee<Integer> selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            database.removeEmployee(selectedEmployee.getEmployeeId());
            updateEmployeeList();
        } else {
            showAlert("No Selection", "Please select an employee to remove.");
        }
    }

    /**
     * Shows dialog for updating an employee.
     */
    @FXML
    private void showUpdateEmployeeDialog() {
        Employee<Integer> selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("No Selection", "Please select an employee to update.");
            return;
        }

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/employeemanagementsystem/EmployeeDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Update Employee");
            dialog.setHeaderText("Update employee details");

            // Get the controller and set the employee
            EmployeeDialogController controller = loader.getController();
            controller.setEmployee(selectedEmployee);

            // Show the dialog and process the result
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Employee<Integer> updatedEmployee = controller.getEmployee(selectedEmployee.getEmployeeId());
                if (updatedEmployee != null) {
                    // Update all fields
                    Integer id = selectedEmployee.getEmployeeId();
                    database.updateEmployeeDetails(id, "name", updatedEmployee.getName());
                    database.updateEmployeeDetails(id, "department", updatedEmployee.getDepartment());
                    database.updateEmployeeDetails(id, "salary", updatedEmployee.getSalary());
                    database.updateEmployeeDetails(id, "performanceRating", updatedEmployee.getPerformanceRating());
                    database.updateEmployeeDetails(id, "yearsOfExperience", updatedEmployee.getYearsOfExperience());
                    database.updateEmployeeDetails(id, "isActive", updatedEmployee.isActive());

                    updateEmployeeList();
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Could not load dialog: " + e.getMessage());
        }
    }

    //Searches employees by name.

    @FXML
    private void searchByName() {
        String searchText = searchNameField.getText().trim();
        if (!searchText.isEmpty()) {
            employeeList.setAll(database.searchByName(searchText));
        } else {
            updateEmployeeList();
        }
    }

    //Searches employees by department.

    @FXML
    private void searchByDepartment() {
        String searchText = searchDeptField.getText().trim();
        if (!searchText.isEmpty()) {
            employeeList.setAll(database.searchByDepartment(searchText));
        } else {
            updateEmployeeList();
        }
    }

    //Filters employees by minimum rating.

    @FXML
    private void filterByRating() {
        double rating = ratingSlider.getValue();
        employeeList.setAll(database.filterByMinimumRating(rating));
    }

    //Sorts employees based on the selected sort option.

    @FXML
    private void sortEmployees() {
        String sortOption = sortComboBox.getValue();
        switch (sortOption) {
            case "By Experience" -> employeeList.setAll(database.sortByExperience());
            case "By Salary" -> employeeList.setAll(database.sortBySalary());
            case "By Performance" -> employeeList.setAll(database.sortByPerformance());
        }
    }

    //Shows all employees.

    @FXML
    private void showAllEmployees() {
        updateEmployeeList();
    }

    //Updates the employee list view with all employees.

    private void updateEmployeeList() {
        employeeList.setAll(database.getAllEmployees());
    }

    //Shows an alert dialog.

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Manually adds some sample data to the database.

    private void addSampleData() {
        database.addEmployee(new Employee<>(nextEmployeeId++, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Jane Smith", "HR", 65000, 4.8, 5, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Bob Johnson", "Finance", 85000, 4.2, 10, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Alice Brown", "IT", 70000, 4.7, 6, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Charlie Davis", "Marketing", 68000, 3.9, 4, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Eva Wilson", "HR", 62000, 4.1, 3, true));
        database.addEmployee(new Employee<>(nextEmployeeId++, "Frank Miller", "Finance", 90000, 4.6, 12, true));
    }


}
