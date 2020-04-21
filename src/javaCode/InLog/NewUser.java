package javaCode.InLog;

import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewUser {

    @FXML
    private Text lblInfo;

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
    private Button btnRegisterUser;

    @FXML
    void btnRegisterUserOnClick(ActionEvent actionevent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(!ReadUsers.readFile(txtEmail.getText(), txtPhone.getText())){
            User newUser = new User(Formatter.assignID(), Validator.name(txtFirstName.getText()),
                    Validator.name(txtLastName.getText()), Validator.email(txtEmail.getText()),
                   Validator.phone(txtPhone.getText()), txtPassword.getText(), false);
            Formatter.addToFile(newUser);
            Stage stage = (Stage) btnRegisterUser.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
            OpenScene.newScene("Log in", root, 500, 500, actionevent);
        } else {
            lblInfo.setText("User already exists");
        }
    }

    @FXML
    void btnUpdateUserOnClick(ActionEvent event) throws IOException {
        String id = String.valueOf(LoggedIn.getId());
        ReadUsers.changeInfo(id, "FirstName", txtFirstName.getText());
        ReadUsers.
    }

    @FXML
    void updateUser() throws FileNotFoundException {
        String id = String.valueOf(LoggedIn.getId());

        txtFirstName.setText(ReadUsers.getInfo(id, "FirstName"));
        txtLastName.setText(ReadUsers.getInfo(id, "LastName"));
        txtEmail.setText(ReadUsers.getInfo(id, "Email"));
        txtPhone.setText(ReadUsers.getInfo(id, "Phone"));
    }

}
