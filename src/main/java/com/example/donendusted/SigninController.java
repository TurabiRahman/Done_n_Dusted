package com.example.donendusted;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.Scanner;

import java.io.FileWriter;

public class SigninController {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private CheckBox showPasswordCheckBox;

    private boolean isPasswordVisible;

    public void initialize() {
        isPasswordVisible = false;
        visiblePasswordField.setVisible(false);

        // Bind visiblePasswordField text to passwordField text
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    protected void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            visiblePasswordField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            visiblePasswordField.setVisible(false);
            passwordField.setVisible(true);
        }
    }

    @FXML
    protected void signIn() {
        String username = usernameField.getText();
        String password = passwordField.isVisible() ? passwordField.getText() : visiblePasswordField.getText();

        try {
            File file = new File("data/users.txt");
            if (!file.exists()) {
                throw new Exception("User not found!");
            } else {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String[] user = scanner.nextLine().split(" ");
                    if (user[0].equals(username) && user[1].equals(password)) {
                        try {
                            File userFile = new File("data/user.txt");
                            FileWriter writer = new FileWriter(userFile);
                            writer.write(username);
                            writer.close();
                            com.example.donendusted.Navigator.setScene(new com.example.donendusted.HomePage().getScene());
                        } catch (Exception e) {
                            errorLabel.setText(e.getMessage());
                            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14; -fx-padding:10;");
                        }
                    }
                }
                throw new Exception("User not found!");
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14; -fx-padding:10;");
        }
    }

    @FXML
    protected void openSignUpPage() {
        // Navigation to Sign Up scene
        Navigator.loadScene("SignUp.fxml", "Done n Dusted");
    }

    @FXML
    protected void close() {
        System.exit(0);
    }
}
