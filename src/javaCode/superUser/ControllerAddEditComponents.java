package javaCode.superUser;
import javaCode.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerAddEditComponents implements Initializable {

    addElements addcar = new addElements();
    private ConverterErrorHandler.IntegerStringConverter intStrConverter
            = new ConverterErrorHandler.IntegerStringConverter();

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

    @FXML
    private TableColumn<TableView<Car>, Integer> price;


    @FXML
    private TableView<Component> tableViewComponents;

    @FXML
    private TableColumn<TableView<Component>, Integer> componentPrice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Car i : Lists.getCars()) {
            chooseFilterCarType.getItems().add(i.getCarType());
        }
        chooseFilterCarType.setPromptText("Choose car");
        for (Component i : Lists.getComponents()) {
            chooseFilterComponent.getItems().add(i.getComponentType());
        }
        chooseFilterCarType.setVisible(false);
        chooseFilterComponent.setVisible(false);
        chooseFilterAdjustment.setVisible(false);
        TableView.setVisible(false);
        tableViewComponents.setVisible(false);

        price.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        componentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));


    }

    @FXML
    void btnAddFromFile(ActionEvent event) {

    }

    @FXML
    void btnBack(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        //Sets new controller
        /*FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");
        //OpenScene.newScene("");

        // Swap screen
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        /*try {
            Main.superUser(window);
        } catch (Exception e) {

        }*/
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.newScene("Superuser",  root, 800, 500, event);
    }

    @FXML
    void btnEditAdjustments(ActionEvent event) {

    }

    @FXML
    void btnEditCars(ActionEvent event) {
        TableView.setItems(Lists.getCars());

        TableView.setVisible(true);
        tableViewComponents.setVisible(false);
    }

    @FXML
    void btnEditComponents(ActionEvent event) {
        tableViewComponents.setItems(Lists.getComponents());
        tableViewComponents.setVisible(true);
        TableView.setVisible(false);

    }

    @FXML
    void btnNewAdjustment(ActionEvent event) {

    }

    @FXML
    void btnNewCar(ActionEvent event) {

        addcar.openAddCarDialog(Lists.getCars());

    }

    @FXML
    void btnNewComponent(ActionEvent event) {
        addcar.openAddComponentsDialog(Lists.getCars(), Lists.getComponents());
    }

    @FXML
    void txtCarTypeEdited(TableColumn.CellEditEvent<Car, String> event) {
        event.getRowValue().setCarType(event.getNewValue());
        TableView.refresh();
    }
    @FXML
    void txtCarDescriptionEdited(TableColumn.CellEditEvent<Car, String> event) {
        event.getRowValue().setDescription(event.getNewValue());
        System.out.println(event.getRowValue());
        TableView.refresh();
    }

    @FXML
    void txtCarPriceEdited(TableColumn.CellEditEvent<Car, Integer> event) {
        try {
            if(intStrConverter.wasSuccessful())
                event.getRowValue().setPrice(event.getNewValue());
        } catch (NumberFormatException e) {
            Dialogs.showErrorDialog("Du må skrive inn et positivt tall.");
        } catch (IllegalArgumentException e) {
            Dialogs.showErrorDialog("Ugyldig alder: " + e.getMessage());
        }

        TableView.refresh();
    }


    @FXML
    void componentIDEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentID(event.getNewValue());
        tableViewComponents.refresh();
    }
    @FXML
    void componentTypeEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentType(event.getNewValue());
        tableViewComponents.refresh();
    }
    @FXML
    void componentDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentDescription(event.getNewValue());
        tableViewComponents.refresh();
    }

    @FXML
    void componentPriceEdited(TableColumn.CellEditEvent<Component, Integer> event) {
        try {
            if(intStrConverter.wasSuccessful())
                event.getRowValue().setComponentPrice(event.getNewValue());
        } catch (NumberFormatException e) {
            Dialogs.showErrorDialog("Du må skrive inn et positivt tall.");
        } catch (IllegalArgumentException e) {
            Dialogs.showErrorDialog("Ugyldig alder: " + e.getMessage());
        }

        tableViewComponents.refresh();
    }


    @FXML
    void carIDEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setCarID(event.getNewValue());
        tableViewComponents.refresh();
    }

}





