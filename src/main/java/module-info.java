module cinema.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens cinema.app to javafx.fxml;
    opens cinema.controllers to javafx.fxml;

    exports cinema.app;
    exports cinema.controllers;
}