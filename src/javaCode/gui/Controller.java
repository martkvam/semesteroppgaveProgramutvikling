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
    void btnBytt(ActionEvent event) throws IOException {

        //Finds new fxml file
        Parent swapParent = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));

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


}
