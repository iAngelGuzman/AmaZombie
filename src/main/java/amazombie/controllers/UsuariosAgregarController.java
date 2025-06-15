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
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class UsuariosAgregarController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox usuariosContainer;
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();

    @FXML private TextField nombreField;
    @FXML private TextField contraField;
    @FXML private TextField repContraField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nombreField.setText(usuarioDao.obtenerUsuario(usuarioDao.getIdUsuarioAEditar()).getNombre());
    }

    public void regresar() throws IOException {
        App.setContent("usuarios");
    }

    @FXML
    public void guardar() throws IOException {
        String nombre = nombreField.getText();
        String contra = contraField.getText();
        String repContra = repContraField.getText();
        if (nombre.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El nombre no puede quedar vacio",
                "Agrega un nombre",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (contra.isEmpty() || repContra.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "La contraseña no puede quedar vacia",
                "Agrega una contraseña",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (!contra.equals(repContra)) {
            mostrarAlerta(
                "Formulario",
                "La contraseña no es igual",
                "Vuelve a ingresar la contraseña",
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

        boolean agregado = usuarioDao.agregarUsuario(nombre, contra, "usuario");
        if (agregado) {
            mostrarAlerta(
                "Formulario",
                "Se ha agregado correctamente",
                "El usuario ha sido agregado correctamente",
                Alert.AlertType.INFORMATION
            );
            nombreField.setText("");
            contraField.setText("");
            repContraField.setText("");
            App.setContent("usuarios");
        } else {
            mostrarAlerta(
                "Formulario",
                "No se guardo el usuario.",
                "Verifique la base de datos.",
                Alert.AlertType.INFORMATION
            );
        }
    }

    public static void mostrarAlerta(String titulo, String mensaje, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
