/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.App;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private Button inicioBtn;
    @FXML private Button usuariosBtn;
    @FXML private Button empleadosBtn;
    @FXML private Button inventarioBtn;
    @FXML private Button sucursalesBtn;
    @FXML private Button faqBtn;
    @FXML private Button perfilBtn;
    
    private final Map<String, Button> botonesMenu = new HashMap<>();
    
    @FXML private VBox boxContent;
    @FXML private ScrollPane scrollContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.setBoxContent(boxContent);
        App.setScrollContent(scrollContent);
        
        botonesMenu.put("inicio", inicioBtn);
        botonesMenu.put("usuarios", usuariosBtn);
        botonesMenu.put("empleados", empleadosBtn);
        botonesMenu.put("inventario", inventarioBtn);
        botonesMenu.put("sucursales", sucursalesBtn);
        botonesMenu.put("faq", faqBtn);
        botonesMenu.put("perfil", perfilBtn);
        
        try {
            cargarContenido("inicio");
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML public void abrirInicio() throws IOException { cargarContenido("inicio"); }
    @FXML public void abrirUsuarios() throws IOException { cargarContenido("usuarios"); }
    @FXML public void abrirEmpleados() throws IOException { cargarContenido("empleados"); }
    @FXML public void abrirInventario() throws IOException { cargarContenido("inventario"); }
    @FXML public void abrirSucursales() throws IOException { cargarContenido("sucursales"); }
    @FXML public void abrirFaq() throws IOException { cargarContenido("faq"); }
    @FXML public void abrirPerfil() throws IOException { cargarContenido("perfil"); }
    @FXML public void cerrarSesion() throws IOException { App.setRoot("sesion"); }

    @FXML
    public void cargarContenido(String ventana) throws IOException {
        App.setContent(ventana);
        actualizarSeleccion(ventana);
    }
    
    public void actualizarSeleccion(String ventana) {
        for (Button btn : botonesMenu.values()) {
            btn.getStyleClass().remove("active");
        }
        Button botonSeleccionado = botonesMenu.get(ventana);
        if (botonSeleccionado != null) {
            botonSeleccionado.getStyleClass().add("active");
        }
    }


}