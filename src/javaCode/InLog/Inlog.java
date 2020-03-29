package javaCode.InLog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Inlog {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogIn;

    @FXML
    void btnLogInOnClick(ActionEvent event) {
        System.out.println("Username: " + txtUserName.getText() + "\nPassword: " + txtPassword.getText());
    }
}