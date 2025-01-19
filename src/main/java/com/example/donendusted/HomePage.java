package com.example.donendusted;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class HomePage {
    private Scene scene;

    public HomePage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));

        try {
            // Load the scene and get the controller
            scene = new Scene(loader.load(), 900,650);
            HomepageController controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
