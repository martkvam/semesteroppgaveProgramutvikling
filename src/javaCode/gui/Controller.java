package javaCode.gui;


import javaCode.*;
import javaCode.user.Methods;
import javaCode.user.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

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
    void btnSuperUser(ActionEvent event) throws IOException {

        //Sets new controller
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");

        // Swap screen
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        /*try{
            Main.superUser(window);
        }
        catch (Exception e){

        }*/
        
    }
    @FXML
    void btnUser(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");

        // Swap screen
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        /*try{
            Main.User(window);
        }
        catch (Exception e){

        }*/
    }

}


