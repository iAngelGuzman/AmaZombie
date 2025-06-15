/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.dao.PaqueteriaDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Paquete;
import amazombie.utils.GestorPaquete;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author manac
 */
public class FacturaController implements Initializable {

    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();

    @FXML private Label fechaLabel;
    @FXML private Label clienteLabel;
    @FXML private Label origenLabel;
    @FXML private Label destinoLabel;
    @FXML private Label guiaLabel;
    @FXML private Label envioLabel;
    @FXML private Label ivaLabel;
    @FXML private Label totalLabel;

    private Paquete paquete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paquete = GestorPaquete.getPaqueteActual();
        if (paquete != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = paquete.getFecha().toLocalDate().format(formatter);
            fechaLabel.setText(fechaFormateada);
            clienteLabel.setText(usuarioDao.obtenerUsuario(paquete.getUsuarioId()).getNombre());
            origenLabel.setText(paquete.getOrigen());
            destinoLabel.setText(paquete.getDestino());
            guiaLabel.setText(paquete.getGuia());
            envioLabel.setText(String.format("$%.2f", paquete.getPrecio()));
            Double iva = paquete.getPrecio() * 0.16;
            ivaLabel.setText(String.format("$%.2f", iva));
            totalLabel.setText(String.format("$%.2f", paquete.getPrecio() + iva));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado un paquete");
            alert.showAndWait();
        }
    }

    @FXML
    public void descargarPDF() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Factura como PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
        File archivo = fileChooser.showSaveDialog((Stage) fechaLabel.getScene().getWindow());
        if (archivo == null) {
            return;
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(archivo.getAbsolutePath()));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Factura - AmaZombie", tituloFont));
            document.add(new Paragraph(" "));
            String fechaFormateada = paquete.getFecha().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            document.add(new Paragraph("Fecha: " + fechaFormateada, normalFont));
            document.add(new Paragraph("Cliente: " + usuarioDao.obtenerUsuario(paquete.getUsuarioId()).getNombre(), normalFont));
            document.add(new Paragraph("Origen: " + paquete.getOrigen(), normalFont));
            document.add(new Paragraph("Destino: " + paquete.getDestino(), normalFont));
            document.add(new Paragraph("Guía: " + paquete.getGuia(), normalFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Envío: $" + String.format("%.2f", paquete.getPrecio()), normalFont));
            document.add(new Paragraph("IVA: $" + String.format("%.2f", paquete.getPrecio() * 0.16), normalFont));
            document.add(new Paragraph("Total: $" + String.format("%.2f", paquete.getPrecio() + paquete.getPrecio() * 0.16), normalFont));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrar() throws IOException {
        Stage stage = (Stage) fechaLabel.getScene().getWindow();
        stage.close();
    }

}
