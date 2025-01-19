package com.example.donendusted;


import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SignUp
{
    private Scene scene;

    public SignUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        scene = new Scene(fxmlLoader.load(), 400, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
