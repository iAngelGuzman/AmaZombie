/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import amazombie.App;
import amazombie.dao.ReporteDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Reporte;
import amazombie.utils.GestorReporte;
import amazombie.utils.GestorSesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ReportesController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox reportesContainer;
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    private final ReporteDao reporteDao = ReporteDao.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void actualizarDatos() {
        reportesContainer.getChildren().clear();
        List<Reporte> reportes;
        if (GestorSesion.getUsuarioActual().esAdmin()) {
            reportes = reporteDao.obtenerReportes();
        } else {
            reportes = reporteDao.obtenerReportesPorUsuario(GestorSesion.getUsuarioActual().getId());
        }

        if (reportes.isEmpty()) {
            Label noReportesLabel = new Label("No hay reportes disponibles.");
            noReportesLabel.setFont(Font.font("Poetsen One", 14));
            noReportesLabel.setAlignment(Pos.CENTER);
            noReportesLabel.setPadding(new Insets(20));
            reportesContainer.getChildren().add(noReportesLabel);
            return;
        }
        reportesContainer.getChildren().add(crearHeader(reportes.get(0)));
        for (Reporte reporte : reportes) {
            reportesContainer.getChildren().add(crearTarjeta(reporte));
        }
    }

    public GridPane crearHeader(Reporte reporte) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        // usuario
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        // asunto
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);

        // estado
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        
        // respuesta
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);

        // fecha
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.NEVER);

        // acciones
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setHgrow(Priority.NEVER);

        if (GestorSesion.getUsuarioActual().esAdmin()) {
            gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6);
        } else {
            gridPane.getColumnConstraints().addAll(col2, col3, col4, col5, col6);
        }

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        Label usuarioNombre = new Label("Usuario");
        usuarioNombre.setFont(Font.font("Poetsen One", 14));
        VBox vboxUsuario = new VBox(usuarioNombre);
        vboxUsuario.setAlignment(Pos.CENTER);
        vboxUsuario.setPadding(new Insets(10, 8, 10, 8));
        vboxUsuario.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxUsuario.setMinWidth(120);

        Label asunto = new Label("Asunto");
        asunto.setFont(Font.font("Poetsen One", 14));
        VBox vboxAsunto = new VBox(asunto);
        vboxAsunto.setAlignment(Pos.CENTER);
        vboxAsunto.setPadding(new Insets(10, 20, 10, 20));
        vboxAsunto.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxAsunto.setMinWidth(140);

        Label estado = new Label("Estado");
        estado.setFont(Font.font("Poetsen One", 14));
        VBox vboxEstado = new VBox(estado);
        vboxEstado.setAlignment(Pos.CENTER);
        vboxEstado.setPadding(new Insets(10, 8, 10, 8));
        vboxEstado.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxEstado.setMinWidth(100);
        vboxEstado.setMaxWidth(200);

        Label respuesta = new Label("Respuesta");
        respuesta.setFont(Font.font("Poetsen One", 14));
        VBox vboxRespuesta = new VBox(respuesta);
        vboxRespuesta.setAlignment(Pos.CENTER_LEFT);
        vboxRespuesta.setPadding(new Insets(10, 20, 10, 20));
        vboxRespuesta.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxRespuesta.setMinWidth(140);

        Label fecha = new Label("Fecha");
        fecha.setFont(Font.font("Poetsen One", 14));
        VBox vboxFecha = new VBox(fecha);
        vboxFecha.setAlignment(Pos.CENTER);
        vboxFecha.setPadding(new Insets(10, 8, 10, 8));
        vboxFecha.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxFecha.setMinWidth(100);

        Label labelAcciones = new Label("Acciones");
        labelAcciones.setFont(Font.font("Poetsen One", 14));

        VBox vboxAcciones = new VBox(labelAcciones);
        vboxAcciones.setAlignment(Pos.CENTER);
        vboxAcciones.setPadding(new Insets(10, 20, 10, 20));
        vboxAcciones.setMinWidth(175);

        // Agregar al GridPane
        if (GestorSesion.getUsuarioActual().esAdmin()) {
            gridPane.add(vboxUsuario, 0, 0);
            gridPane.add(vboxAsunto, 1, 0);
            gridPane.add(vboxEstado, 2, 0);
            gridPane.add(vboxRespuesta, 3, 0);
            gridPane.add(vboxFecha, 4, 0);
            gridPane.add(vboxAcciones, 5, 0);
        } else {
            gridPane.add(vboxAsunto, 0, 0);
            gridPane.add(vboxEstado, 1, 0);
            gridPane.add(vboxRespuesta, 2, 0);
            gridPane.add(vboxFecha, 3, 0);
            gridPane.add(vboxAcciones, 4, 0);
        }

        return gridPane;
    }

    public GridPane crearTarjeta(Reporte reporte) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        // usuario
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        // asunto
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);

        // estado
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        
        // respuesta
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);

        // fecha
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.NEVER);

        // acciones
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setHgrow(Priority.NEVER);

        if (GestorSesion.getUsuarioActual().esAdmin()) {
            gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6);
        } else {
            gridPane.getColumnConstraints().addAll(col2, col3, col4, col5, col6);
        }

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        Label usuarioNombre = new Label(usuarioDao.obtenerUsuario(reporte.getUsuarioId()).getNombre());
        usuarioNombre.setFont(Font.font("Poetsen One", 14));
        VBox vboxUsuario = new VBox(usuarioNombre);
        vboxUsuario.setAlignment(Pos.CENTER);
        vboxUsuario.setPadding(new Insets(10, 8, 10, 8));
        vboxUsuario.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxUsuario.setMinWidth(120);

        Label asunto = new Label(reporte.getAsunto());
        asunto.setFont(Font.font("Poetsen One", 14));
        VBox vboxAsunto = new VBox(asunto);
        vboxAsunto.setAlignment(Pos.CENTER);
        vboxAsunto.setPadding(new Insets(10, 20, 10, 20));
        vboxAsunto.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxAsunto.setMinWidth(140);

        Label estado = new Label(reporte.getEstado());
        estado.setFont(Font.font("Poetsen One", 14));
        VBox vboxEstado = new VBox(estado);
        vboxEstado.setAlignment(Pos.CENTER);
        vboxEstado.setPadding(new Insets(10, 8, 10, 8));
        vboxEstado.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxEstado.setMinWidth(100);
        vboxEstado.setMaxWidth(200);

        Label respuesta = new Label(reporte.getRespuesta());
        respuesta.setFont(Font.font("Poetsen One", 14));
        VBox vboxRespuesta = new VBox(respuesta);
        vboxRespuesta.setAlignment(Pos.CENTER_LEFT);
        vboxRespuesta.setPadding(new Insets(10, 20, 10, 20));
        vboxRespuesta.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxRespuesta.setMinWidth(140);

        String fechaFormateada = reporte.getFecha().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Label fecha = new Label(fechaFormateada);
        fecha.setFont(Font.font("Poetsen One", 14));
        VBox vboxFecha = new VBox(fecha);
        vboxFecha.setAlignment(Pos.CENTER);
        vboxFecha.setPadding(new Insets(10, 8, 10, 8));
        vboxFecha.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxFecha.setMinWidth(100);

        // HBox con los botones
        Button btnDetalles = new Button("Detalles");
        btnDetalles.setFont(Font.font("Poetsen One", 14));
        btnDetalles.setCursor(Cursor.HAND);

        // Detalles
        btnDetalles.setOnAction(e -> {
            GestorReporte.setReporteActual(reporte);
            Stage stage = new Stage();
            stage.setOnCloseRequest(event -> {
                actualizarDatos();
            });
            try {
                stage.setScene(new Scene(App.loadFXML("reporte")));
                stage.setTitle("Reporte");
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al abrir el reporte");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        // Eiminar
        Button eliminarBtn = new Button("Eliminar");
        eliminarBtn.setFont(Font.font("Poetsen One", 14));
        eliminarBtn.setCursor(Cursor.HAND);

        btnDetalles.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar reporte");
            alert.setHeaderText("¿Está seguro de que desea eliminar el reporte?");
            alert.setContentText("Esta acción no se puede deshacer");

            ButtonType buttonTypeSi = new ButtonType("Sí, aceptar");
            ButtonType buttonTypeNo = new ButtonType("No, cancelar");
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeSi) {
                boolean eliminado = reporteDao.eliminarReporte(reporte.getId());
                if (eliminado) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Reporte eliminado");
                    alert2.setHeaderText("Reporte eliminado");
                    alert2.setContentText("El reporte ha sido eliminado");
                    alert2.showAndWait();
                    actualizarDatos();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText("Error al eliminar el reporte");
                    alert2.setContentText("No se pudo eliminar el reporte");
                    alert2.showAndWait();
                }
            }
        });

        HBox hboxBotones = new HBox(8, btnDetalles, eliminarBtn);
        hboxBotones.setAlignment(Pos.CENTER);
        hboxBotones.setPadding(new Insets(10));
        hboxBotones.setMinWidth(175);

        // Agregar al GridPane
        if (GestorSesion.getUsuarioActual().esAdmin()) {
            gridPane.add(vboxUsuario, 0, 0);
            gridPane.add(vboxAsunto, 1, 0);
            gridPane.add(vboxEstado, 2, 0);
            gridPane.add(vboxRespuesta, 3, 0);
            gridPane.add(vboxFecha, 4, 0);
            gridPane.add(hboxBotones, 5, 0);
        } else {
            gridPane.add(vboxAsunto, 0, 0);
            gridPane.add(vboxEstado, 1, 0);
            gridPane.add(vboxRespuesta, 2, 0);
            gridPane.add(vboxFecha, 3, 0);
            gridPane.add(hboxBotones, 4, 0);
        }

        return gridPane;
    }

    public static void mostrarAlerta(String titulo, String mensaje, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}
