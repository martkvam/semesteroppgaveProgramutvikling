package javaCode.InLog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewUser {

    @FXML
    private TextArea lblInfo;

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
    void btnRegisterUserOnClick(ActionEvent actionevent) throws IOException {
        if(!ReadUsers.readFile(txtEmail.getText(), txtPhone.getText())){
        User newUser = new User(Formatter.assignID(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(),
               txtPhone.getText(), txtPassword.getText());
         Formatter.addToFile(newUser);
         Stage stage = (Stage) btnRegisterUser.getScene().getWindow();
         stage.close();
        } else {
            lblInfo.setVisible(true);
            lblInfo.setText("User already exists");
        }
    }

}
