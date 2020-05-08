package javaCode.user;

import javaCode.Dialogs;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateInfoController implements Initializable {

    @FXML
    private TextField txtChangePhone;

    @FXML
    private TextField txtChangeEmail;

    @FXML
    private TextField txtChangePassword;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnCancel;

    @FXML
    void cancel(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
        OpenScene.newScene("My profile", root, 610, 650, event);
    }

    @FXML //Method for changing personal info
    void changeInfo(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String ID = "" + LoggedIn.getId();

        String phone = txtChangePhone.getText();
        String email = txtChangeEmail.getText();
        String password = txtChangePassword.getText();

        boolean correctInfo = true;

        if(!phone.isEmpty()){
            try {
                ReadUsers.changeInfo(ID, "Phone", phone);
            } catch (IOException e) {
                Dialogs.showErrorDialog("Phone number is invalid");
                correctInfo = false;
            }
        }

        if(!email.isEmpty()){
            try {
                ReadUsers.changeInfo(ID, "Email", email);
            } catch (IOException e) {
                Dialogs.showErrorDialog("Email is invalid");
                correctInfo = false;
            }
        }
        if(!password.isEmpty()){
            try {
                ReadUsers.changeInfo(ID, "Password", password);
            } catch (IOException e) {
                Dialogs.showErrorDialog("Password is invalid");
                correctInfo = false;
            }
        }

        if(correctInfo){
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
            OpenScene.newScene("My profile", root, 610, 650, event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String ID = "" + LoggedIn.getId();
        try {
            txtChangeEmail.setText(ReadUsers.getInfo(ID, "Email"));
            txtChangePassword.setText(ReadUsers.getInfo(ID, "Password"));
            txtChangePhone.setText(ReadUsers.getInfo(ID, "Phone"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}