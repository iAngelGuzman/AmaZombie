/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.PaqueteriaDao;
import amazombie.dao.ReporteDao;
import amazombie.dao.SolicitudDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Paquete;
import amazombie.models.Reporte;
import amazombie.models.Solicitud;
import amazombie.utils.GestorPaquete;
import amazombie.utils.GestorReporte;
import amazombie.utils.GestorSesion;
import amazombie.utils.GestorSolicitud;

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
public class SolicitudController implements Initializable {

    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    private final SolicitudDao solicitudDao = SolicitudDao.getInstancia();

    @FXML private TextField usuarioField;
    @FXML private TextArea razonArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Solicitud solicitud = GestorSolicitud.getSolicitudActual();
        if (solicitud != null) {
            usuarioField.setText(usuarioDao.obtenerUsuario(solicitud.getUsuarioId()).getNombre());
            razonArea.setText(solicitud.getRazon());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la solicitud");
            alert.showAndWait();
        }
    }

    @FXML
    public void aceptarSolicitud() throws IOException {
        Solicitud solicitud = GestorSolicitud.getSolicitudActual();
        if (solicitud == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la solicitud");
            alert.showAndWait();
            return;
        }

        boolean aceptada = solicitudDao.aceptarSolicitud(solicitud.getId());
        if (!aceptada) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al aceptar");
            alert.setHeaderText("No se pudo aceptar la solicitud");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Solicitud aceptada");
            alert.setHeaderText("La solicitud ha sido aceptada");
            alert.showAndWait();
            cerrar();
        }
    }

    @FXML
    public void rechazarSolicitud() throws IOException {
        Solicitud solicitud = GestorSolicitud.getSolicitudActual();
        if (solicitud == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la solicitud");
            alert.showAndWait();
            return;
        }

        boolean rechazada = solicitudDao.rechazarSolicitud(solicitud.getId());
        if (!rechazada) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al rechazar");
            alert.setHeaderText("No se pudo rechazar la solicitud");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Solicitud rechazada");
            alert.setHeaderText("La solicitud ha sido rechazada");
            alert.showAndWait();
            cerrar();
        }
    }

    @FXML
    public void cerrar() throws IOException {
        Stage stage = (Stage) usuarioField.getScene().getWindow();
        stage.close();
    }
}
