/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.UnaryOperator;

import amazombie.App;
import amazombie.dao.PaqueteriaDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Usuario;
import amazombie.utils.Estado;
import amazombie.utils.Ruta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;


public class InventarioAgregarController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox usuariosContainer;
    private final PaqueteriaDao paqueteriaDao = PaqueteriaDao.getInstancia();
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();

    @FXML private ChoiceBox<String> usuarioCB;
    @FXML private TextField nombreField;
    @FXML private TextField descripcionField;
    @FXML private TextField origenField;
    @FXML private TextField destinoField;
    @FXML private TextField precioField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarCampos();
    }

    private void actualizarCampos() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        };
        TextFormatter<Double> formatter = new TextFormatter<>(
            new DoubleStringConverter(), 0.0, filter);
        precioField.setTextFormatter(formatter);

        usuarioCB.getItems().clear();
        usuarioCB.getSelectionModel().clearSelection();
        usuarioCB.getItems().addAll(
            usuarioDao.obtenerTodosLosUsuarios()
                .stream()
                .map(Usuario::getNombre)
                .toList()
        );
    }

    @FXML
    public void regresar() throws IOException {
        App.setContent("inventario");
    }

    @FXML
    public void guardar() throws IOException {
        String guia = "G" + UUID.randomUUID().toString();
        if (guia.length() > 16) {
            guia = guia.substring(0, 16);
        }

        String usuario = usuarioCB.getSelectionModel().getSelectedItem();
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        String origen = origenField.getText();
        String destino = destinoField.getText();
        String precio = precioField.getText();
        if (usuario == null) {
            mostrarAlerta(
                "Formulario",
                "El usuario no puede quedar vacio",
                "Selecciona un usuario",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (nombre.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El nombre no puede quedar vacio",
                "Agrega un nombre",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (descripcion.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "La contraseña no puede quedar vacia",
                "Agrega una contraseña",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (origen.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El origen no puede quedar vacio",
                "Agrega un origen",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (destino.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El destino no puede quedar vacio",
                "Agrega un destino",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (precio.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El precio no puede quedar vacio",
                "Agrega un precio",
                Alert.AlertType.WARNING
            );
            return;
        }
        
        boolean agregado = paqueteriaDao.agregarPaquete(
            nombre,
            descripcion,
            Double.valueOf(precio),
            Estado.ESPERA,
            Ruta.PREPARANDO,
            guia,
            usuarioDao.obtenerUsuarioPorNombre(usuario).getId(),
            origen,
            destino
        );
        if (agregado) {
            mostrarAlerta(
                "Formulario",
                "El paquete se agrego correctamente.",
                "El paquete ha sido agregado al inventario del usuario " + usuario,
                Alert.AlertType.INFORMATION
            );
            usuarioCB.getSelectionModel().clearSelection();
            nombreField.clear();
            descripcionField.clear();
            origenField.clear();
            destinoField.clear();
            precioField.clear();
            App.setContent("inventario");
        } else {
            mostrarAlerta(
                "Formulario",
                "Error al agregar el paquete",
                "Intenta nuevamente",
                Alert.AlertType.ERROR
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
