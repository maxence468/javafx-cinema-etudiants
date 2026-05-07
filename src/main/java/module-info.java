module cinema.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;  // ← driver PostgreSQL

    opens cinema.app to javafx.fxml;
    opens cinema.controllers to javafx.fxml;
    opens cinema.BO to javafx.base;

    exports cinema.app;
    exports cinema.controllers;
}