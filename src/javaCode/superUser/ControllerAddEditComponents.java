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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerAddEditComponents implements Initializable {

    addElements addcar = new addElements();
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();
    tableFilter tableFilter = new tableFilter();


    @FXML
    private TextField txtFilterInn;

    @FXML
    private Button btnDeleteElement;

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
        chooseFilterComponent.getItems().addAll("Component ID", "Component type", "Component description","Component price", "Car ID");
        chooseFilterComponent.setPromptText("Filter list by");
        chooseFilterComponent.setVisible(false);
        chooseFilterAdjustment.setVisible(false);
        tableViewComponents.setVisible(false);
        txtFilterInn.setVisible(false);
        TableView.setItems(Lists.getCars());
        tableViewComponents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        chooseFilterComponent.setVisible(false);
        TableView.setVisible(true);
        tableViewComponents.setVisible(false);
        txtFilterInn.setVisible(false);
    }

    @FXML
    void btnEditComponents(ActionEvent event) {
        tableViewComponents.setItems(Lists.getComponents());
        tableViewComponents.setVisible(true);
        TableView.setVisible(false);
        chooseFilterComponent.setVisible(true);
        txtFilterInn.setVisible(true);

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
    void btnDeleteElement(ActionEvent event) {
        //Choose to delete or not
        boolean deleteSelectedItems = false;
        //Generate two lists to get all components and the selected components
        ObservableList<Component> allComponents, selectedComponents;

        //Gets all components
        allComponents = tableViewComponents.getItems();

        //Gets all selected components
        selectedComponents = tableViewComponents.getSelectionModel().getSelectedItems();

        //Makes sure that a table row has been selected
        if (tableViewComponents.getSelectionModel().getSelectedCells().size() > 0) {
            //If one row is selected
            if (tableViewComponents.getSelectionModel().getSelectedCells().size()==1) {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected component?");
            }
            //If multiple rows are selected
            else {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected components?");
            }
            //Makes sure that the superuser wants to delete the selected node(s).
            if(deleteSelectedItems){
                try {
                    //deletes all selected components
                    for(Component comp : selectedComponents){
                        allComponents.remove(comp);
                    }

                    //If the component could not be deleted
                }catch(Exception e){
                    Dialogs.showErrorDialog("Could not delete det selected component");
                }
            }
        }
        //If the user didnt choose a component to delete
        else{
            Dialogs.showErrorDialog("You have to choose a component to delete");
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





