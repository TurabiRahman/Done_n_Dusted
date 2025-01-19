package com.example.donendusted;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Done_n_Dusted extends Application {
    @Override
    public void start(Stage primaryStage)
    {


        try
        {

            // Load the first scene (openingScene.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("openingScene.fxml"));
            Scene scene = new Scene(loader.load());

            // Set minimum dimensions


            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(650);

            // Set up the stage and Navigator
            primaryStage.setScene(scene);
            primaryStage.setTitle("Done n Dusted");
            primaryStage.show();

            // Set up Navigator with the primary stage
            Navigator.setStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
