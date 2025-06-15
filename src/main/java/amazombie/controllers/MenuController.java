/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.App;
import amazombie.utils.GestorSesion;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private StackPane inicioSP;
    @FXML private StackPane usuariosSP;
    @FXML private StackPane empleadosSP;
    @FXML private StackPane inventarioSP;
    @FXML private StackPane sucursalesSP;
    @FXML private StackPane faqSP;
    @FXML private StackPane atencionSP;
    @FXML private StackPane reportesSP;
    @FXML private StackPane solicitudesSP;
    @FXML private StackPane solicitarSP;
    @FXML private StackPane perfilSP;
    @FXML private StackPane salirSP;

    @FXML private ImageView inicioIMG;
    @FXML private ImageView usuariosIMG;
    @FXML private ImageView empleadosIMG;
    @FXML private ImageView inventarioIMG;
    @FXML private ImageView sucursalesIMG;
    @FXML private ImageView faqIMG;
    @FXML private ImageView atencionIMG;
    @FXML private ImageView reportesIMG;
    @FXML private ImageView solicitudesIMG;
    @FXML private ImageView solicitarIMG;
    @FXML private ImageView perfilIMG;
    @FXML private ImageView salirIMG;

    @FXML private Label adminLabel;
    
    ColorAdjust itemActivo;
    ColorAdjust itemInactivo;
    ColorAdjust perfilActivo;
    ColorAdjust perfilInactivo;
    ColorAdjust salirActivo;
    ColorAdjust salirInactivo;
    ImageView botonSeleccionado;
    
    private final Map<String, ImageView> botonesMenu = new HashMap<>();
    
    @FXML private VBox boxContent;
    @FXML private ScrollPane scrollContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.setBoxContent(boxContent);
        App.setScrollContent(scrollContent);
        
        itemActivo = new ColorAdjust();
        itemActivo.setBrightness(-0.45);
        itemActivo.setContrast(0.4);
        itemActivo.setHue(0.5);
        itemActivo.setSaturation(-0.5);

        itemInactivo = new ColorAdjust();
        itemInactivo.setBrightness(-0.8);
        itemInactivo.setContrast(0.4);
        itemInactivo.setHue(0.5);
        itemInactivo.setSaturation(-0.5);
        
        perfilActivo = new ColorAdjust();
        perfilActivo.setBrightness(-0.45);
        perfilActivo.setContrast(0.4);
        perfilActivo.setHue(0.9);
        perfilActivo.setSaturation(-0.5);

        perfilInactivo = new ColorAdjust();
        perfilInactivo.setBrightness(-0.8);
        perfilInactivo.setContrast(0.4);
        perfilInactivo.setHue(0.9);
        perfilInactivo.setSaturation(-0.5);

        salirActivo = new ColorAdjust();
        salirActivo.setBrightness(-0.45);
        salirActivo.setContrast(0.4);
        salirActivo.setHue(-0.18);
        salirActivo.setSaturation(-0.5);

        salirInactivo = new ColorAdjust();
        salirInactivo.setBrightness(-0.8);
        salirInactivo.setContrast(0.4);
        salirInactivo.setHue(-0.18);
        salirInactivo.setSaturation(-0.5);
        
        botonesMenu.put("inicio", inicioIMG);
        botonesMenu.put("usuarios", usuariosIMG);
        botonesMenu.put("empleados", empleadosIMG);
        botonesMenu.put("inventario", inventarioIMG);
        botonesMenu.put("sucursales", sucursalesIMG);
        botonesMenu.put("faq", faqIMG);
        botonesMenu.put("atencion", atencionIMG);
        botonesMenu.put("reportes", reportesIMG);
        botonesMenu.put("solicitudes", solicitudesIMG);
        botonesMenu.put("solicitar", solicitarIMG);
        botonesMenu.put("perfil", perfilIMG);
        botonesMenu.put("salir", salirIMG);
        
        try {
            cargarContenido("inicio");
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        efectosBotones();

        if (!GestorSesion.getUsuarioActual().esAdmin()) {
            usuariosSP.setVisible(false);
            usuariosSP.setManaged(false);
            
            empleadosSP.setVisible(false);
            empleadosSP.setManaged(false);

            adminLabel.setVisible(false);
            adminLabel.setManaged(false);

            solicitudesSP.setVisible(false);
            solicitudesSP.setManaged(false);
        }
    }
    
    public void efectosBotones() {
        inicioSP.setOnMouseEntered((MouseEvent e) -> { inicioIMG.setEffect(itemActivo); });
        inicioSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != inicioIMG) { inicioIMG.setEffect(itemInactivo); } });
        usuariosSP.setOnMouseEntered((MouseEvent e) -> { usuariosIMG.setEffect(itemActivo); });
        usuariosSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != usuariosIMG) { usuariosIMG.setEffect(itemInactivo); }});
        empleadosSP.setOnMouseEntered((MouseEvent e) -> { empleadosIMG.setEffect(itemActivo); });
        empleadosSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != empleadosIMG) { empleadosIMG.setEffect(itemInactivo); } });
        inventarioSP.setOnMouseEntered((MouseEvent e) -> { inventarioIMG.setEffect(itemActivo); });
        inventarioSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != inventarioIMG) { inventarioIMG.setEffect(itemInactivo); } });
        sucursalesSP.setOnMouseEntered((MouseEvent e) -> { sucursalesIMG.setEffect(itemActivo); });
        sucursalesSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != sucursalesIMG) { sucursalesIMG.setEffect(itemInactivo); } });
        faqSP.setOnMouseEntered((MouseEvent e) -> { faqIMG.setEffect(itemActivo); });
        faqSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != faqIMG) { faqIMG.setEffect(itemInactivo); } });
        atencionSP.setOnMouseEntered((MouseEvent e) -> { atencionIMG.setEffect(itemActivo); });
        atencionSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != atencionIMG) { atencionIMG.setEffect(itemInactivo); } });
        reportesSP.setOnMouseEntered((MouseEvent e) -> { reportesIMG.setEffect(itemActivo); });
        reportesSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != reportesIMG) { reportesIMG.setEffect(itemInactivo); } });
        solicitudesSP.setOnMouseEntered((MouseEvent e) -> { solicitudesIMG.setEffect(itemActivo); });
        solicitudesSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != solicitudesIMG) { solicitudesIMG.setEffect(itemInactivo); } });
        solicitarSP.setOnMouseEntered((MouseEvent e) -> { solicitarIMG.setEffect(itemActivo); });
        solicitarSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != solicitarIMG) { solicitarIMG.setEffect(itemInactivo); } });
        perfilSP.setOnMouseEntered((MouseEvent e) -> { perfilIMG.setEffect(perfilActivo); });
        perfilSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != perfilIMG) { perfilIMG.setEffect(perfilInactivo); } });
        salirSP.setOnMouseEntered((MouseEvent e) -> { salirIMG.setEffect(salirActivo); });
        salirSP.setOnMouseExited((MouseEvent e) -> { if (botonSeleccionado != salirIMG) { salirIMG.setEffect(salirInactivo); } });
    }

    @FXML public void abrirInicio() throws IOException { cargarContenido("inicio"); }
    @FXML public void abrirUsuarios() throws IOException { cargarContenido("usuarios"); }
    @FXML public void abrirEmpleados() throws IOException { cargarContenido("empleados"); }
    @FXML public void abrirInventario() throws IOException { cargarContenido("inventario"); }
    @FXML public void abrirSucursales() throws IOException { cargarContenido("sucursales"); }
    @FXML public void abrirFaq() throws IOException { cargarContenido("faq"); }
    @FXML public void abrirAtencion() throws IOException { cargarContenido("atencion"); }
    @FXML public void abrirReportes() throws IOException { cargarContenido("reportes"); }
    @FXML public void abrirSolicitudes() throws IOException { cargarContenido("solicitudes"); }
    @FXML public void abrirSolicitar() throws IOException { cargarContenido("solicitar"); }
    @FXML public void abrirPerfil() throws IOException { cargarContenido("perfil"); }
    @FXML public void cerrarSesion() throws IOException { 
        GestorSesion.cerrarSesion();
        App.setRoot("sesion");
    }

    @FXML
    public void cargarContenido(String ventana) throws IOException {
        App.setContent(ventana);
        actualizarSeleccion(ventana);
    }
    
    public void actualizarSeleccion(String ventana) {
        for (ImageView sp : botonesMenu.values()) {
            if (sp == perfilIMG) {
                sp.setEffect(perfilInactivo);
            } else if (sp == salirIMG) {
                sp.setEffect(salirInactivo);
            } else {
                sp.setEffect(itemInactivo);
            }
        }

        // Aplicar al StackPane
        botonSeleccionado = botonesMenu.get(ventana);
        if (botonSeleccionado != null) {
            if (botonSeleccionado == perfilIMG) {
                botonSeleccionado.setEffect(perfilActivo);
            } else if (botonSeleccionado == salirIMG) {
                botonSeleccionado.setEffect(salirActivo);
            } else {
                botonSeleccionado.setEffect(itemActivo);
            }
        }
    }

    
}