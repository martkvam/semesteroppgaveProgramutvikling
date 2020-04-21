package javaCode.superUser;

import javaCode.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerAddEditComponents implements Initializable {
    //Initialize different classes
    Lists lists = new Lists();
    addElements addElements = new addElements();
    tableFilter tableFilter = new tableFilter();
    FileHandler filehandler = new FileHandler();

    //Initialize int/String converter
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();

    //Sets up private variables
    private String selectedElement = "Car";
    private Stage stage;

    @FXML
    private TextField txtFilterInn;

    @FXML
    private Button btnDeleteElement;

    @FXML
    private ComboBox<String> chooseFilterComponent;

    @FXML
    private ComboBox<String> chooseFilterAdjustment;

    @FXML
    private ComboBox<String> chooseElementType;

    //TableView Car
    @FXML
    private TableView<Car> TableView;

    @FXML
    private TableColumn<TableView<Car>, Integer> price;

    //TableView Component
    @FXML
    private TableView<Component> tableViewComponents;

    @FXML
    private TableColumn<TableView<Component>, Integer> componentPrice;

    //TableView Adjustments
    @FXML
    private TableView<Adjustment> tableViewAdjustments;

    @FXML
    private TableColumn<TableView<Adjustment>, Integer> adjustmentPrice;

    //Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Reads inn all files
        //filehandler.readAllFiles(stage);

        //Sets up combobox elements and prompt text
        chooseFilterComponent.getItems().addAll("Component ID", "Component type", "Component description","Component price", "Car ID");
        chooseFilterComponent.setPromptText("Filter list by");

        chooseElementType.getItems().addAll("Car", "Component", "Adjustment");
        chooseElementType.setPromptText("Choose element type");

        //Sets visibility on different gui elements
        chooseFilterComponent.setVisible(false);
        chooseFilterAdjustment.setVisible(false);
        tableViewComponents.setVisible(false);
        tableViewAdjustments.setVisible(false);
        txtFilterInn.setVisible(false);

        //Sets items on TableView for cars
        TableView.setItems(Lists.getCars());

        //Multiple selection on TableViews
        TableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewComponents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAdjustments.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Sets integer elements in in TableViews
        price.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        componentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        adjustmentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));

    }

    @FXML
    void btnAddFromFile(ActionEvent event) {
        //Have to choose a element type
        if(chooseElementType.getValue() != null){
            if(chooseElementType.getValue().equals("Car")) {
                filehandler.openSelectedFile(stage, "Car");
            }
            else if(chooseElementType.getValue().equals("Component")) {
                filehandler.openSelectedFile(stage, "Component");
            }
            else if(chooseElementType.getValue().equals("Adjustment")) {
                filehandler.openSelectedFile(stage, "Adjustment");
            }
        }
        else{
            Dialogs.showErrorDialog("You have to choose a element to read from file");
        }
        TableView.refresh();
        tableViewComponents.refresh();
        tableViewAdjustments.refresh();
    }

    @FXML
    void btnBack(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        filehandler.saveAllFiles();
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.newScene("Superuser",  root, 600, 400, event);
    }

    @FXML
    void btnEditAdjustments(ActionEvent event) {
        tableViewAdjustments.setItems(Lists.getAdjustment());
        tableViewAdjustments.setVisible(true);
        tableViewComponents.setVisible(false);
        TableView.setVisible(false);
        chooseFilterComponent.setVisible(true);
        txtFilterInn.setVisible(true);
        selectedElement = "Adjustment";
    }

    @FXML
    void btnEditCars(ActionEvent event) {
        TableView.setItems(Lists.getCars());
        chooseFilterComponent.setVisible(false);
        TableView.setVisible(true);
        tableViewComponents.setVisible(false);
        tableViewAdjustments.setVisible(false);
        txtFilterInn.setVisible(false);
        selectedElement = "Car";
    }

    @FXML
    void btnEditComponents(ActionEvent event) {
        tableViewComponents.setItems(Lists.getComponents());
        tableViewComponents.setVisible(true);
        TableView.setVisible(false);
        tableViewAdjustments.setVisible(false);
        chooseFilterComponent.setVisible(true);
        txtFilterInn.setVisible(true);
        selectedElement = "Components";

    }

    @FXML
    void btnNewAdjustment(ActionEvent event) {

    }

    @FXML
    void btnNewCar(ActionEvent event) {

        addElements.openAddCarDialog(Lists.getCars());

    }

    @FXML
    void btnNewComponent(ActionEvent event) {
        addElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents());
    }


    @FXML
    void btnDeleteElement(ActionEvent event) throws IOException {
        //Choose to delete or not
        DeleteElements delete = new DeleteElements();
        ObservableList<Component> deleteComponents = null;
        ObservableList<Car> deleteCars = null;
        try{
            switch (selectedElement) {
                case "Car":
                    //Checks if the superUser has selected a tableRow and wants to delete it
                    if(delete.deleteCars(TableView.getSelectionModel().getSelectedItems())){
                        //Deletes tableRow(s)
                        TableView.getItems().removeAll(TableView.getSelectionModel().getSelectedItems());
                    }

                    break;
                case "Components":
                    //Checks if the superUser has selected a tableRow and wants to delete it
                    if(delete.deleteComponents(tableViewComponents.getSelectionModel().getSelectedItems())) {
                        //Deletes tableRow(s)
                        tableViewComponents.getItems().removeAll(tableViewComponents.getSelectionModel().getSelectedItems());
                    }

                    break;
                case "Adjsutments":
                    break;
            }
        } catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        //Refresh tableview values
        tableViewComponents.refresh();
    }

    //Edit car type
    @FXML
    void txtCarTypeEdited(TableColumn.CellEditEvent<Car, String> event) {
        event.getRowValue().setCarType(event.getNewValue());
        TableView.refresh();
    }
    //Edit car description
    @FXML
    void txtCarDescriptionEdited(TableColumn.CellEditEvent<Car, String> event) {
        event.getRowValue().setDescription(event.getNewValue());
        System.out.println(event.getRowValue());
        TableView.refresh();
    }

    //Edit car price
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

//Edit component id
    @FXML
    void componentIDEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentID(event.getNewValue());
        tableViewComponents.refresh();
    }

    //Edit component type
    @FXML
    void componentTypeEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentType(event.getNewValue());
        tableViewComponents.refresh();
    }

    //Edit component description
    @FXML
    void componentDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setComponentDescription(event.getNewValue());
        tableViewComponents.refresh();
    }

    //Edit component price
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

//Edit car id
    @FXML
    void carIDEdited(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setCarID(event.getNewValue());
        tableViewComponents.refresh();
    }

    @FXML
    void adjustmentIdEdited(ActionEvent event) {

    }

    @FXML
    void adjustmentDescriptionEdited(ActionEvent event) {

    }

    @FXML
    void adjustmentPriceEdited(ActionEvent event) {

    }

    @FXML
    void adjustmentTypeEdited(ActionEvent event) {

    }


    //Updates filter by button click
    @FXML
    void txtFilterTyped(KeyEvent event) {

        try{
            String txtInn = txtFilterInn.getText();
            String choosenFilter = chooseFilterComponent.getValue();
            tableViewComponents.setItems(tableFilter.filterComponents(txtInn, choosenFilter));
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

}





