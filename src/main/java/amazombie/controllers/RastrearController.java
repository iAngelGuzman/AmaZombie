/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import amazombie.App;
import amazombie.dao.PaqueteriaDao;
import amazombie.models.Paquete;
import amazombie.utils.Estado;
import amazombie.utils.GestorPaquete;
import amazombie.utils.GestorSesion;
import amazombie.utils.Ruta;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;


public class RastrearController implements Initializable {

    /**
     * Initializes the controller class.
     */

    private final PaqueteriaDao paqueteriaDao = PaqueteriaDao.getInstancia();

    private Paquete paquete;
    private Ruta rutaActual;

    @FXML
    private Button retrocederBtn, avanzarBtn;
    @FXML
    private ImageView zombieImage;
    @FXML
    private Label Mensaje1;
    @FXML
    private Label guiaLabel;

    @FXML
    private ImageView cerebro1, cerebro2, cerebro3, cerebro4, cerebro5;
    private List<ImageView> cerebros;

    @FXML
    private ImageView linea1, linea2, linea3, linea4, linea5;
    private List<ImageView> lineas;

    private List<String> rutas;

    @FXML
    private Button recompensa;

    private int paso = 0;

    private double posicionY = 0;

    private final String[] mensajes = {
            "Preparando paquete...",
            "Paquete listo para enviar",
            "Paquete en camino",
            "Paquete llegando al destino",
            "¡Paquete entregado!"
    };

    ColorAdjust gris;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gris = new ColorAdjust();
        gris.setSaturation(-1);
        gris.setBrightness(-0.2);
        gris.setContrast(-0.1);
        
        cerebros = Arrays.asList(cerebro1, cerebro2, cerebro3, cerebro4, cerebro5);
        lineas = Arrays.asList(linea1, linea2, linea3, linea4, linea5);
        rutas = Arrays.asList(Ruta.PREPARANDO, Ruta.LISTO, Ruta.CAMINO, Ruta.LLEGANDO, Ruta.ENTREGADO);

    }
    
    public void actualizarDatos() {
        if (!GestorSesion.getUsuarioActual().esAdmin()) {
            retrocederBtn.setVisible(false);
            retrocederBtn.setManaged(false);
            avanzarBtn.setVisible(false);
            avanzarBtn.setManaged(false);
        }
        
        paquete = GestorPaquete.getPaqueteActual();
        System.out.println("Actualizando paquete " + paquete.getId());

        lineas.forEach(linea -> linea.setOpacity(0));
        cerebros.forEach(cerebro -> cerebro.setEffect(null));
        paso = 0;
        posicionY = 0;

        switch (paquete.getRuta()) {
            case Ruta.PREPARANDO:
                paso = 0;
                posicionY = paso * 60;
                zombieImage.setTranslateY(posicionY);
                Mensaje1.setText(mensajes[paso]);
                lineas.get(paso).setOpacity(1);
                for (int i = 0; i < cerebros.size(); i++) {
                    ImageView cerebro = cerebros.get(i);
                    if (cerebro != null) {
                        if (i < paso) {
                            cerebro.setEffect(gris);
                        } else {
                            cerebro.setEffect(null);
                        }
                    }
                }
                break;
            case Ruta.LISTO:
                paso = 1;
                posicionY = paso * 60;
                zombieImage.setTranslateY(posicionY);
                Mensaje1.setText(mensajes[paso]);
                lineas.get(paso).setOpacity(1);
                for (int i = 0; i < cerebros.size(); i++) {
                    ImageView cerebro = cerebros.get(i);
                    if (cerebro != null) {
                        if (i < paso) {
                            cerebro.setEffect(gris);
                        } else {
                            cerebro.setEffect(null);
                        }
                    }
                }
                break;
            case Ruta.CAMINO:
                paso = 2;
                posicionY = paso * 60;
                zombieImage.setTranslateY(posicionY);
                Mensaje1.setText(mensajes[paso]);
                lineas.get(paso).setOpacity(1);
                for (int i = 0; i < cerebros.size(); i++) {
                    ImageView cerebro = cerebros.get(i);
                    if (cerebro != null) {
                        if (i < paso) {
                            cerebro.setEffect(gris);
                        } else {
                            cerebro.setEffect(null);
                        }
                    }
                }
                break;
            case Ruta.LLEGANDO:
                paso = 3;
                posicionY = paso * 60;
                zombieImage.setTranslateY(posicionY);
                Mensaje1.setText(mensajes[paso]);
                lineas.get(paso).setOpacity(1);
                for (int i = 0; i < cerebros.size(); i++) {
                    ImageView cerebro = cerebros.get(i);
                    if (cerebro != null) {
                        if (i < paso) {
                            cerebro.setEffect(gris);
                        } else {
                            cerebro.setEffect(null);
                        }
                    }
                }
                break;
            case Ruta.ENTREGADO:
                paso = 4;
                posicionY = paso * 60;
                zombieImage.setTranslateY(posicionY);
                Mensaje1.setText(mensajes[paso]);
                lineas.get(paso).setOpacity(1);
                for (int i = 0; i < cerebros.size(); i++) {
                    ImageView cerebro = cerebros.get(i);
                    if (cerebro != null) {
                        if (i < paso) {
                            cerebro.setEffect(gris);
                        } else {
                            cerebro.setEffect(null);
                        }
                    }
                }
                break;
            default:
                break;
        }

        guiaLabel.setText(paquete.getGuia());
    }

    @FXML
    private void copiarGuia() {
        String guia = paquete.getGuia();
        if (guia != null && !guia.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(guia);
            clipboard.setContent(content);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guía copiada al portapapeles");
            alert.setHeaderText(null);
            alert.setContentText("Guía copiada al portapapeles: " + guia);
            alert.showAndWait();
        }
    }

    @FXML
    private void regresar() throws IOException {
        App.setContent("inventario");
    }

    @FXML
    private void moverPaquete(int direccion) {
        avanzarBtn.setDisable(true);
        retrocederBtn.setDisable(true);

        int nuevoPaso = paso + direccion;
        if (nuevoPaso < 0 || nuevoPaso >= cerebros.size()) {
            avanzarBtn.setDisable(false);
            retrocederBtn.setDisable(false);
            return;
        }

        posicionY += direccion * 60;

        TranslateTransition transicion = new TranslateTransition(Duration.millis(300), zombieImage);
        transicion.setToY(posicionY);

        transicion.setOnFinished(event -> {
            paso = nuevoPaso;
            Mensaje1.setText(mensajes[paso]);
            lineas.forEach(linea -> linea.setOpacity(0));
            lineas.get(paso).setOpacity(1);
            // Actualizar efectos de cerebros
            for (int i = 0; i < cerebros.size(); i++) {
                ImageView cerebro = cerebros.get(i);
                if (cerebro != null) {
                    if (i < paso) {
                        cerebro.setEffect(gris);
                    } else {
                        cerebro.setEffect(null);
                    }
                }
            }

            avanzarBtn.setDisable(false);
            retrocederBtn.setDisable(false);

            paquete.setRuta(rutas.get(paso));
            if (paso > 1) {
                if (paso >= 4) {
                    paquete.setEstado(Estado.PROCESADO);
                } else {
                    paquete.setEstado(Estado.ENVIADO);
                }
            } else {
                paquete.setEstado(Estado.ESPERA);
            }
            
            paqueteriaDao.actualizarPaquete(paquete);
        });

        transicion.play();
    }

    @FXML
    private void avanzar() {
        moverPaquete(1);
    }

    @FXML
    private void retroceder() {
        moverPaquete(-1);
    }

}