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
                   Validator.phone(txtPhone.getText()), txtPassword.getText());
            Formatter.addToFile(newUser);
            Stage stage = (Stage) btnRegisterUser.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
            OpenScene.newScene("Log in", root, 500, 500, actionevent);
        } else {
            lblInfo.setText("User already exists");
        }
    }

}
