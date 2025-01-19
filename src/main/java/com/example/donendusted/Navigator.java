package com.example.donendusted;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator
{
    private static Stage stage;

    public static void setStage(Stage stage) {
        Navigator.stage = stage;
    }


    public static void setScene(Scene scene)
    {
        if (stage != null) {
            stage.setScene(scene);
            stage.show();
        }
    }


    public static void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + fxmlFile);
        }
    }

    public static Stage getStage() {
        return stage;
    }
}
