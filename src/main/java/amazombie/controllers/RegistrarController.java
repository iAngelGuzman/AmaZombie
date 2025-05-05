package amazombie.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import amazombie.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


/**
 * FXML Controller class
 *
 * @author JoseANG3L
 */
public class RegistrarController {
    
    @FXML private TextField usuarioField;
    @FXML private PasswordField contrasenaField;
    @FXML private TextField contrasenaText;
    @FXML private ImageView passEyeImage;
    @FXML private Button sesionBtn;
    @FXML private Label createLabel;
    @FXML private GridPane gridPane;
    @FXML private ImageView imageView;
    
    private boolean contrasenaVisible = false;
    
    String ojoAbiertoImagenRuta = getClass().getResource("/amazombie/images/eye-open.png").toExternalForm();
    String ojoCerradoImagenRuta = getClass().getResource("/amazombie/images/eye-close.png").toExternalForm();
    String bgImagenRuta = getClass().getResource("/amazombie/images/bg.png").toExternalForm();
    
    @FXML
    public void initialize() {
        configurarImagen();
        configurarTabs();
        
        // MusicManager.get().play("Intro");
        
        // configuracion inicial
        if (contrasenaVisible) {
            hacerContrasenaVisible();
        } else {
            hacerContrasenaInvisible();
        }
    }
    
    @FXML
    public void iniciarSesion() throws IOException {
        App.setRoot("sesion");
    }
    
    @FXML
    private void crearCuenta() throws IOException {
        App.setRoot("registrar");
    }
    
    public void configurarImagen() {
        imageView.setImage(new Image(bgImagenRuta));
        imageView.fitWidthProperty().bind(gridPane.widthProperty().multiply(0.5)); // 70% del ancho
        imageView.fitHeightProperty().bind(gridPane.heightProperty());
    }
    
    public void configurarTabs() {
        contrasenaField.textProperty().bindBidirectional(contrasenaText.textProperty());
        usuarioField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.TAB) {
                if (e.isShiftDown()) {
                    createLabel.requestFocus();
                } else {
                    if (contrasenaVisible) {
                        contrasenaText.requestFocus();
                    } else {
                        contrasenaField.requestFocus();
                    }
                }
                e.consume();
            } else if (e.getCode() == KeyCode.ENTER) {
                try {
                    iniciarSesion();
                    e.consume();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        contrasenaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    usuarioField.requestFocus(); // Regresa al usuario
                } else {
                    passEyeImage.requestFocus(); // Avanza al botón
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                try {
                    iniciarSesion();
                    event.consume();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        contrasenaText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    usuarioField.requestFocus(); // Regresa al usuario
                } else {
                    passEyeImage.requestFocus(); // Avanza al botón
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                try {
                    iniciarSesion();
                    event.consume();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        passEyeImage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    if (contrasenaVisible) {
                        contrasenaText.requestFocus();
                    } else {
                        contrasenaField.requestFocus();
                    }
                } else {
                    sesionBtn.requestFocus(); // Avanza al botón
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                try {
                    iniciarSesion();
                    event.consume();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        sesionBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    passEyeImage.requestFocus();
                } else {
                    createLabel.requestFocus();
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                try {
                    iniciarSesion();
                    event.consume();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        createLabel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    sesionBtn.requestFocus();
                } else {
                    usuarioField.requestFocus();
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                try {
                    crearCuenta();
                } catch (IOException ex) {
                    Logger.getLogger(SesionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                event.consume();
            }
        });
    }
    
    private void hacerContrasenaVisible() {
        contrasenaField.setManaged(false);
        contrasenaField.setVisible(false);
        contrasenaText.setManaged(true);
        contrasenaText.setVisible(true);
        passEyeImage.setImage(new Image(ojoAbiertoImagenRuta));
    }
    
    private void hacerContrasenaInvisible() {
        contrasenaField.setManaged(true);
        contrasenaField.setVisible(true);
        contrasenaText.setManaged(false);
        contrasenaText.setVisible(false);
        passEyeImage.setImage(new Image(ojoCerradoImagenRuta));
    }
    
    @FXML
    private void cambiarVisibilidadContrasena() {
        if (contrasenaVisible) {
            hacerContrasenaInvisible();
            contrasenaVisible = false;
        } else {
            hacerContrasenaVisible();
            contrasenaVisible = true;
        }
    }
    
    public static boolean datosInvalidos() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Formulario");
        alert.setHeaderText("Los datos ingresados son invalidos.");
        alert.setContentText("Ingresa el usuario y contraseña correctos.");

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
    
    public static boolean usuarioVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Formulario");
        alert.setHeaderText("El nombre de usuario no puede quedar vacio.");
        alert.setContentText("Agrega un nombre de usuario.");

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
    
    public static boolean contrasenaVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Formulario");
        alert.setHeaderText("La contraseña no puede quedar vacia.");
        alert.setContentText("Agrega una contraseña.");

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
    
    public static boolean datosVacio() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Formulario");
        alert.setHeaderText("Los datos no pueden quedar vacios.");
        alert.setContentText("Agregar el usuario y contraseña.");

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
    
}
