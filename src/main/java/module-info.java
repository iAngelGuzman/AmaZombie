module amazombie {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.base;
    requires java.desktop;

    opens amazombie.controllers to javafx.fxml;
    exports amazombie;
}
