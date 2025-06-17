package amazombie.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConexionDB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/amazombie";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            databaseNullConnection(e.getMessage());
            return null;
        }
    }
    
    public static boolean databaseNullConnection(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Base de Datos");
        alert.setHeaderText("Error al conectar a la base de datos.");
        alert.setContentText(error);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}

