package javaCode.superUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ControllerSuperUser {

    //Scene where superuser choose between two operations.


    @FXML
    private Button Components;

    @FXML
    private Label lblOut;

    //navigates to scene where superuser are able to add and edit components and information
    @FXML
    void btnEditComponents(ActionEvent event) {

    }

    //Navigates to scene where superuser are able to edit/delete orders.
    @FXML
    void btnEditOrders(ActionEvent event) {

    }

    //Takes superUser back to login page
    @FXML
    void btnGoBack(ActionEvent event) {

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

