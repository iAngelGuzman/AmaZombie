/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class UsuariosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private WebView webView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine webEngine = webView.getEngine();
        URL map = getClass().getResource("/amazombie/data/mapa.html");

        if (url != null) {
            webEngine.load(map.toExternalForm());

            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    // Ahora sí puedes acceder al objeto map
                    webEngine.executeScript("map.setView([20.0, -99.0], 10);");
                }
            });
        } else {
            System.err.println("No se encontró el archivo map.html");
        }
    }    
}
