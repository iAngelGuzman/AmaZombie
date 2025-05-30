/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.ConexionDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author manac
 */
public class FaqController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private ScrollPane scrollpanel;
    @FXML private VBox faqContainer;

    @FXML
    public void cargarFAQ() throws SQLException{
        String sql = "SELECT pregunta, respuesta FROM FAQ ORDER BY id ASC";
        faqContainer.getChildren().clear();

         try (java.sql.Connection conn = ConexionDB.conectar();
                PreparedStatement pstmtsql = conn.prepareStatement(sql);
                 ResultSet rs = pstmtsql.executeQuery()){
                 
                 if (!rs.isBeforeFirst()) { // Esto verifica si hay alguna fila en el resultado
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Preguntas Y Respuestas");
                    alert.setHeaderText("Sin información disponible");
                    alert.setContentText("Inténtelo más tarde, mientras se trabaja en más FAQ");
                    alert.showAndWait();
                    return; // Sale del método
                 }
             
                 while (rs.next()) {
                    String pregunta = rs.getString("pregunta");
                    String respuesta = rs.getString("respuesta");

                    Label preguntaLabel = new Label("❓ " + pregunta);
                    preguntaLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 25px;");
                    Label respuestaLabel = new Label("💡 " + respuesta);
                   respuestaLabel.setStyle("-fx-padding: 0 0 10 10; -fx-font-size: 23px;");
                    Separator separador = new Separator();
                    faqContainer.getChildren().addAll(preguntaLabel, respuestaLabel, separador);
                 }

         }catch(SQLException e){
             e.printStackTrace();
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setTitle("Error al Cargar FAQ");
              alert.setHeaderText("Error al cargar la información desde la base de datos");
              alert.setContentText("Por favor, inténtelo más tarde");
              alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    try {
        cargarFAQ();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
}
