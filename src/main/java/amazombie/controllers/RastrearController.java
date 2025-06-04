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
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class RastrearController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private Button Paquetes;
    @FXML private Label Mensaje1;

    @FXML private ImageView cerebro1, cerebro2, cerebro3, cerebro4, cerebro5;
    private List<ImageView> cerebros;

    @FXML private ImageView linea1, linea2, linea3, linea4, linea5;
    private List<ImageView> lineas;
    
    @FXML private Button recompensa;

    private int paso = 1;
    private double posicionY = 0;

    private final String[] mensajes = {
        "Paquete listo para enviar",
        "Paquete en camino",
        "Paquete llegando al destino",
        "¡Paquete entregado!"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cerebros = Arrays.asList(cerebro1, cerebro2, cerebro3, cerebro4, cerebro5);
        lineas = Arrays.asList(linea1, linea2, linea3, linea4, linea5);

        // Validar si alguno está null
        for (int i = 0; i < cerebros.size(); i++) {
            if (cerebros.get(i) == null) {
                System.err.println("cerebro" + (i + 1) + " está null. Revisa el fx:id en el FXML.");
            }
        }
        for (int j = 1; j < lineas.size(); j++) {
            if (lineas.get(j) == null) {
                System.err.println("linea" + (j + 1) + " está null. Revisa el fx:id en el FXML.");
            } else {
                lineas.get(j).setOpacity(0); // Ocultar todas las líneas al inicio
            }
                for (int i = 0; i < cerebros.size(); i++) {
              final int index = i;
              ImageView cerebro = cerebros.get(i);

              if (cerebro != null) {
                  cerebro.setOnMouseClicked(event -> {
                      if (index < paso - 1) {
                          Mensaje1.setText(mensajes[index]);
                          Mensaje1.setStyle("-fx-text-fill: gray;");
                          recompensa.setVisible(true); // mostrar botón solo para cerebros anteriores
                      } else {
                          recompensa.setVisible(false); // ocultar si se hace clic en un cerebro futuro o actual
                      }
                  });
              }
          }

        recompensa.setVisible(false); // ocultar al inicio

        }
        
        // Posición inicial (paso 1 equivale a Y=60)
        posicionY = 60;
        Paquetes.setTranslateY(posicionY);

        Mensaje1.setText("Preparando paquete...");

        for (int i = 0; i < lineas.size(); i++) {
            lineas.get(i).setOpacity(0);
        }
        if (!lineas.isEmpty()) {
            lineas.get(0).setOpacity(1);
        }

        // Primer cerebro sin gris, el resto tampoco
        for (ImageView cerebro : cerebros) {
            cerebro.setEffect(null);
        }
    }

    @FXML
    private void regresar() throws IOException {
        App.setContent("inventario");
    }
    
    @FXML
    private void moverPaquete(int direccion) {
        Paquetes.setDisable(true);

        int nuevoPaso = paso + direccion;

        if (nuevoPaso < 1 || nuevoPaso > cerebros.size()) {
            Paquetes.setDisable(false);
            return;
        }

        posicionY += direccion * 60;

        TranslateTransition transicion = new TranslateTransition(Duration.millis(300), Paquetes);
        transicion.setToY(posicionY);

        transicion.setOnFinished(event -> {
            paso = nuevoPaso;

            // Mostrar mensaje
            if (paso == 1) {
                Mensaje1.setText("Preparando paquete...");
            } else if (paso > 1 && paso - 2 < mensajes.length) {
                Mensaje1.setText(mensajes[paso - 2]);
            } else {
                Mensaje1.setText("");
            }

            // Mostrar solo la línea actual
            for (ImageView linea : lineas) {
                linea.setOpacity(0);
            }
            if (paso - 1 >= 0 && paso - 1 < lineas.size()) {
                lineas.get(paso - 1).setOpacity(1);
            }

            // Actualizar efectos de cerebros
            for (int i = 0; i < cerebros.size(); i++) {
                ImageView cerebro = cerebros.get(i);
                if (cerebro != null) {
                    if (i < paso - 1) {
                        // Cerebros anteriores: aplicar gris
                        ColorAdjust gris = new ColorAdjust();
                        gris.setSaturation(-1);
                        gris.setBrightness(-0.2);
                        gris.setContrast(-0.1);
                        cerebro.setEffect(gris);
                    } else {
                        // Cerebro actual y siguientes: sin efecto
                        cerebro.setEffect(null);
                    }
                }
            }

            Paquetes.setDisable(false);
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