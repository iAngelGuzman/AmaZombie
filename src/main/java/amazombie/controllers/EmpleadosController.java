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

import amazombie.App;
import amazombie.dao.UsuarioDao;
import amazombie.models.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class EmpleadosController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML private VBox empleadosContainer;
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void abrirAgregar() throws IOException {
        App.setContent("empleadosAgregar");
    }

    public void actualizarDatos() {
        empleadosContainer.getChildren().clear();
        List<Usuario> empleados = usuarioDao.obtenerEmpleados();

        if (empleados.isEmpty()) {
            Label noEmpleadosLabel = new Label("No hay empleados registrados");
            noEmpleadosLabel.setFont(Font.font("Poetsen One", 14));
            noEmpleadosLabel.setAlignment(Pos.CENTER);
            noEmpleadosLabel.setPadding(new Insets(20));
            empleadosContainer.getChildren().add(noEmpleadosLabel);
            return;
        }
        empleadosContainer.getChildren().add(crearHeader(empleados.get(0)));

        for (Usuario empleado : empleados) {
            empleadosContainer.getChildren().add(crearTarjeta(empleado));
        }

    }

    public GridPane crearHeader(Usuario empleado) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        Label id = new Label(String.valueOf("Id"));
        id.setFont(Font.font("Poetsen One", 14));

        VBox vboxId = new VBox(id);
        vboxId.setAlignment(Pos.CENTER);
        vboxId.setPadding(new Insets(10, 20, 10, 20));
        vboxId.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxId.setMinWidth(80);

        // VBox con el nombre
        Label labelNombre = new Label("Usuario");
        labelNombre.setFont(Font.font("Poetsen One", 14));

        VBox vboxNombre = new VBox(labelNombre);
        vboxNombre.setAlignment(Pos.CENTER);
        vboxNombre.setPadding(new Insets(10, 20, 10, 20));
        vboxNombre.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxNombre.setMinWidth(200);

        Label labelAcciones = new Label("Acciones");
        labelAcciones.setFont(Font.font("Poetsen One", 14));

        VBox vboxAcciones = new VBox(labelAcciones);
        vboxAcciones.setAlignment(Pos.CENTER);
        vboxAcciones.setPadding(new Insets(10, 20, 10, 20));

        // Agregar al GridPane
        gridPane.add(vboxId, 0, 0);
        gridPane.add(vboxNombre, 1, 0);
        gridPane.add(vboxAcciones, 2, 0);

        return gridPane;
    }

    public GridPane crearTarjeta(Usuario empleado) {
        // Crear el GridPane
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
        // margenes
        VBox.setMargin(gridPane, new Insets(0, 0, 5, 0));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(row);

        Label id = new Label(String.valueOf(empleado.getId()));
        id.setFont(Font.font("Poetsen One", 14));

        VBox vboxId = new VBox(id);
        vboxId.setAlignment(Pos.CENTER);
        vboxId.setPadding(new Insets(10, 20, 10, 20));
        vboxId.setStyle("-fx-border-color: grey; -fx-border-width: 0 1 0 0;");
        vboxId.setMinWidth(80);

        // VBox con el nombre
        Label labelNombre = new Label(empleado.getNombre());
        labelNombre.setFont(Font.font("Poetsen One", 14));

        VBox vboxNombre = new VBox(labelNombre);
        vboxNombre.setAlignment(Pos.CENTER);
        vboxNombre.setPadding(new Insets(10, 20, 10, 20));
        vboxNombre.setMinWidth(200);

        // HBox con los botones
        Button btnEditar = new Button("Editar");
        btnEditar.setFont(Font.font("Poetsen One", 14));
        HBox.setMargin(btnEditar, new Insets(0, 5, 0, 0));

        // Editar
        btnEditar.setOnAction(e -> {
            usuarioDao.setIdUsuarioAEditar(empleado.getId());
            try {
                App.setContent("empleadosEditar");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setFont(Font.font("Poetsen One", 14));

        // Eliminar
        btnEliminar.setOnAction(e -> {
            Usuario usr = usuarioDao.obtenerUsuario(empleado.getId());

            if (usr != null) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Eliminar");
                alerta.setHeaderText("¿Está seguro de que desea eliminar a " + usr.getNombre() + "?");
                alerta.setContentText("Esta acción no se puede deshacer");

                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    boolean eliminado = usuarioDao.eliminarUsuario(usr.getId());

                    if (eliminado) {
                        mostrarAlerta(
                            "Éxito",
                            "Empleado eliminado correctamente",
                            null,
                            Alert.AlertType.INFORMATION
                        );
                        actualizarDatos();
                    } else {
                        mostrarAlerta(
                            "Error",
                            "No se pudo eliminar el empleado",
                            "Verifica la conexión o intenta nuevamente",
                            Alert.AlertType.ERROR
                        );
                    }
                }

            } else {
                mostrarAlerta(
                    "Eliminar",
                    "No se pudo encontrar el empleado",
                    "Por favor, inténtelo de nuevo",
                    Alert.AlertType.WARNING
                );
            }
        });


        HBox hboxBotones = new HBox(10, btnEditar, btnEliminar);
        hboxBotones.setAlignment(Pos.CENTER_RIGHT);
        hboxBotones.setPadding(new Insets(10));
        hboxBotones.setStyle("-fx-border-color: grey; -fx-border-width: 0 0 0 1;");

        // Agregar al GridPane
        gridPane.add(vboxId, 0, 0);
        gridPane.add(vboxNombre, 1, 0);
        gridPane.add(hboxBotones, 2, 0);

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
