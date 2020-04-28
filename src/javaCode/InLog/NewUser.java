package javaCode.InLog;

import javaCode.Dialogs;
import javaCode.OpenScene;
import javaCode.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Button btnRegisterUser;

    @FXML
    void btnRegisterUserOnClick(ActionEvent actionevent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if((ReadUsers.getUserId(txtEmail.getText()) == null) && ReadUsers.getUserId(txtPhone.getText()) == null) {
            if (Validator.name(txtFirstName.getText()) != null &&
                    Validator.name(txtLastName.getText()) != null && Validator.email(txtEmail.getText()) != null &&
                    Validator.phone(txtPhone.getText()) != null) {
                User newUser = new User(Formatter.assignID(), Validator.name(txtFirstName.getText()),
                        Validator.name(txtLastName.getText()), Validator.email(txtEmail.getText()),
                        Validator.phone(txtPhone.getText()), txtPassword.getText(), false);
                Formatter.addToFile(newUser);
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
                OpenScene.newScene("Log in", root, 500, 500, actionevent);
            }
        }else {
            Dialogs.showErrorDialog("User already exists");
        }
    }

}
