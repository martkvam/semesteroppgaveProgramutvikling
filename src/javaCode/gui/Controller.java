package javaCode.gui;


import javaCode.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    void btnSuperUser(ActionEvent event) throws IOException {

        //Sets new controller
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");

        // Swap screen
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try{
            Main.superUser(window);
        }
        catch (Exception e){

        }
        
    }
    @FXML
    void btnUser(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");

        // Swap screen
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try{
            Main.User(window);
        }
        catch (Exception e){

        }
    }

}


