package javaCode.InLog;

import javaCode.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Inlog implements Initializable {

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

    Lists lists = new Lists();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Component motor1 = new Component("1", "1-01","Motor", "Rask jævel", 20000);
        Component wheel1 = new Component("2", "2-01", "Ratt" ,"Billig", 2000);
        Component rim1 = new Component("3", "3-01", "Felg" ,"Dyreste", 2000);
        Component setetrekk = new Component("4", "4-01", "Setetrekk" ,"Skinn", 2000);
        Component motor2 = new Component("3", "1-02", "Motor" ,"Effektiv", 35000);
        Component wheel2 = new Component("1", "2-02", "Ratt", "Sport", 5000);
        Component exhaust = new Component("1", "5-01", "Eksospotte", "Bråkete", 4000);

        Adjustment hitch = new Adjustment("Hengerfeste", 2000);
        Adjustment sunroof = new Adjustment("Soltak", 5000);
        Adjustment gps = new Adjustment("Integrert GPS", 7000);
        Adjustment airCondition = new Adjustment("Air Condition", 8000);


        Car bensin = new Car("1", "Bensin", "Bensinbil", 150000);
        Car diesel = new Car("2", "Diesel", "Dieselbil", 150000);
        Car elektrisk = new Car("3", "Elektrisk", "Elektrisk bil", 150000);
        Car hybrid = new Car("4", "Hybrid", "Hybridbil", 150000);
        lists.addComponent(motor1);
        lists.addComponent(wheel1);
        lists.addComponent(rim1);
        lists.addComponent(setetrekk);
        lists.addComponent(motor2);
        lists.addComponent(wheel2);
        lists.addComponent(exhaust);
        lists.addCar(bensin);
        lists.addCar(diesel);
        lists.addCar(elektrisk);
        lists.addCar(hybrid);
        lists.addAdjustment(hitch);
        lists.addAdjustment(sunroof);
        lists.addAdjustment(gps);
        lists.addAdjustment(airCondition);
    }

    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        boolean correct = false;
        boolean superUsr = false;

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
                    LoggedIn.setId(strings[0]);
                    correct = true;
                    superUsr = Boolean.parseBoolean(strings[6]);
                    break;
                }
            }
            myReader.close();
        }
        if(correct){
            if(superUsr) {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
                OpenScene.newScene("Superuser",  root, 800, 500, event);
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("User", root, 800, 500, event);
            }
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

    public void enterKeyPressed(KeyEvent kEvent) {
        if(kEvent.getCode()== KeyCode.ENTER)
            btnLogIn.fire();
    }
}