package javaCode.superUser;
import javaCode.*;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerOrders implements Initializable {
    Stage stage = new Stage();
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();
    private static ObservableList<Component> componentListSuperUser = FXCollections.observableArrayList();
    FileHandler fileHandler = new FileHandler();

    @FXML
    private Label lblOutName;

    @FXML
    private Label lblOutPhone;

    @FXML
    private Label lblMailOut;

    @FXML
    private TableView<Order> tableViewOrder;

    @FXML
    private TableView<Component> tableViewComponents;

    @FXML
    private TableView<Order> tableViewAdjustments;

    @FXML
    private TableColumn<TableView<Order>, Integer> personId;

    @FXML
    private TableColumn<TableView<Order>, String> carId;

    @FXML
    private TableColumn<TableView<Order>, Date> orderStarted;

    @FXML
    private TableColumn<TableView<Order>, Date> orderEnded;

    @FXML
    private TableColumn<TableView<Order>, Integer> totalPrice;

    @FXML
    private TableColumn<TableView<Order>, String> color;

    @FXML
    private TableColumn<TableView<Order>, Boolean> orderStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewOrder.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();
        fileHandler.readAllFiles(stage);

        personId.setStyle("-fx-background-color: green;");
        tableViewOrder.setItems(Lists.getOrders());
        //Finds person in first order
        int outPersonId = 0;

        tableViewOrder.getSelectionModel().selectFirst();
        outPersonId = tableViewOrder.getSelectionModel().getSelectedItem().getPersonId();

        try {
            for(User i : ReadUsers.getUserList()){
                if(i.getId() == outPersonId){
                    lblOutName.setText(i.getFirstName() + " "+ i.getLastName());
                    lblOutPhone.setText((i.getPhone()));
                    lblMailOut.setText(i.getEmail());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for(Order i : Lists.getOrders()){
            tableViewComponents.setItems(i.getComponentList());
        }
        totalPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
    }
    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        fileHandler.saveSelectedFile(stage);
        tableViewOrder.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();
        FXMLLoader loader = new FXMLLoader();

        loader.setController("../superUser/ControllerSuperUser");
        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.
                newScene("Edit orders", root, 710 ,500, event);
    }

    @FXML
    void onClickView(MouseEvent event) throws FileNotFoundException {
        int outPersonId = 0;
        for(Order i : Lists.getOrders()){
            if(tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr() == i.getOrderNr()){
                tableViewComponents.setItems(i.getComponentList());
                outPersonId = i.getPersonId();
            }
        }
        for(User i : ReadUsers.getUserList()){
            if(i.getId() == outPersonId){
                lblOutName.setText(i.getFirstName() + " "+ i.getLastName());
                lblOutPhone.setText((i.getPhone()));
                lblMailOut.setText(i.getEmail());
            }
        }


    }

    
    @FXML
    void componentIdEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{
            event.getRowValue().setComponentID(event.getNewValue());
        }
        catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewComponents.refresh();
    }

    @FXML
    void componentTypeEdited(TableColumn.CellEditEvent<Component, String> event) {

    }

    @FXML
    void componentDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) {

    }

    @FXML
    void componentPriceEdited(TableColumn.CellEditEvent<Component, String> event) {

    }



}


