/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author manac
 */
public class CreacionCuentaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField Tx_UserNuevo;
    @FXML
    private TextField Tx_PsswdNueva;
    @FXML
    private TextField Tx_PsswdNueva_Repetir;

    private boolean esContrasenaValida(String contrasena) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return contrasena.matches(regex);
    }

    @FXML
    private void registrarUsuario() throws IOException {
        String usuario = Tx_UserNuevo.getText();
        String contrasena = Tx_PsswdNueva.getText();
        String contrasenaRepetir = Tx_PsswdNueva_Repetir.getText();

        if (!esContrasenaValida(contrasena)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validación de la contraseña");
            alert.setHeaderText("Contraseña insegura");
            alert.setContentText("La contraseña debe tener al menos 8 caracteres. Incluir mayúsculas, numeros y un caracter especial");
            alert.showAndWait();
            return;
        }

        if (!contrasena.equals(contrasenaRepetir)) {
            System.out.println("⚠️ Las contraseñas no coinciden. Intenta nuevamente.");
            return;
        }
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";
        String insert = "INSERT INTO usuarios(usuario,contraseña) VALUES (?,?)";

//        try (java.sql.Connection conn = DB_Conec.conectar();
//                PreparedStatement pstmtsql = conn.prepareStatement(sql);
//                PreparedStatement pstmtInsert = conn.prepareStatement(insert)){
//                
//                pstmtsql.setString(1,usuario);
//                 ResultSet rs = pstmtsql.executeQuery();
//                 
//                 if(rs.next()){
//                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Error al Cerrar Sesión");
//                    alert.setHeaderText("El usuario no está disponible");
//                    alert.setContentText("Intente uno diferente");
//                    alert.showAndWait();
//                     return;
//                 }
//                 
//                 pstmtInsert.setString(1,usuario);
//                 pstmtInsert.setString(2,contrasena);
//                 int filasAfect = pstmtInsert.executeUpdate();
//                 
//                 if(filasAfect > 0){
//                      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Creación de cuenta");
//                    alert.setHeaderText("Cuenta creada exitosamente");
//                    alert.showAndWait();
//                    CS01.setRoot("FXMLDocument");
//                     return;
//                 }
//        } catch(SQLException e){
//                e.printStackTrace();
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Error al guardarrdar la información con el servidor");
//                    alert.setHeaderText("Error al guardar la información con el servidor");
//                    alert.setContentText("Por favor inténtelo más tarde");
//                    alert.showAndWait();
//                     return;
//        }
//        return;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
