package javaCode.superUser;

import javaCode.*;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerAddEditComponents implements Initializable {

    //Sets up different classes
    Lists lists = new Lists();
    AddElements addElements = new AddElements();
    FileHandler filehandler = new FileHandler();
    private newThread delayThread;

    //Sets up int/String converter
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();

    //Sets up private variables
    private String selectedElement = "Car";
    private Stage stage;

    @FXML
    private TextField txtFilterInn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnDeleteElement;

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
        TableView.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();
        mainPane.setDisable(true);


        tableViewComponents.setVisible(false);
        tableViewAdjustments.setVisible(false);
        txtFilterInn.setVisible(false);

        //Sets up delay thread
        delayThread = new newThread();
        delayThread.setOnSucceeded(this::threadSucceded);
        delayThread.setOnFailed(this::threadFailed);
        Thread thread = new Thread(delayThread);


        //Multiple selection on TableViews
        TableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewComponents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAdjustments.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Sets integer elements in in TableViews

        price.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        componentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        adjustmentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));

        //Starts delayThread
        thread.setDaemon(true);
        thread.start();
    }

    //Method that starts when thread is done and succeeded
    private void threadSucceded(WorkerStateEvent e) {

        //Reads inn all files
        filehandler.readAllFiles(stage);

        //Enables the elements in the window
        mainPane.setDisable(false);

        //Sets items on TableView for cars
        TableView.setItems(Lists.getCars());

        //Sets up combobox elements and prompt text
        chooseElementType.getItems().addAll("Car", "Component", "Adjustment");
        chooseElementType.setPromptText("Choose element type");

        //Sets items in tableview
        TableView.setItems(Lists.getCars());
    }

    //Method that starts when thread is done and failed
    private void threadFailed(WorkerStateEvent e) {
        Dialogs.showErrorDialog("The loading did not succeed");
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
            OpenScene.newScene("Superuser",  root, 600, 400, null);
        }catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnAddFromFile(ActionEvent event) {
        //The user has to choose an element type before reading from file

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

    //Goes back to previous scene
    @FXML
    void btnBack(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        filehandler.saveAllFiles();
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.newScene("Superuser", root, 470 ,300, event);
        TableView.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();
    }

    //Sets tableview for adjustments and makes elements visible
    @FXML
    void btnEditAdjustments(ActionEvent event) {
        tableViewAdjustments.setItems(Lists.getAdjustment());
        tableViewAdjustments.setVisible(true);
        tableViewComponents.setVisible(false);
        TableView.setVisible(false);
        txtFilterInn.setVisible(true);
        selectedElement = "Adjustment";
        txtFilterInn.setText("");
    }

    //Sets tableview for cars and makes elements visible
    @FXML
    void btnEditCars(ActionEvent event) {
        TableView.setItems(Lists.getCars());

        TableView.setVisible(true);
        tableViewComponents.setVisible(false);
        tableViewAdjustments.setVisible(false);
        txtFilterInn.setVisible(false);
        selectedElement = "Car";
    }

    //Sets tableview for components and makes elements visible
    @FXML
    void btnEditComponents(ActionEvent event) {
        tableViewComponents.setItems(Lists.getComponents());
        tableViewComponents.setVisible(true);
        TableView.setVisible(false);
        tableViewAdjustments.setVisible(false);

        txtFilterInn.setVisible(true);
        selectedElement = "Components";
        txtFilterInn.setText("");
    }


    @FXML
    void btnNewCar(ActionEvent event) {
        //Directs user to dialog window to add a new car
        try{
            addElements.openAddCarDialog(Lists.getCars(),"", "", 0);

        }catch (NumberFormatException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    @FXML
    void btnNewComponent(ActionEvent event) {
        //Directs user to dialog window to add a new component
        addElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents(),"", "", "", 0);
    }

    @FXML
    void btnNewAdjustment(ActionEvent event) {
        //Directs user to dialog window to add a new adjustment
        addElements.openAddAdjustmentDialog(Lists.getAdjustment(),"", "", 0);
    }

    @FXML
    void btnDeleteElement(ActionEvent event) {
        //Choose to delete or not
        DeleteElements delete = new DeleteElements();
        try{
            switch (selectedElement) {
                case "Car":
                    //Checks if the superUser has selected a tableRow and wants to delete it
                    if(delete.deleteCars(TableView.getSelectionModel().getSelectedItems())){
                        //Deletes tableRow(s)
                        TableView.getItems().removeAll(TableView.getSelectionModel().getSelectedItems());
                        for(Car c : TableView.getSelectionModel().getSelectedItems()){
                            lists.addDeletedCar(c);
                        }
                    }

                    break;
                case "Components":
                    //Checks if the superUser has selected a tableRow and wants to delete it
                    if(delete.deleteComponents(tableViewComponents.getSelectionModel().getSelectedItems())) {
                        //Deletes tableRow(s)
                        for(Component c : tableViewComponents.getSelectionModel().getSelectedItems()){
                            lists.addDeletedComponent(c);
                        }
                        tableViewComponents.getItems().removeAll(tableViewComponents.getSelectionModel().getSelectedItems());
                    }

                    break;
                case "Adjustment":
                    if(delete.deleteAdjustments(tableViewAdjustments.getSelectionModel().getSelectedItems())) {
                        //Deletes tableRow(s)
                        for(Adjustment a : tableViewAdjustments.getSelectionModel().getSelectedItems()){
                            lists.addDeletedAdjustment(a);
                        }
                        tableViewAdjustments.getItems().removeAll(tableViewAdjustments.getSelectionModel().getSelectedItems());
                    }
                    break;
            }
        } catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        //Refresh tableview values
        tableViewComponents.refresh();
    }


    //Edit car from tableview
    //Edit car type
    @FXML
    void txtCarTypeEdited(TableColumn.CellEditEvent<Car, String> event) {
        try{
            event.getRowValue().setCarType(event.getNewValue());

        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        TableView.refresh();
    }
    //Edit car description
    @FXML
    void txtCarDescriptionEdited(TableColumn.CellEditEvent<Car, String> event) {
        try{
            event.getRowValue().setDescription(event.getNewValue());

        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        TableView.refresh();
    }

    //Edit car price
    @FXML
    void txtCarPriceEdited(TableColumn.CellEditEvent<Car, Integer> event) {
        try {
            if(intStrConverter.wasSuccessful())
                event.getRowValue().setPrice(event.getNewValue());
        } catch (NumberFormatException e) {
            Dialogs.showErrorDialog(e.getMessage());
        }

        TableView.refresh();
    }

    //Edit components from tableview
    //Edit component id
    @FXML
    void componentIDEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{
            event.getRowValue().setComponentID(event.getNewValue());
        }
        catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewComponents.refresh();
    }

    //Edit component type
    @FXML
    void componentTypeEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{
            event.getRowValue().setComponentType(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
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
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewComponents.refresh();
    }

    //Edit car id
    @FXML
    void carIDEdited(TableColumn.CellEditEvent<Component, String> event){
        try{
            event.getRowValue().setCarID(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewComponents.refresh();
    }

    //Edit adjustments from tableview
    //Adjustment id edited
    @FXML
    void adjustmentIdEdited(TableColumn.CellEditEvent<Adjustment, String> event) {
        try{
            event.getRowValue().setAdjustmentID(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewAdjustments.refresh();
    }
    //Adjustment type edited
    @FXML
    void adjustmentTypeEdited(TableColumn.CellEditEvent<Adjustment, String> event) {
        try{
            event.getRowValue().setAdjustmentType(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewAdjustments.refresh();
    }
    //Adjustment description editer
    @FXML
    void adjustmentDescriptionEdited(TableColumn.CellEditEvent<Adjustment, String> event) {
        event.getRowValue().setAdjustmentDescription(event.getNewValue());
        tableViewAdjustments.refresh();
    }

    //Adjustment price editer
    @FXML
    void adjustmentPriceEdited(TableColumn.CellEditEvent<Adjustment, Integer> event) {
        try {
            if(intStrConverter.wasSuccessful()) {
                event.getRowValue().setAdjustmentPrice(event.getNewValue());
            }
        } catch (NumberFormatException e) {
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewAdjustments.refresh();
    }


    //Updates and filters tableview by key event
    @FXML
    void txtFilterTyped(KeyEvent event) {
        if(selectedElement.equals("Components")){
            FilteredList<Component> filtered = new FilteredList<>(Lists.getComponents(), b -> true);

            //Event listener on input field with lambda expression that sets a new filtered list by method call
            txtFilterInn.textProperty().addListener((observable, oldValue, newValue) -> {
                filtered.setPredicate(component -> lists.filterComponentList(component, newValue));

                SortedList<Component> sorted = new SortedList<>(filtered);
                sorted.comparatorProperty().bind(tableViewComponents.comparatorProperty());
                tableViewComponents.setItems(sorted);
            });

        }
        else{
            FilteredList<Adjustment> filtered = new FilteredList<>(Lists.getAdjustment(), b -> true);

            //Event listener on input field with lambda expression that sets a new filtered list by method call. The new filtered list is set in the tableview
            txtFilterInn.textProperty().addListener((observable, oldValue, newValue) -> {
                filtered.setPredicate(adjustment -> lists.filterAdjustmentList(adjustment, newValue));

                SortedList<Adjustment> sorted = new SortedList<>(filtered);
                sorted.comparatorProperty().bind(tableViewAdjustments.comparatorProperty());
                tableViewAdjustments.setItems(sorted);
            });
        }
    }
}