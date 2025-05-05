/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class InventarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
//    public void confirmarEntrega(int id) {
//        Paqueteria paqueteria = new PaqueteriaDao();
//        if (!paqueteria.existePaquete(id)) {
//            return;
//        }
//        paqueteria.confirmarEntrega(id);
//    }
//    
//    public void rastrearPaquete(int id) {
//        Paqueteria paqueteria = new PaqueteriaDao();
//        paqueteria.setIdRastreo(id);
//        App.setRoot("rastreo");
//    }
    
    @FXML public void regresar() throws IOException { App.setRoot("menu"); }
}
