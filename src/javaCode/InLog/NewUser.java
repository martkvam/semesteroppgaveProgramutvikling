package javaCode.InLog;

import javaCode.Dialogs;
import javaCode.OpenScene;
import javaCode.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    @FXML   //Button for validating new user input, registering new user or handle exception
    void btnRegisterUserOnClick(ActionEvent actionevent) {
            try {
                ReadUsers.checkIfUserExists(txtEmail.getText(), txtPhone.getText());
                passwordMatches();
                User newUser = new User(Formatter.assignID(), txtFirstName.getText(),
                        txtLastName.getText(), txtEmail.getText(), txtPhone.getText(),
                         txtPassword.getText(), false);
                Formatter.addToFile(newUser);
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
                OpenScene.newScene("Log in", root, 600, 450, actionevent);
            } catch (Exception e){
                Dialogs.showErrorDialog("Could register user due to:\n" + e.getMessage());
            }
    }

    //Method for user to register by pressing enter, as well calling allFieldsWritten to check if all fields are filled
    public void enterKeyPressed(KeyEvent kEvent) {
        allFieldsWritten(kEvent);
        if(kEvent.getCode()== KeyCode.ENTER) {
            btnRegisterUser.fire();
        }
    }

    //Checking if all fields are filled and enable the register button after
    private void allFieldsWritten(KeyEvent keyEvent){
        if(!(txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() ||
                txtPassword.getText().isEmpty() || txtPhone.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtRepeatPassword.getText().isEmpty())){
            btnRegisterUser.setDisable(false);
        } else{
            btnRegisterUser.setDisable(true);
        }
    }

    @FXML //Checking if the passwords in the two password fields match
    private void passwordMatches(){
        if(!txtPassword.getText().matches(txtRepeatPassword.getText())){
            throw new IllegalArgumentException("Password dont match");
        }
    }

    //Opens the inlog scene when the "back" button is pressed
    public void btnBackOnClick(ActionEvent actionEvent) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 600, 450, actionEvent);
    }

    @FXML
    public void initialize(){

    }
}
