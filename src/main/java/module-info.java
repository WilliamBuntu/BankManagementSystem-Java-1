module com.example.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.employeemanagementsystem to javafx.fxml;
    exports com.example.employeemanagementsystem;
    exports com.example.employeemanagementsystem.controllers;
    opens com.example.employeemanagementsystem.controllers to javafx.fxml;
}