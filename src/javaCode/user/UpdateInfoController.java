package javaCode.user;

import javaCode.Dialogs;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UpdateInfoController {

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
        OpenScene.newScene("My profile", root, 610, 630, event);
    }

    @FXML
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
            OpenScene.newScene("My profile", root, 610, 630, event);
        }

    }

}