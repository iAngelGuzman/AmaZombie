/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import amazombie.App;
import amazombie.dao.PaqueteriaDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Paquete;
import amazombie.utils.GestorPaquete;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;


public class InventarioEditarController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox usuariosContainer;
    private final PaqueteriaDao paqueteriaDao = PaqueteriaDao.getInstancia();
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    
    @FXML private TextField nombreField;
    @FXML private TextField descripcionField;
    @FXML private TextField origenField;
    @FXML private TextField destinoField;
    @FXML private TextField precioField;

    Paquete paquete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paquete = GestorPaquete.getPaqueteActual();
        if (paquete != null) {
            nombreField.setText(paquete.getNombre());
            descripcionField.setText(paquete.getDescripcion());
            origenField.setText(paquete.getOrigen());
            destinoField.setText(paquete.getDestino());
            precioField.setText(String.format("%.2f", paquete.getPrecio()));
        } else {
            mostrarAlerta(
                "Error",
                "No se ha seleccionado un paquete",
                "Por favor, selecciona un paquete para editar.",
                Alert.AlertType.ERROR
            );
            return;
        }
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
    }

    @FXML
    public void regresar() throws IOException {
        App.setContent("inventario");
    }

    @FXML
    public void guardar() throws IOException {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        String origen = origenField.getText();
        String destino = destinoField.getText();
        String precio = precioField.getText();
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

        paquete.setNombre(nombre);
        paquete.setDescripcion(descripcion);
        paquete.setOrigen(origen);
        paquete.setDestino(destino);
        paquete.setPrecio(Double.parseDouble(precio));
        
        boolean actualizado = paqueteriaDao.actualizarPaquete(paquete);

        if (actualizado) {
            mostrarAlerta(
                "Formulario",
                "Paquete actualizado correctamente",
                "El paquete ha sido actualizado exitosamente.",
                Alert.AlertType.INFORMATION
            );
            App.setContent("inventario");
        } else {
            mostrarAlerta(
                "Formulario", 
                "Error al actualizar el paquete",
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
