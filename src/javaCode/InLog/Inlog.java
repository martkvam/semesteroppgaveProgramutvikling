package javaCode.InLog;

import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Inlog {

    @FXML
    private Text lblInfo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnNewUser;


    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        boolean correct = false;
        if(txtUserName.getText().equals("Admin") && txtPassword.getText().equals("Admin")){
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
            OpenScene.newScene("Register User",  root, 800, 500, event);
        }
        if(ReadUsers.readFile(txtUserName.getText(), txtUserName.getText())){
            File myObj = new File(Formatter.path);
            Scanner myReader = new Scanner(myObj);

            for (; myReader.hasNext(); ) {
                String u = myReader.next();
                String[] strings = u.split(";");
                if((strings[3].equals(txtUserName.getText()) || strings[4].equals(txtUserName.getText())) &&
                        strings[5].equals(txtPassword.getText())){
                    lblInfo.setVisible(true);
                    lblInfo.setText("Correct");
                    correct = true;
                    break;
                }
            }
            myReader.close();
        }
        if(correct){
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
            OpenScene.newScene("User",  root, 800, 500, event);
        }else{
            lblInfo.setText("Username and password incorrect");
        }
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        OpenScene.newScene("Register User",  root, 300, 500, event);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}