module amazombie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens amazombie.controllers to javafx.fxml;
    exports amazombie;
}
