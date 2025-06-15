/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.PaqueteriaDao;
import amazombie.dao.ReporteDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Paquete;
import amazombie.models.Reporte;
import amazombie.utils.GestorPaquete;
import amazombie.utils.GestorReporte;
import amazombie.utils.GestorSesion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author manac
 */
public class ReporteController implements Initializable {

    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    private final ReporteDao reporteDao = ReporteDao.getInstancia();

    @FXML private TextField usuarioField;
    @FXML private TextField asuntoField;
    @FXML private TextArea descripcionArea;
    @FXML private TextField fechaField;
    @FXML private TextArea respuestaArea;

    @FXML private HBox usuarioBox;
    @FXML private Button responderBtn;

    private Reporte reporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reporte = GestorReporte.getReporteActual();
        if (reporte != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = reporte.getFecha().toLocalDate().format(formatter);
            fechaField.setText(fechaFormateada);
            usuarioField.setText(usuarioDao.obtenerUsuario(reporte.getUsuarioId()).getNombre());
            asuntoField.setText(reporte.getAsunto());
            descripcionArea.setText(reporte.getDescripcion());
            respuestaArea.setText(reporte.getRespuesta());

            if (GestorSesion.getUsuarioActual().esAdmin()) {
                usuarioBox.setVisible(true);
                usuarioBox.setManaged(true);
                responderBtn.setVisible(true);
                responderBtn.setManaged(true);
                respuestaArea.setEditable(true);
                respuestaArea.setPromptText("Escribe tu respuesta aquí...");
            } else {
                usuarioBox.setVisible(false);
                usuarioBox.setManaged(false);
                responderBtn.setVisible(false);
                responderBtn.setManaged(false);
                respuestaArea.setEditable(false);
                respuestaArea.setPromptText("No tienes permiso para responder este reporte");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado un reporte");
            alert.showAndWait();
        }
    }

    @FXML
    public void cerrar() throws IOException {
        Stage stage = (Stage) asuntoField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void responder() throws IOException {
        boolean respondido = reporteDao.actualizarReporte(reporte.getId(), respuestaArea.getText(), "resuelto");
        if (!respondido) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al responder");
            alert.setHeaderText("No se pudo responder el reporte");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte respondido");
            alert.setHeaderText("El reporte ha sido respondido");
            alert.showAndWait();
        }
    }

}
