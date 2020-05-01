package javaCode.InLog;

import javaCode.Dialogs;
import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class NewUser {

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRepeatPassword;

    @FXML
    private Button btnRegisterUser;

    @FXML
    void btnRegisterUserOnClick(ActionEvent actionevent) throws IOException {
            try {
                ReadUsers.checkIfUserExists(txtEmail.getText(), txtPhone.getText());
                passwordMatches();
                User newUser = new User(Formatter.assignID(), txtFirstName.getText(),
                        txtLastName.getText(), txtEmail.getText(), txtPhone.getText(),
                         txtPassword.getText(), false);
                Formatter.addToFile(newUser);
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
                OpenScene.newScene("Log in", root, 500, 500, actionevent);
            } catch (Exception e){
                Dialogs.showErrorDialog("Could register user due to:\n" + e.getMessage());
            }
    }

    public void enterKeyPressed(KeyEvent kEvent) {
        if(kEvent.getCode()== KeyCode.ENTER) {
            btnRegisterUser.fire();
        }
    }

    @FXML
    private void passwordMatches(){
        if(!txtPassword.getText().matches(txtRepeatPassword.getText())){
            throw new IllegalArgumentException("Password dont match");
        }
    }
}
