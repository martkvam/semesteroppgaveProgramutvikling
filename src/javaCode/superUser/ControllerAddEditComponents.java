package javaCode.superUser;
import javaCode.Component;
import javaCode.Lists;
import javaCode.Main;
import javaCode.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerAddEditComponents implements Initializable {

    addElements addcar = new addElements();

    @FXML
    private Label lblUt;

    @FXML
    private ComboBox<String> chooseFilterCarType;

    @FXML
    private ComboBox<String> chooseFilterComponent;

    @FXML
    private ComboBox<String> chooseFilterAdjustment;

    @FXML
    private TableView<Car> TableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Car i : Lists.getCars()) {
            chooseFilterCarType.getItems().add(i.getCarType());
        }
        chooseFilterCarType.setPromptText("Choose car");
        for(Component i : Lists.getComponents()) {
            chooseFilterComponent.getItems().add(i.getComponentType());
        }
        chooseFilterCarType.setVisible(false);
        chooseFilterComponent.setVisible(false);
        chooseFilterAdjustment.setVisible(false);
        TableView.setVisible(false);
    }
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
        TableView.setItems(Lists.getCars());
        for(int i = 0; i < Lists.getCars().size();i++){
            System.out.println(Lists.getCars().get(i).getDescription());
        }
        TableView.setVisible(true);
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
        ArrayList<Object> newComponent = addcar.openAddComponentsDialog(Lists.getCars(), Lists.getComponents() );
        for(int i = 0; i <newComponent.size(); i++){
            System.out.println(newComponent.get(i));
        }
    }
}





