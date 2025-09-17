module com.example.geoquestkidsexplorer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.geoquestkidsexplorer to javafx.fxml;
    exports com.example.geoquestkidsexplorer;
    opens  com.example.geoquestkidsexplorer.controllers to javafx.fxml;
    exports com.example.geoquestkidsexplorer.controllers;
    exports com.example.geoquestkidsexplorer.database;
    opens com.example.geoquestkidsexplorer.database to javafx.fxml;
}