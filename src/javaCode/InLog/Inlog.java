package javaCode.InLog;

import javaCode.Dialogs;
import javaCode.FileHandler;
import javaCode.Lists;
import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Inlog implements Initializable {

    @FXML
    public TextField txtVisiblePassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private CheckBox chkBoxPassword;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnNewUser;
    Stage stage = new Stage();
    Lists lists = new Lists();
    FileHandler handler = new FileHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler.readAllFiles(stage);

    }

    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        boolean correct = false;
        boolean superUsr = false;
        String id = "";
        String[] values = new String[7];

        try {
            id = Objects.requireNonNull(ReadUsers.getUserId(txtUserName.getText())).get(0);
            int length = ReadUsers.getInfo(id, "User").length();
            String info = ReadUsers.getInfo(id, "User").substring(1, length - 1);
            values = info.replaceAll("\\s+", "").split(",");
        }catch (Exception e){
            Dialogs.showErrorDialog("User don't exist");
            txtUserName.clear();
            txtPassword.clear();
        }

        if((values[3].equals(txtUserName.getText()) || values[4].equals(txtUserName.getText())) &&
                values[5].equals(txtPassword.getText())){
            LoggedIn.setId(id);
            correct = true;
            superUsr = Boolean.parseBoolean(values[6]);
        }

        if(correct){
            if(superUsr) {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
                OpenScene.newScene("Superuser",  root, 470, 300, event);
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("User", root, 1000, 700, event);
            }
        }else{
            Dialogs.showErrorDialog("Username and password incorrect");
        }
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        OpenScene.newScene("Register User",  root, 300, 500, event);
    }

    public void enterKeyPressed(KeyEvent kEvent) {
        if(kEvent.getCode()== KeyCode.ENTER) {
            btnLogIn.fire();
        }
    }

    public void checkBoxShowPasswordClicked(ActionEvent actionEvent) {
        boolean checked = chkBoxPassword.isSelected();
        txtVisiblePassword.setEditable(checked);
        txtVisiblePassword.setDisable(!checked);
        txtPassword.setEditable(!checked);
        txtPassword.setDisable(checked);

        if(checked){
            txtVisiblePassword.setText(txtPassword.getText());
            txtVisiblePassword.toFront();
            txtVisiblePassword.setOpacity(1);
            txtPassword.toBack();
            txtPassword.setOpacity(0);
        } else{
            txtPassword.setText(txtVisiblePassword.getText());
            txtPassword.toFront();
            txtPassword.setOpacity(1);
            txtVisiblePassword.toBack();
            txtVisiblePassword.setOpacity(0);
        }
    }
}