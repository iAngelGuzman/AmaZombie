/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.ConexionDB;
import amazombie.dao.FaqDao;
import amazombie.models.Faq;
import amazombie.models.Usuario;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    private FaqDao faqDao = new FaqDao();

    public void actualizarDatos() {
        faqContainer.getChildren().clear();
        List<Faq> faqs = faqDao.obtenerFaqs();

        for (Faq faq : faqs) {
            faqContainer.getChildren().add(crearFaqBox(faq));
        }

    }

    public static VBox crearFaqBox(Faq faq) {
        // VBox principal
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-color: grey; -fx-background-color: white; -fx-border-radius: 10;");
        vbox.setPadding(new Insets(10));
        VBox.setMargin(vbox, new Insets(5)); // margen superior externo
        //vbox.setPrefHeight(Region.USE_COMPUTED_SIZE); // o simplemente
        //vbox.setMaxHeight(Double.MAX_VALUE);


        // Label de título
        Label titulo = new Label("❓ " + faq.getPregunta());
        titulo.setTextFill(javafx.scene.paint.Color.web("#006f06"));
        titulo.setFont(Font.font("Poetsen One", 20));

        // TextArea con el mensaje
        TextArea texto = new TextArea("💡 " + faq.getRespuesta());
        texto.setEditable(false);
        texto.setWrapText(true);
        texto.setFont(Font.font("Poetsen One", 14));
        texto.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
        VBox.setMargin(texto, new Insets(5, 0, 0, 0)); // margen superior interno
        //VBox.setVgrow(texto, Priority.ALWAYS);

        // Calcular número de líneas estimadas (puedes refinar esto si quieres precisión real)
        int numLineas = texto.getText().split("\n").length + 1;
        double altoPorLinea = 20.0; // Aproximadamente para fuente de 14pt
        texto.setPrefHeight(numLineas * altoPorLinea);


        VBox.setVgrow(texto, Priority.ALWAYS);
        texto.setMaxHeight(Double.MAX_VALUE);

        // Agregar al VBox
        vbox.getChildren().addAll(titulo, texto);
        return vbox;
    }

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
