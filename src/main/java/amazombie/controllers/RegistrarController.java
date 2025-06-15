package amazombie.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import amazombie.App;
import amazombie.dao.UsuarioDao;

import java.io.IOException;
import java.sql.SQLException;

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



public class RegistrarController {

    private UsuarioDao usuarioDao = new UsuarioDao();
    
    @FXML private TextField usuarioField;
    @FXML private PasswordField contrasenaField;
    @FXML private TextField contrasenaText;
    @FXML private PasswordField confirmarContraField;
    @FXML private TextField confirmarContraText;
    @FXML private ImageView passEyeImage;
    @FXML private ImageView passEyeImage2;
    @FXML private Button sesionBtn;
    @FXML private Label createLabel;
    @FXML private GridPane gridPane;
    @FXML private ImageView imageView;
    
    private boolean contrasenaVisible = false;
    private boolean confirmarContrasenaVisible = false;
    
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
        if (confirmarContrasenaVisible) {
            hacerConfirmarContrasenaVisible();
        } else {
            hacerConfirmarContrasenaInvisible();
        }
    }
    
    @FXML
    public void iniciarSesion() throws IOException {
        App.setRoot("sesion");
    }
    
    @FXML
    private void crearCuenta() throws IOException, SQLException {
        String nombre = usuarioField.getText();
        String contrasena = contrasenaField.getText();
        String confirmarContrasena = confirmarContraField.getText();
        String rol = "usuario";

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "Los datos no pueden quedar vacios",
                "Agregar el usuario, contraseña y confirmación de contraseña",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (nombre.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "El nombre de usuario no puede quedar vacio",
                "Agrega un nombre de usuario",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (contrasena.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "La contraseña no puede quedar vacia",
                "Agrega una contraseña",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (confirmarContrasena.isEmpty()) {
            mostrarAlerta(
                "Formulario",
                "La confirmación de contraseña no puede quedar vacia",
                "Agrega una confirmación de contraseña",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (usuarioDao.existeUsuario(nombre)) {
            mostrarAlerta(
                "Formulario",
                "El nombre de usuario ya existe",
                "Ingresa un nombre de usuario diferente",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (!confirmarContrasena.equals(contrasena)) {
            mostrarAlerta(
                "Formulario",
                "Las contraseñas no coinciden",
                "Ingresa las mismas contraseñas",
                Alert.AlertType.WARNING
            );
            return;
        }
        if (usuarioDao.agregarUsuario(nombre, contrasena, rol)) {
            usuarioField.setText("");
            contrasenaText.setText("");
            contrasenaField.setText("");
            confirmarContraText.setText("");
            confirmarContraField.setText("");
            mostrarAlerta("Formulario", "Usuario creado", "El usuario ha sido creado correctamente", Alert.AlertType.INFORMATION);
            App.setRoot("sesion");
        } else {
            mostrarAlerta("Formulario", "Error al crear usuario", "El usuario no ha sido creado correctamente", Alert.AlertType.ERROR);
        }
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
                    if (confirmarContrasenaVisible) {
                        confirmarContraText.requestFocus();
                    } else {
                        confirmarContraField.requestFocus();
                    }
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
        
        confirmarContraField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    passEyeImage.requestFocus();
                } else {
                    passEyeImage2.requestFocus(); // Avanza al botón
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
        
        confirmarContraText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    passEyeImage.requestFocus(); // Regresa al usuario
                } else {
                    passEyeImage2.requestFocus(); // Avanza al botón
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
        
        passEyeImage2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    if (contrasenaVisible) {
                        confirmarContraText.requestFocus();
                    } else {
                        confirmarContraField.requestFocus();
                    }
                } else {
                    sesionBtn.requestFocus();
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
                    try {
                        crearCuenta();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
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
    
    private void hacerConfirmarContrasenaVisible() {
        confirmarContraField.setManaged(false);
        confirmarContraField.setVisible(false);
        confirmarContraText.setManaged(true);
        confirmarContraText.setVisible(true);
        passEyeImage.setImage(new Image(ojoAbiertoImagenRuta));
    }
    
    private void hacerConfirmarContrasenaInvisible() {
        confirmarContraField.setManaged(true);
        confirmarContraField.setVisible(true);
        confirmarContraText.setManaged(false);
        confirmarContraText.setVisible(false);
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

    @FXML
    private void cambiarVisibilidadConfirmarContrasena() {
        if (confirmarContrasenaVisible) {
            hacerConfirmarContrasenaInvisible();
            confirmarContrasenaVisible = false;
        } else {
            hacerConfirmarContrasenaVisible();
            confirmarContrasenaVisible = true;
        }
    }

    public static void mostrarAlerta(String titulo, String mensaje, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
