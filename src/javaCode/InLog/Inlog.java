package javaCode.InLog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Inlog {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnNewUser;

    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException {

        System.out.println("Username: " + txtUserName.getText() + "\nPassword: " + txtPassword.getText());
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
}