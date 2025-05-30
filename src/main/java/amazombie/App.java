package amazombie;

import amazombie.controllers.FXMLCache;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static VBox boxContent;
    private static ScrollPane scrollContent;

    @Override
    public void start(Stage stage) throws IOException {
        cargarFuentes();
        scene = new Scene(loadFXML("sesion"), 640, 480);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    public static void setBoxContent(VBox box) {
        boxContent = box;
    }
    
    public static void setScrollContent(ScrollPane scroll) {
        scrollContent = scroll;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setContent(String fxml) throws IOException {
        if (boxContent == null || scrollContent == null) {
            return;
        }
        
        Parent vista = FXMLCache.getView(fxml);

        boxContent.getChildren().clear();
        boxContent.getChildren().add(vista);

        if (vista instanceof Region region) {
            region.maxWidthProperty().bind(scrollContent.widthProperty());
            region.prefWidthProperty().bind(scrollContent.widthProperty());
            region.maxHeightProperty().bind(scrollContent.heightProperty());
            region.prefHeightProperty().bind(scrollContent.heightProperty());
        }

        // Llamar a actualizarDatos si el método "actualizarDatos()" existe
        Object controller = FXMLCache.getController(fxml);
        try {
            Method method = controller.getClass().getMethod("actualizarDatos");
            method.invoke(controller);
        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            // No hacer nada si el método no existe
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    static void cargarFuentes() {
        Font.loadFont(App.class.getResourceAsStream("/amazombie/fonts/OrdinaryNotes.ttf"), 12);
        Font.loadFont(App.class.getResourceAsStream("/amazombie/fonts/OrdinaryNotesThin.ttf"), 12);
        Font.loadFont(App.class.getResourceAsStream("/amazombie/fonts/Komika.ttf"), 12);
        Font.loadFont(App.class.getResourceAsStream("/amazombie/fonts/Grobold.ttf"), 12);
        Font.loadFont(App.class.getResourceAsStream("/amazombie/fonts/PoetsenOne.ttf"), 12);
    }
}