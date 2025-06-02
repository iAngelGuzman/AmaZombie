/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import amazombie.App;
import amazombie.dao.UsuarioDao;
import amazombie.models.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class EmpleadosEditarController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox empleadosContainer;
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();

    @FXML private TextField nombreField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nombreField.setText(usuarioDao.obtenerUsuario(usuarioDao.getIdUsuarioAEditar()).getNombre());
    }

    public void actualizarDatos() {
        Usuario usuario = usuarioDao.obtenerUsuario(usuarioDao.getIdUsuarioAEditar());
        if (usuario != null) {
            nombreField.setText(usuario.getNombre());
        }
    }

    public void regresar() throws IOException {
        App.setContent("empleados");
    }

    @FXML
    public void guardar() throws IOException {
        String nombre = nombreField.getText();
        if (nombre.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El nombre no puede quedar vacio",
                "Agrega un nombre",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (usuarioDao.existeUsuario(nombre)) {
            mostrarAlerta(
                "Formulario",
                "El nombre ya existe",
                "Elija otro nombre",
                Alert.AlertType.WARNING
            );
            return;
        }

        usuarioDao.actualizarUsuario(new Usuario(
            usuarioDao.getIdUsuarioAEditar(),
            nombre,
            usuarioDao.obtenerUsuario(usuarioDao.getIdUsuarioAEditar()).getRol())
        );
        // Mensaje de exito
        mostrarAlerta(
            "Formulario",
            "Se ha actualizado correctamente",
            "El empleado ha sido actualizado",
            Alert.AlertType.INFORMATION
        );

        App.setContent("empleados");
    }

    public static void mostrarAlerta(String titulo, String mensaje, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
