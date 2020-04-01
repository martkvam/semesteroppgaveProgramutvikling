package javaCode.superUser;
import javaCode.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerAddEditComponents {

    addElements addcar = new addElements();

    @FXML
    private Label lblUt;

    @FXML
    private ChoiceBox<?> chooseFilterCarType;

    @FXML
    private ChoiceBox<?> chooseFilterComponent;

    @FXML
    private ChoiceBox<?> chooseFilterAdjustment;

    @FXML
    void btnAddFromFile(ActionEvent event) {

    }

    @FXML
    void btnBack(ActionEvent event) {
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
    void btnEditAdjustments(ActionEvent event) {

    }

    @FXML
    void btnEditCars(ActionEvent event) {

    }

    @FXML
    void btnEditComponents(ActionEvent event) {

    }

    @FXML
    void btnNewAdjustment(ActionEvent event) {

    }

    @FXML
    void btnNewCar(ActionEvent event) {

        addcar.openAddCarDialog();

    }

    @FXML
    void btnNewComponent(ActionEvent event) {
        ArrayList<Object> newComponent = addcar.openAddComponentsDialog();
        for(int i = 0; i <newComponent.size(); i++){
            System.out.println(newComponent.get(i));
        }


    }

}





