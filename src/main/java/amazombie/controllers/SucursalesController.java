package amazombie.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class SucursalesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    // @FXML private WebView webView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        WebEngine webEngine = webView.getEngine();
//        URL map = getClass().getResource("/amazombie/data/mapa.html");
//
//        if (url != null) {
//            webEngine.load(map.toExternalForm());
//
//            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//                if (newState == Worker.State.SUCCEEDED) {
//                    // Ahora sí puedes acceder al objeto map
//                    webEngine.executeScript("map.setView([20.0, -99.0], 10);");
//                }
//            });
//        } else {
//            System.err.println("No se encontró el archivo map.html");
//        }
    }    
    
    @FXML
    public void abrirMapa(ActionEvent event) {
        try {
            // Obtener el recurso como URL
            URL recurso = getClass().getResource("/amazombie/data/mapa.html");

            if (recurso != null) {
                // Convertir a File
                File archivoHtml = new File(recurso.toURI());
                if (archivoHtml.exists()) {
                    Desktop.getDesktop().browse(archivoHtml.toURI());
                    System.out.println("HTML abierto en el navegador.");
                } else {
                    System.out.println("Archivo no encontrado.");
                }
            } else {
                System.out.println("Recurso HTML no encontrado.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
