/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package amazombie.controllers;

import amazombie.App;
import amazombie.dao.PaqueteriaDao;
import amazombie.dao.UsuarioDao;
import amazombie.models.Paquete;
import amazombie.utils.Estado;
import amazombie.utils.GestorPaquete;
import amazombie.utils.GestorSesion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;

public class InventarioController implements Initializable {

    /**
     * Initializes the controller class.
     */

    private final PaqueteriaDao paqueteriaDao = PaqueteriaDao.getInstancia();
    private final UsuarioDao usuarioDao = UsuarioDao.getInstancia();
    private List<Paquete> paquetes;

    @FXML
    private VBox todosContainer;
    @FXML
    private VBox enEsperaContainer;
    @FXML
    private VBox enviadosContainer;
    @FXML
    private VBox procesadosContainer;
    @FXML
    private VBox enterradosContainer;

    @FXML
    private Button agregarBtn;

    VBox enEsperaNuevoContainer = new VBox();
    VBox enviadosNuevoContainer = new VBox();
    VBox procesadosNuevoContainer = new VBox();
    VBox enterradosNuevoContainer = new VBox();

    String defaultImagePath = getClass().getResource("/amazombie/images/itemDefault.png").toExternalForm();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void abrirAgregar() throws IOException {
        App.setContent("inventarioAgregar");
    }

    public void actualizarDatos() {
        if (!GestorSesion.getUsuarioActual().esAdmin()) {
            agregarBtn.setVisible(false);
            agregarBtn.setManaged(false);
        }
        paquetes = paqueteriaDao.obtenerPaquetes();
        todosContainer.getChildren().clear();
        enEsperaContainer.getChildren().clear();
        enviadosContainer.getChildren().clear();
        procesadosContainer.getChildren().clear();
        enterradosContainer.getChildren().clear();

        enEsperaNuevoContainer.getChildren().clear();
        enviadosNuevoContainer.getChildren().clear();
        procesadosNuevoContainer.getChildren().clear();
        enterradosNuevoContainer.getChildren().clear();

        for (Paquete paquete : paquetes) {
            if (!GestorSesion.getUsuarioActual().esAdmin()) {
                if (paquete.getUsuarioId() != GestorSesion.getUsuarioActual().getId()) {
                    continue;
                }
            }
            switch (paquete.getEstado().toLowerCase()) {
                case Estado.ESPERA -> {
                    enEsperaContainer.getChildren().add(crearPaquete(paquete));
                    enEsperaNuevoContainer.getChildren().add(crearPaquete(paquete));
                }
                case Estado.ENVIADO -> {
                    enviadosContainer.getChildren().add(crearPaquete(paquete));
                    enviadosNuevoContainer.getChildren().add(crearPaquete(paquete));
                }
                case Estado.PROCESADO -> {
                    procesadosContainer.getChildren().add(crearPaquete(paquete));
                    procesadosNuevoContainer.getChildren().add(crearPaquete(paquete));
                }
                case Estado.ENTERRADO -> {
                    enterradosContainer.getChildren().add(crearPaquete(paquete));
                    enterradosNuevoContainer.getChildren().add(crearPaquete(paquete));
                }
            }
        }

        crearTodos();

        if (enEsperaContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes en espera.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(10, 0, 0, 0));
            enEsperaContainer.getChildren().add(label);
        }
        if (enviadosContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes enviados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill:rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(10, 0, 0, 0));
            enviadosContainer.getChildren().add(label);
        }
        if (procesadosContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes procesados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(10, 0, 0, 0));
            procesadosContainer.getChildren().add(label);
        }
        if (enterradosContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes enterrados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(10, 0, 0, 0));
            enterradosContainer.getChildren().add(label);
        }

    }

    public void crearTodos() {
        todosContainer.getChildren().clear();

        Label enEsperaLabel = new Label("En Espera");
        enEsperaLabel.setFont(new Font("Poetsen One", 16));
        VBox.setMargin(enEsperaLabel, new Insets(5, 0, 5, 0));
        todosContainer.getChildren().addAll(enEsperaLabel, enEsperaNuevoContainer);

        if (enEsperaNuevoContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes en espera.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(5, 0, 20, 0));
            todosContainer.getChildren().add(label);
        }

        Label enviadosLabel = new Label("Enviados");
        enviadosLabel.setFont(new Font("Poetsen One", 16));
        VBox.setMargin(enviadosLabel, new Insets(0, 0, 5, 0));
        todosContainer.getChildren().addAll(enviadosLabel, enviadosNuevoContainer);

        if (enviadosNuevoContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes enviados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(5, 0, 20, 0));
            todosContainer.getChildren().add(label);
        }

        Label procesadosLabel = new Label("Procesados");
        procesadosLabel.setFont(new Font("Poetsen One", 16));
        VBox.setMargin(procesadosLabel, new Insets(5, 0, 5, 0));
        todosContainer.getChildren().addAll(procesadosLabel, procesadosNuevoContainer);

        if (procesadosNuevoContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes procesados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(5, 0, 20, 0));
            todosContainer.getChildren().add(label);
        }

        Label enterradosLabel = new Label("Enterrados");
        enterradosLabel.setFont(new Font("Poetsen One", 16));
        VBox.setMargin(enterradosLabel, new Insets(5, 0, 5, 0));
        todosContainer.getChildren().addAll(enterradosLabel, enterradosNuevoContainer);

        if (enterradosNuevoContainer.getChildren().isEmpty()) {
            Label label = new Label("No hay paquetes enterrados.");
            label.setFont(new Font("Poetsen One", 16));
            label.setStyle("-fx-text-fill: rgb(150, 150, 150);");
            VBox.setMargin(label, new Insets(5, 0, 20, 0));
            todosContainer.getChildren().add(label);
        }
    }

    public GridPane crearPaquete(Paquete paquete) {
        // GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(8);
        gridPane.setStyle("-fx-border-radius: 5; -fx-border-color: grey; -fx-border-width: 1;");
        gridPane.setPadding(new Insets(8));
        VBox.setMargin(gridPane, new Insets(0, 0, 8, 0));

        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setFillWidth(false);
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.SOMETIMES);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);

        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        gridPane.getRowConstraints().add(new RowConstraints());

        // ImageView
        Image imageMinitura = new Image(defaultImagePath);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setImage(imageMinitura);
        gridPane.add(imageView, 0, 0);

        // VBox column 1 (Product Info)
        VBox productInfoVBox = new VBox();
        Label nameLabel = new Label(paquete.getNombre());
        nameLabel.setFont(new Font("Poetsen One", 14));
        nameLabel.setStyle("-fx-text-fill: rgb(16, 16, 16);");
        VBox.setMargin(nameLabel, new Insets(0, 0, 4, 0));

        Label descriptionLabel = new Label(paquete.getDescripcion());
        descriptionLabel.setFont(new Font("Poetsen One", 14));
        descriptionLabel.setStyle("-fx-text-fill: rgb(48, 48, 48);");
        VBox.setMargin(descriptionLabel, new Insets(0, 0, 4, 0));

        Label origenDestinoLabel = new Label(paquete.getOrigen() + " -> " + paquete.getDestino());
        origenDestinoLabel.setFont(new Font("Poetsen One", 14));
        origenDestinoLabel.setStyle("-fx-text-fill: rgb(48, 48, 48);");
        VBox.setMargin(origenDestinoLabel, new Insets(0, 0, 4, 0));

        Label priceLabel = new Label("$" + String.format("%.2f", paquete.getPrecio()) + " MXN");
        priceLabel.setFont(new Font("Poetsen One", 14));
        priceLabel.setStyle("-fx-text-fill: rgb(0, 112, 13);");

        if (GestorSesion.getUsuarioActual().esAdmin()) {
            Label usuarioLabel = new Label("Usuario: " + usuarioDao.obtenerUsuario(paquete.getUsuarioId()).getNombre());
            usuarioLabel.setFont(new Font("Poetsen One", 14));
            usuarioLabel.setStyle("-fx-text-fill: rgb(0, 99, 229);");
            VBox.setMargin(usuarioLabel, new Insets(0, 0, 5, 0));
            productInfoVBox.getChildren().add(usuarioLabel);
        }
        productInfoVBox.getChildren().addAll(nameLabel, descriptionLabel, origenDestinoLabel, priceLabel);
        gridPane.add(productInfoVBox, 1, 0);

        // Acciones
        VBox actionVBox = new VBox();
        actionVBox.setAlignment(Pos.CENTER);

        Button editarButton = new Button("Editar");
        editarButton.setFont(new Font("Poetsen One", 14));
        editarButton.setPrefWidth(110);
        editarButton.setCursor(Cursor.HAND);
        editarButton.setPadding(new Insets(6));
        VBox.setMargin(editarButton, new Insets(0, 0, 6, 0));

        Button rastrearButton = new Button("Rastrear");
        rastrearButton.setFont(new Font("Poetsen One", 14));
        rastrearButton.setPrefWidth(110);
        rastrearButton.setCursor(Cursor.HAND);
        rastrearButton.setPadding(new Insets(6));
        VBox.setMargin(rastrearButton, new Insets(0, 0, 6, 0));

        Button facturaButton = new Button("Facturar");
        facturaButton.setFont(new Font("Poetsen One", 14));
        facturaButton.setPrefWidth(110);
        facturaButton.setCursor(Cursor.HAND);
        facturaButton.setPadding(new Insets(6));
        VBox.setMargin(facturaButton, new Insets(0, 0, 6, 0));

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setFont(new Font("Poetsen One", 14));
        eliminarButton.setPrefWidth(110);
        eliminarButton.setCursor(Cursor.HAND);
        eliminarButton.setPadding(new Insets(6));

        editarButton.setOnAction(e -> {
            try {
                GestorPaquete.setPaqueteActual(paquete);
                App.setContent("inventarioEditar");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        rastrearButton.setOnAction(e -> {
            try {
                GestorPaquete.setPaqueteActual(paquete);
                App.setContent("rastrear");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        facturaButton.setOnAction(e -> {
            GestorPaquete.setPaqueteActual(paquete);
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(App.loadFXML("factura")));
                stage.setTitle("Factura de Paquete");
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al abrir la factura");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        eliminarButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar paquete");
            alert.setHeaderText("¿Está seguro de que desea eliminar el paquete?");
            alert.setContentText("Esta acción no se puede deshacer");

            ButtonType buttonTypeSi = new ButtonType("Sí, aceptar");
            ButtonType buttonTypeNo = new ButtonType("No, cancelar");
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeSi) {
                boolean eliminado = paqueteriaDao.eliminarPaquete(paquete.getId());
                if (eliminado) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Paquete eliminado");
                    alert2.setHeaderText("Paquete eliminado");
                    alert2.setContentText("El paquete ha sido eliminado");
                    alert2.showAndWait();
                    actualizarDatos();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText("Error al eliminar el paquete");
                    alert2.setContentText("No se pudo eliminar el paquete");
                    alert2.showAndWait();
                }
            }
        });

        // Button confirmarButton = new Button("Confirmar");
        // confirmarButton.setFont(new Font("Poetsen One", 14));
        // confirmarButton.setPrefWidth(100);
        // confirmarButton.setCursor(Cursor.HAND);
        // confirmarButton.setPadding(new Insets(5));

        actionVBox.getChildren().addAll(editarButton, rastrearButton, facturaButton);

        if (GestorSesion.getUsuarioActual().esAdmin()) {
            actionVBox.getChildren().add(eliminarButton);
        }

        gridPane.add(actionVBox, 2, 0);

        // Assemble VBox
        // rootVBox.getChildren().addAll(enviadosLabel, gridPane);

        return gridPane;
    }

    @FXML
    public void rastrear() throws IOException {
        App.setContent("perfil");
    }

    @FXML
    public void regresar() throws IOException {
        App.setRoot("menu");
    }

    private Image cargarImagen(byte[] imagenUrl) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imagenUrl);
            Image image = new Image(byteArrayInputStream); // Intentar cargar la imagen con "background loading"

            // Si la imagen no se carga correctamente, usar la de resources
            if (image.isError()) {
                return new Image(defaultImagePath);
            }
            return image;
        } catch (Exception e) {
            return new Image(defaultImagePath); // En caso de error, usar la imagen por defecto
        }
    }
}
