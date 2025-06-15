module amazombie {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires javafx.graphics;
    requires com.github.librepdf.openpdf;

    opens amazombie.controllers to javafx.fxml;
    exports amazombie;
}
