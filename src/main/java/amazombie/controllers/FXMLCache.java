package amazombie.controllers;

import amazombie.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FXMLCache {

    private static final Map<String, FXMLLoader> cache = new HashMap<>();

    private static FXMLLoader getLoader(String nombre) throws IOException {
        if (!cache.containsKey(nombre)) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/" + nombre + ".fxml"));
            loader.load();
            cache.put(nombre, loader);
        }
        return cache.get(nombre);
    }

    public static Parent getView(String nombre) throws IOException {
        return getLoader(nombre).getRoot();
    }

    public static <T> T getController(String nombre) throws IOException {
        return getLoader(nombre).getController();
    }
}

