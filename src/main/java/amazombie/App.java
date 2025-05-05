package amazombie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.text.Font;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        cargarFuentes();
        scene = new Scene(loadFXML("sesion"), 640, 480);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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