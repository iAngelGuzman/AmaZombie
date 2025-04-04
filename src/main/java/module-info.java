module amazombie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens amazombie.controllers to javafx.fxml;
    exports amazombie;
}
