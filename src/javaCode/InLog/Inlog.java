package javaCode.InLog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    void btnLogInOnClick(ActionEvent event) throws IOException {
        boolean correct = false;
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
        if(!correct) {
            lblInfo.setText("Username and password incorrect");
        }
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register User");
        stage.setScene(new Scene(root, 300, 500));
        stage.show();
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }
}