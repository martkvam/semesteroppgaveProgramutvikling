package javaCode.superUser;
import javaCode.*;
import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javaCode.user.ProfileController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ControllerOrders implements Initializable {
    Stage stage = new Stage();
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();
    private static ObservableList<Order> orderList = FXCollections.observableArrayList();
    private static Order selectedOrder;
    FileHandler fileHandler = new FileHandler();
    DeleteElements deleteElements = new DeleteElements();

    @FXML
    private Label lblOutName;

    @FXML
    private Label lblOutPhone;

    @FXML
    private Label lblMailOut;

    @FXML
    private Button deleteOrder;

    @FXML
    private TableView<Order> tableViewOrder;

    @FXML
    private TableView<Component> tableViewComponents;

    @FXML
    private TableView<Adjustment> tableViewAdjustments;

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

    @FXML
    private TableColumn<TableView<Component>, Integer> componentPrice;

    @FXML
    private TableColumn<TableView<Adjustment>, Integer> adjustmentPrice;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ControllerOrdersAddComponent.toBeChanged){
            Order editedOrder = ControllerOrdersAddComponent.getNewComponents();

            for(Order i : tableViewOrder.getItems()){
                if(i.getOrderNr().equals(editedOrder.getOrderNr())){
                    i.getComponentList().clear();
                    i.setComponentList(editedOrder.getComponentList());
                }
            }
            tableViewOrder.getSelectionModel().select(selectedOrder);
            tableViewOrder.setItems(Lists.getOrders());
            int outPersonId = 0;
            selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
            outPersonId = tableViewOrder.getSelectionModel().getSelectedItem().getPersonId();

            try {
                for(User i : ReadUsers.getUserList()){
                    if(i.getId() == outPersonId){
                        lblOutName.setText(i.getFirstName() + " "+ i.getLastName());
                        lblOutPhone.setText((i.getPhone()));
                        lblMailOut.setText(i.getEmail());
                    }
                }
            } catch (FileNotFoundException | UserAlreadyExistException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }

            for(Order i : Lists.getOrders()){
                tableViewComponents.setItems(i.getComponentList());
            }
            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);

        }
        else{

            tableViewOrder.getItems().clear();
            tableViewComponents.getItems().clear();
            tableViewAdjustments.getItems().clear();

            Thread delayThread = new Thread(new newThread());

            delayThread.start();
            try{
                delayThread.join();
            }catch (InterruptedException e){
                System.out.println("Nei");
            }

            FileHandler.readAllFiles(stage);

            tableViewOrder.setItems(Lists.getOrders());
            //Finds person in first order
            int outPersonId = 0;

            tableViewOrder.getSelectionModel().select(tableViewOrder.getItems().get(0));
            selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
            outPersonId = tableViewOrder.getSelectionModel().getSelectedItem().getPersonId();

        try {
            for(User i : ReadUsers.getUserList()){
                if(i.getId() == outPersonId){
                    lblOutName.setText(i.getFirstName() + " "+ i.getLastName());
                    lblOutPhone.setText((i.getPhone()));
                    lblMailOut.setText(i.getEmail());
                }
            }
        } catch (FileNotFoundException | UserAlreadyExistException e) {
            Dialogs.showErrorDialog(e.getMessage());
        }

            for(Order i : Lists.getOrders()){
                tableViewComponents.setItems(i.getComponentList());
                tableViewAdjustments.setItems(i.getAdjustmentList());
            }

        }
        personId.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        totalPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        componentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        adjustmentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));

    }
    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FileHandler.saveSelectedFile(stage);
        tableViewOrder.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();
        FXMLLoader loader = new FXMLLoader();
        ControllerOrdersAddComponent.toBeChanged = false;

        loader.setController("../superUser/ControllerSuperUser");
        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.
                newScene("Edit orders", root, 710 ,500, event);
    }

    @FXML
    void onClickView(MouseEvent event) throws FileNotFoundException, UserAlreadyExistException {
        int outPersonId = 0;
        selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
        for(Order i : Lists.getOrders()){
            if(tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr() == i.getOrderNr()){
                tableViewComponents.setItems(i.getComponentList());
                tableViewAdjustments.setItems(i.getAdjustmentList());
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
    void deleteOrder(ActionEvent event) {
        try{
            if(EditOrders.deleteOrder(tableViewOrder.getSelectionModel().getSelectedItems())){
                //Deletes tableRow(s)
                tableViewOrder.getItems().removeAll(tableViewOrder.getSelectionModel().getSelectedItems());
            }
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    @FXML
    void btnAddComponent(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserAddComponentAndAdjustment.fxml"));
        OpenScene.newScene("Components", root, 870, 460, event );

       }

    @FXML
    void btnDeleteComponent(ActionEvent event) {
        try{
            if(deleteElements.deleteComponents(tableViewComponents.getSelectionModel().getSelectedItems())){
                //Deletes tableRow(s)
                tableViewComponents.getItems().removeAll(tableViewComponents.getSelectionModel().getSelectedItems());
            }
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    @FXML
    void btnNewComponent(ActionEvent event) {
        String selectedCartype ="";
        for(Car i : Lists.getCars()){
            if(i.getCarID().equals(tableViewOrder.getSelectionModel().getSelectedItem().getCarId())){
                selectedCartype = i.getCarType();
            }
        }
        addElements.openAddComponentsDialog(Lists.getCars(),Lists.getComponents(),selectedCartype,"", "", 0);

    }


    @FXML
    void carIdEdited(TableColumn.CellEditEvent<Order, String> event) {
        try{
            event.getRowValue().setCarId(event.getNewValue());
        }catch (IllegalArgumentException e){

        }
        tableViewOrder.refresh();

    }
    @FXML
    void orderEndedEdited(TableColumn.CellEditEvent<Order, String> event) {

    }

    @FXML
    void totPriceEdited(TableColumn.CellEditEvent<Order, Integer> event) {
        try{
            int price = EditOrders.editOrderPrice(event.getOldValue());
            if(price <= 0) {
                Dialogs.showErrorDialog("Invalid total price");
            }

            else {
                event.getRowValue().setTotPrice(price);
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewOrder.refresh();
    }

    @FXML
    void carColorEdited(TableColumn.CellEditEvent<Order, String> event) {
        try{
            event.getRowValue().setCarColor(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewOrder.refresh();
    }

    @FXML
    void orderStatusEdited(ActionEvent event) {

    }


    @FXML
    void componentIdEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{
            event.getRowValue().setComponentID(event.getNewValue());
            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
        }
        catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewComponents.refresh();
    }

    @FXML
    void componentTypeEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{
            event.getRowValue().setComponentType(event.getNewValue());
            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewComponents.refresh();
    }

    @FXML
    void componentDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) {
        try{

        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    @FXML
    void componentPriceEdited(TableColumn.CellEditEvent<Component, Integer> event) {

        try{
            int price = EditOrders.editOrderPrice(event.getOldValue());
            if(price <= 0) {
                Dialogs.showErrorDialog("Invalid total price");
            }
            else {
                event.getRowValue().setComponentPrice(price);
                tableViewOrder.getSelectionModel().getSelectedItem().setTotPrice(EditOrders.recalculateTotalPrice(tableViewOrder.getSelectionModel().getSelectedItem()));

                Date dateNow = new Date();
                tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewComponents.refresh();
        tableViewOrder.refresh();
    }
    public static Order getSelectedOrder(){
        return selectedOrder;
    }

    @FXML
    void adjustmentPriceEdited(TableColumn.CellEditEvent<Adjustment, Integer> event) {
        try{
            int price = EditOrders.editOrderPrice(event.getOldValue());
            if(price <= 0) {
                Dialogs.showErrorDialog("Invalid total price");
            }

            else {
                event.getRowValue().setAdjustmentPrice(price);
                Date dateNow = new Date();
                tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
                tableViewOrder.getSelectionModel().getSelectedItem().setTotPrice(EditOrders.recalculateTotalPrice(tableViewOrder.getSelectionModel().getSelectedItem()));
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewAdjustments.refresh();
        tableViewOrder.refresh();
    }
}


