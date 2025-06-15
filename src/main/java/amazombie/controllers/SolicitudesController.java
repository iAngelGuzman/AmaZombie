/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import amazombie.App;
import amazombie.dao.SolicitudDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Solicitud;
import amazombie.models.Usuario;
import amazombie.utils.GestorReporte;
import amazombie.utils.GestorSolicitud;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class SolicitudesController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox solicitudesContainer;
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    private final SolicitudDao solicitudDao = SolicitudDao.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void actualizarDatos() {
        solicitudesContainer.getChildren().clear();
        List<Solicitud> solicitudes = solicitudDao.obtenerSolicitudes();

        if (solicitudes.isEmpty()) {
            Label noSolicitudesLabel = new Label("No hay solicitudes de empleo registradas");
            noSolicitudesLabel.setFont(Font.font("Poetsen One", 14));
            noSolicitudesLabel.setAlignment(Pos.CENTER);
            noSolicitudesLabel.setPadding(new Insets(20));
            solicitudesContainer.getChildren().add(noSolicitudesLabel);
            return;
        }
        solicitudesContainer.getChildren().add(crearHeader(solicitudes.get(0)));

        for (Solicitud solicitud : solicitudes) {
            solicitudesContainer.getChildren().add(crearTarjeta(solicitud));
        }

    }

    public GridPane crearHeader(Solicitud solicitud) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.NEVER);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        // VBox con el nombre
        Label labelNombre = new Label("Usuario");
        labelNombre.setFont(Font.font("Poetsen One", 14));

        VBox vboxNombre = new VBox(labelNombre);
        vboxNombre.setAlignment(Pos.CENTER);
        vboxNombre.setPadding(new Insets(10, 20, 10, 20));
        vboxNombre.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxNombre.setMinWidth(160);

        Label labelRazon = new Label("Razón");
        labelRazon.setFont(Font.font("Poetsen One", 14));

        VBox vboxRazon = new VBox(labelRazon);
        vboxRazon.setAlignment(Pos.CENTER_LEFT);
        vboxRazon.setPadding(new Insets(10, 20, 10, 20));
        vboxRazon.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxRazon.setMinWidth(200);

        Label labelEstado = new Label("Estado");
        labelEstado.setFont(Font.font("Poetsen One", 14));

        VBox vboxEstado = new VBox(labelEstado);
        vboxEstado.setAlignment(Pos.CENTER);
        vboxEstado.setPadding(new Insets(10, 20, 10, 20));
        vboxEstado.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxEstado.setMinWidth(200);

        Label labelAcciones = new Label("Acciones");
        labelAcciones.setFont(Font.font("Poetsen One", 14));

        VBox vboxAcciones = new VBox(labelAcciones);
        vboxAcciones.setAlignment(Pos.CENTER);
        vboxAcciones.setPadding(new Insets(10, 20, 10, 20));
        vboxAcciones.setMinWidth(200);

        // Agregar al GridPane
        gridPane.add(vboxNombre, 0, 0);
        gridPane.add(vboxRazon, 1, 0);
        gridPane.add(vboxEstado, 2, 0);
        gridPane.add(vboxAcciones, 3, 0);

        return gridPane;
    }

    public GridPane crearTarjeta(Solicitud solicitud) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.NEVER);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        // VBox con el nombre
        Label labelNombre = new Label(usuarioDao.obtenerUsuario(solicitud.getUsuarioId()).getNombre());
        labelNombre.setFont(Font.font("Poetsen One", 14));

        VBox vboxNombre = new VBox(labelNombre);
        vboxNombre.setAlignment(Pos.CENTER);
        vboxNombre.setPadding(new Insets(10, 20, 10, 20));
        vboxNombre.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxNombre.setMinWidth(160);

        Label labelRazon = new Label(solicitud.getRazon());
        labelRazon.setFont(Font.font("Poetsen One", 14));

        VBox vboxRazon = new VBox(labelRazon);
        vboxRazon.setAlignment(Pos.CENTER_LEFT);
        vboxRazon.setPadding(new Insets(10, 20, 10, 20));
        vboxRazon.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxRazon.setMinWidth(200);

        Label labelEstado = new Label(solicitud.getEstado());
        labelEstado.setFont(Font.font("Poetsen One", 14));

        VBox vboxEstado = new VBox(labelEstado);
        vboxEstado.setAlignment(Pos.CENTER);
        vboxEstado.setPadding(new Insets(10, 20, 10, 20));
        vboxEstado.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxEstado.setMinWidth(200);

        // Detalles
        Button btnDetalles = new Button("Detalles");
        btnDetalles.setFont(Font.font("Poetsen One", 14));

        btnDetalles.setOnAction(e -> {
            GestorSolicitud.setSolicitudActual(solicitud);
            Stage stage = new Stage();
            stage.setOnCloseRequest(event -> {
                actualizarDatos();
            });
            try {
                stage.setScene(new Scene(App.loadFXML("solicitud")));
                stage.setTitle("Solicitud");
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SolicitudesController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al abrir la solicitud");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setFont(Font.font("Poetsen One", 14));

        btnEliminar.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Eliminar");
            alerta.setHeaderText("¿Está seguro de que desea eliminar la solicitud?");
            alerta.setContentText("Esta acción no se puede deshacer");

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean eliminado = solicitudDao.eliminarSolicitud(solicitud.getId());

                if (eliminado) {
                    mostrarAlerta(
                        "Éxito",
                        "Solicitud eliminada correctamente",
                        null,
                        Alert.AlertType.INFORMATION
                    );
                    actualizarDatos();
                } else {
                    mostrarAlerta(
                        "Error",
                        "No se pudo eliminar la solicitud",
                        null,
                        Alert.AlertType.ERROR
                    );
                }
            }
        });

        HBox hboxBotones = new HBox(8, btnDetalles, btnEliminar);
        hboxBotones.setAlignment(Pos.CENTER);
        hboxBotones.setPadding(new Insets(10));
        hboxBotones.setMinWidth(200);

        // Agregar al GridPane
        gridPane.add(vboxNombre, 0, 0);
        gridPane.add(vboxRazon, 1, 0);
        gridPane.add(vboxEstado, 2, 0);
        gridPane.add(hboxBotones, 3, 0);

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
