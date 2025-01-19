package com.example.donendusted;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Opening_Scene_Controller
{

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Login button clicked!");
        // Logic to switch to the login scene
        Navigator.loadScene("SignIn.fxml", "Done n Dusted");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        System.out.println("Register button clicked!");
        // Logic to switch to the register scene
        Navigator.loadScene("SignUp.fxml", "Done n Dusted");
    }
}
