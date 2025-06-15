/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.App;
import amazombie.dao.ConexionDB;
import amazombie.dao.FaqDao;
import amazombie.dao.PaqueteriaDao;
import amazombie.dao.ReporteDao;
import amazombie.models.Faq;
import amazombie.models.Paquete;
import amazombie.models.Usuario;
import amazombie.utils.Estado;
import amazombie.utils.GestorSesion;
import amazombie.utils.Ruta;

import java.io.IOException;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author manac
 */
public class AtencionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private final PaqueteriaDao paqueteriaDao = PaqueteriaDao.getInstancia();
    private final ReporteDao reporteDao = ReporteDao.getInstancia();

    @FXML private ScrollPane scrollpanel;
    @FXML private VBox faqContainer;
    @FXML private TextField guiaField;

    @FXML private Label guiaLabel;
    @FXML private Label nombreLabel;
    @FXML private Label descripcionLabel;
    @FXML private Label origenLabel;
    @FXML private Label destinoLabel;
    @FXML private Label estadoLabel;
    @FXML private Label rutaLabel;
    @FXML private Label precioLabel;

    @FXML private TextField asuntoField;
    @FXML private TextArea descripcionArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    
    @FXML
    public void buscarGuia() {
        String guia = guiaField.getText().trim();
        if (guia.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese una guía para buscar.");
            alert.showAndWait();
            return;
        }
        Paquete paquete = paqueteriaDao.obtenerPaquetePorGuia(guia);
        if (paquete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Paquete no encontrado");
            alert.setHeaderText(null);
            alert.setContentText("No se encontró un paquete con la guía proporcionada.");
            alert.showAndWait();
            return;
        }
        String estadoString = "";
        switch (paquete.getEstado()) {
            case Estado.ESPERA:
                estadoString = "En espera";
                break;
            case Estado.ENVIADO:
                estadoString = "Enviado";
                break;
            case Estado.PROCESADO:
                estadoString = "Procesado";
                break;
            case Estado.ENTERRADO:
                estadoString = "Enterrado";
                break;
            default:
                estadoString = "Desconocido";
        }
        String rutaString = "";
        switch (paquete.getRuta()) {
            case Ruta.PREPARANDO:
                rutaString = "Preparando paquete...";
                break;
            case Ruta.LISTO:
                rutaString = "Paquete listo para enviar";
                break;
            case Ruta.CAMINO:
                rutaString = "Paquete en camino";
                break;
            case Ruta.LLEGANDO:
                rutaString = "Paquete llegando al destino";
                break;
            case Ruta.ENTREGADO:
                rutaString = "¡Paquete entregado!";
                break;
            default:
                rutaString = "Desconocido";
        }
        guiaLabel.setText(paquete.getGuia());
        nombreLabel.setText(paquete.getNombre());
        descripcionLabel.setText(paquete.getDescripcion());
        origenLabel.setText(paquete.getOrigen());
        destinoLabel.setText(paquete.getDestino());
        estadoLabel.setText(estadoString);
        rutaLabel.setText(rutaString);
        precioLabel.setText("$" + String.format("%.2f", paquete.getPrecio()) + " MXN");
    }

    @FXML
    public void abrirPuntos() throws IOException {
        App.setContent("perfil");
    }
    
    @FXML
    public void enviarReporte() {
        String asunto = asuntoField.getText();
        String descripcion = descripcionArea.getText();
        if (asunto.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese un asunto.");
            alert.showAndWait();
            return;
        }
        if (descripcion.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese una descripción.");
            alert.showAndWait();
            return;
        }
        boolean reporteCreado = reporteDao.crearReporte(GestorSesion.getUsuarioActual().getId(), asunto, descripcion);
        if (reporteCreado) {
            asuntoField.setText("");
            descripcionArea.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte enviado");
            alert.setHeaderText(null);
            alert.setContentText("El reporte ha sido enviado con éxito.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al enviar reporte");
            alert.setHeaderText(null);
            alert.setContentText("Error al enviar el reporte.");
            alert.showAndWait();
        }
    }
    
}
