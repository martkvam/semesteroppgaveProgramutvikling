package javaCode.InLog;

import javaCode.ConverterErrorHandler;
import javaCode.Dialogs;
import javaCode.FileHandler;
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
import java.util.ResourceBundle;

public class Inlog implements Initializable {

    private static boolean showPasswordFieldActive;
    @FXML
    public TextField txtVisiblePassword;
    Stage stage = new Stage();
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUserName;
    @FXML
    private CheckBox chkBoxPassword;
    @FXML
    private Button btnLogIn;
    private boolean disableKeyEvent;

    //Variable for passwordfields, if the text or the dot one i visible
    private static boolean isShowPasswordFieldActive() {
        return showPasswordFieldActive;
    }

    private static void setShowPasswordFieldActive(boolean showPasswordFieldActive) {
        Inlog.showPasswordFieldActive = showPasswordFieldActive;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileHandler.readAllFiles(stage);
    }

    //Checks and validates input values for logging in and handel exceptions
    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        ConverterErrorHandler.BooleanStringConverter boolStrConv = new ConverterErrorHandler.BooleanStringConverter();
        boolean correct = false;
        boolean superUsr = false;
        String id;
        String[] values;
        try {
            try {
                id = ReadUsers.getUserId(txtUserName.getText()).get(0);
                int length = ReadUsers.getInfo(id, "User").length();
                String info = ReadUsers.getInfo(id, "User").substring(1, length - 1);
                values = info.replaceAll("\\s+", "").split(",");
            } catch (Exception e) {
                if (txtUserName.getText().isEmpty() || (isShowPasswordFieldActive() && txtVisiblePassword.getText().isEmpty())
                        || (!isShowPasswordFieldActive() && txtPassword.getText().isEmpty())) {
                    throw new NullPointerException("All fields have to be filled");
                } else {
                    txtUserName.clear();
                    txtPassword.clear();
                    throw new IllegalArgumentException("User dont exist");
                }
            }

            if ((values[3].equals(txtUserName.getText()) || values[4].equals(txtUserName.getText()))) {
                if (!isShowPasswordFieldActive() && values[5].equals(txtPassword.getText())) {
                    LoggedIn.setId(id);
                    correct = true;
                    superUsr = Boolean.parseBoolean(values[6]);
                } else if (isShowPasswordFieldActive() && values[5].equals(txtVisiblePassword.getText())) {
                    LoggedIn.setId(id);
                    correct = true;
                    superUsr = boolStrConv.fromString(values[6]);
                }
            }


            if (correct) {
                if (superUsr) {
                    Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
                    OpenScene.newScene("Superuser", root, 470, 300, event);
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                    OpenScene.newScene("User", root, 700, 700, event);
                }
            } else {
                throw new IllegalArgumentException("Username and password inncorrect");
            }
        } catch (Exception e) {
            setDisableKeyEvent(true);
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    @FXML
        //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        OpenScene.newScene("Register User", root, 300, 500, event);
    }

    //Variable for disabling the keyEvent on ENTER when alert is present
    public boolean isDisableKeyEvent() {
        return disableKeyEvent;
    }

    public void setDisableKeyEvent(boolean disableKeyEvent) {
        this.disableKeyEvent = disableKeyEvent;
    }

    //Enables users to log in with pressing ENTER
    public void enterKeyPressed(KeyEvent kEvent) {
        if (!isDisableKeyEvent()) {
            if (kEvent.getCode() == KeyCode.ENTER) {
                btnLogIn.fire();
            }
        } else {
            setDisableKeyEvent(false);
        }
    }

    //Makes it possible to visualize the password
    public void checkBoxShowPasswordClicked(ActionEvent actionEvent) {
        boolean checked = chkBoxPassword.isSelected();
        txtVisiblePassword.setEditable(checked);
        txtVisiblePassword.setDisable(!checked);
        txtPassword.setEditable(!checked);
        txtPassword.setDisable(checked);

        if (checked) {
            txtVisiblePassword.setText(txtPassword.getText());
            txtVisiblePassword.toFront();
            txtVisiblePassword.setOpacity(1);
            txtPassword.toBack();
            txtPassword.setOpacity(0);
            setShowPasswordFieldActive(true);
        } else {
            txtPassword.setText(txtVisiblePassword.getText());
            txtPassword.toFront();
            txtPassword.setOpacity(1);
            txtVisiblePassword.toBack();
            txtVisiblePassword.setOpacity(0);
            setShowPasswordFieldActive(false);
        }
    }

}