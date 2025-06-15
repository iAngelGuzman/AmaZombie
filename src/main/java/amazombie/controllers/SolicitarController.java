/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.SolicitudDao;
import amazombie.utils.GestorSesion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;


public class SolicitarController implements Initializable {

    /**
     * Initializes the controller class.
     */

    private final SolicitudDao solicitudDao = SolicitudDao.getInstancia();

    @FXML private TextArea razonArea;
    @FXML private HBox solicitudBox;
    @FXML private Button enviarBtn;
    @FXML private Button cancelarBtn;
    @FXML private Label infoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    public void actualizarDatos() {
        razonArea.setText("");
        
        if (solicitudDao.solicitudAceptada(GestorSesion.getUsuarioActual().getId())) {
            solicitudBox.setVisible(true);
            solicitudBox.setManaged(true);
            infoLabel.setText("Su solicitud ha sido aceptada. ¡Bienvenido al equipo!");
            infoLabel.setStyle("-fx-text-fill: green;");
            enviarBtn.setDisable(true);
            cancelarBtn.setVisible(false);
            cancelarBtn.setManaged(false);
        } else if (solicitudDao.solicitudRechazada(GestorSesion.getUsuarioActual().getId())) {
            solicitudBox.setVisible(true);
            solicitudBox.setManaged(true);
            infoLabel.setText("Su solicitud ha sido rechazada. Puede volver a intentarlo más tarde.");
            infoLabel.setStyle("-fx-text-fill: red;");
            enviarBtn.setDisable(false);
            cancelarBtn.setVisible(false);
            cancelarBtn.setManaged(false);
        } else {
            solicitudBox.setVisible(true);
            solicitudBox.setManaged(true);
            infoLabel.setText("Tienes una solicitud en proceso...");
            infoLabel.setStyle("-fx-text-fill: black;");
            enviarBtn.setDisable(true);
            cancelarBtn.setVisible(true);
            cancelarBtn.setManaged(true);
        }

        if (solicitudDao.usuarioEnvioSolicitud(GestorSesion.getUsuarioActual().getId())) {
            solicitudBox.setVisible(true);
            solicitudBox.setManaged(true);
            enviarBtn.setDisable(true);
        } else {
            solicitudBox.setVisible(false);
            solicitudBox.setManaged(false);
            enviarBtn.setDisable(false);
        }
    }
    
    @FXML
    private void enviarSolicitud() {
        if (solicitudDao.usuarioEnvioSolicitud(GestorSesion.getUsuarioActual().getId())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Solicitud ya enviada");
            alert.setContentText("Ya ha enviado una solicitud, espere a que sea atendida.");
            alert.showAndWait();
            return;
        }
        String razon = razonArea.getText().trim();
        if (razon.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Razon de solicitud vacia");
            alert.setContentText("Por favor, ingrese una razon para la solicitud.");
            alert.showAndWait();
            return;
        }
        boolean solicitado = solicitudDao.crearSolicitud(GestorSesion.getUsuarioActual().getId(), razon);
        if (!solicitado) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al enviar solicitud");
            alert.setContentText("No se pudo enviar la solicitud.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Solicitud enviada");
            alert.setHeaderText(null);
            alert.setContentText("Su solicitud ha sido enviada exitosamente.");
            alert.showAndWait();
            actualizarDatos();
        }
    }

    @FXML
    private void cancelarSolicitud() {
        boolean cancelada = solicitudDao.cancelarSolicitud(GestorSesion.getUsuarioActual().getId());
        if (!cancelada) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cancelar solicitud");
            alert.setContentText("No se pudo cancelar la solicitud.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Solicitud cancelada");
            alert.setHeaderText(null);
            alert.setContentText("Su solicitud ha sido cancelada exitosamente.");
            alert.showAndWait();
            actualizarDatos();
        }
    }
    
}
