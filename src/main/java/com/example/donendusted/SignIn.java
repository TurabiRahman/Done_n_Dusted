package com.example.donendusted;



import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SignIn {
    private Scene scene;

    public SignIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignIn.fxml"));

        scene = new Scene(fxmlLoader.load(), 400, 600);
    }

    public Scene getScene() {
        return scene;
    }
}


