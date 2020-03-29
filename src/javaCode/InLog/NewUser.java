package javaCode.InLog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button btnRegisterUser;


    @FXML
    void btnRegisterUserOnClick(ActionEvent actionevent) throws IOException {
        Formatter.addToFile(Formatter.person(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(),
                txtPhone.getText()));
       Stage stage = (Stage) btnRegisterUser.getScene().getWindow();
       stage.close();
    }

}
