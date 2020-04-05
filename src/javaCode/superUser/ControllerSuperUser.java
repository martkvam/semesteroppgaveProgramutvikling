package javaCode.superUser;

import javaCode.Main;
import javaCode.OpenScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;


public class ControllerSuperUser {

    //Scene where superuser choose between two operations.

    @FXML
    private Button Components;

    @FXML
    private Label lblOut;

    //navigates to scene where superuser are able to add and edit components and information
    @FXML
    void btnEditComponents(ActionEvent event) throws IOException {

        //Sets new controller
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerAddEditComponents");

        // Swap screen
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        /*try{
            Main.superUserComponents(window);
        }
        catch (Exception e){

        }*/
    }

    //Navigates to scene where superuser are able to edit/delete orders.
    @FXML
    void btnEditOrders(ActionEvent event) {

    }

    //Takes superUser back to login page
    @FXML
    void btnGoBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 500, 500, event);
    }

    //Info about components scene on hover
    @FXML
    void btnInfoComp(MouseEvent event) {

        lblOut.setText("The components scene are where you as a Superuser can add new cars, components and adjustments in addition to edit existing components.");
    }

    //Info about orders scene on hover
    @FXML
    void btnInfoOrders(MouseEvent event) {
        lblOut.setText("The Orders scene are where you as a Superuser can view, edit and delete existing orders.");
    }

    //Removes info on remove hover
    @FXML
    void btnRemoveInfoComp(MouseEvent event) {
        lblOut.setText("");
    }

    //Removes info on remove hover
    @FXML
    void btnRemoveInfoOrders(MouseEvent event) {
        lblOut.setText("");
    }

}

