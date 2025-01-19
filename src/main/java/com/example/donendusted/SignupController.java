package com.example.donendusted;

import java.io.File;
import java.io.FileWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class SignupController {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private TextField visibleConfirmPasswordField;

    @FXML
    protected void signUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            File file = new File("data/users.txt");

            try {
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    writer.write(username + " " + password + "\n");
                    writer.close();
                } else {
                    FileWriter writer = new FileWriter(file, true);
                    writer.write(username + " " + password + "\n");
                    writer.close();
                }
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14; -fx-padding:10;");
            }

            try {
                Navigator.loadScene("SignIn.fxml", "Done n Dusted");
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14; -fx-padding:10;");
            }
        } else {
            errorLabel.setText("Passwords are not the same!");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14; -fx-padding:10;");
        }
    }

    @FXML
    protected void openLoginPage()
    {
        Navigator.loadScene("SignIn.fxml", "Done n Dusted");
    }
    @FXML
    protected void close() {
        System.exit(0);
    }

    @FXML
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            // Show the passwords in plain text
            visiblePasswordField.setText(passwordField.getText());
            visibleConfirmPasswordField.setText(confirmPasswordField.getText());

            // Hide the password fields
            passwordField.setVisible(false);
            confirmPasswordField.setVisible(false);

            // Make the visible fields visible
            visiblePasswordField.setVisible(true);
            visibleConfirmPasswordField.setVisible(true);
        } else {
            // Hide the visible password fields
            visiblePasswordField.setVisible(false);
            visibleConfirmPasswordField.setVisible(false);

            // Show the password fields again
            passwordField.setVisible(true);
            confirmPasswordField.setVisible(true);
        }
    }

}
