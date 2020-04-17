package javaCode.InLog;

import javaCode.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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

        Adjustment hitch = new Adjustment("1", "Hengerfeste", "Universell hengerfeste", 2000);
        Adjustment sunroof = new Adjustment("2", "Soltak", "Soltak med UV-filter", 7000);
        Adjustment gps = new Adjustment("3", "Integrert GPS", "Integrert GPS", 6000);
        Adjustment airCondition = new Adjustment("4", "Air Condition", "Air Condition", 8000);

        Car bensin = new Car("1", "Bensin", "Bensinbil", 150000);
        Car diesel = new Car("2", "Diesel", "Dieselbil", 150000);
        Car elektrisk = new Car("3", "Elektrisk", "Elektrisk bil", 150000);
        Car hybrid = new Car("4", "Hybrid", "Hybridbil", 150000);

        ObservableList<Component> testList = FXCollections.observableArrayList();
        testList.add(motor1);
        ObservableList<Adjustment> testList2 = FXCollections.observableArrayList();
        testList2.add(hitch);

        Date date1 = new Date(2/2/2019);
        Order order1 = new Order("1", 1, 1,date1, date1, Lists.getComponents(), Lists.getAdjustment(), 1000, "Blue", true );
        Order order2 = new Order("2", 1, 1, date1, date1, testList, testList2, 2000, "Red", false);
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
        lists.addOrder(order1);
        lists.addOngoingOrder(order2);

    }

    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        boolean correct = false;
        boolean superUsr = false;

        String id = Objects.requireNonNull(ReadUsers.getUserId(txtUserName.getText())).get(0);
        int length = Objects.requireNonNull(ReadUsers.getInfo(id, "User")).length();
        String info = Objects.requireNonNull(ReadUsers.getInfo(id, "User")).substring(1, length-1);
        String [] values = info.replaceAll("\\s+","").split(",");

        if((values[3].equals(txtUserName.getText()) || values[4].equals(txtUserName.getText())) &&
                values[5].equals(txtPassword.getText())){
            Dialogs.showSuccessDialog("Success");
            LoggedIn.setId(id);
            correct = true;
            superUsr = Boolean.parseBoolean(values[6]);
        }

        if(correct){
            if(superUsr) {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
                OpenScene.newScene("Superuser",  root, 800, 500, event);
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("User", root, 700, 700, event);
            }
        }else{
            Dialogs.showErrorDialog("Username and password inncorrect");
        }
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        OpenScene.newScene("Register User",  root, 300, 500, event);
    }

    public void enterKeyPressed(KeyEvent kEvent) {
        if(kEvent.getCode()== KeyCode.ENTER)
            btnLogIn.fire();
    }
}