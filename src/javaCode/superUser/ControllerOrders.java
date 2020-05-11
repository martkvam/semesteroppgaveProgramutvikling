package javaCode.superUser;
import javaCode.*;
import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.ReadUsers;
import javaCode.objects.User;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerOrders implements Initializable {
    private static int firstInlog;
    Stage stage = new Stage();
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();
    private ConverterErrorHandler.BooleanStringConverter booleanStringConverter = new ConverterErrorHandler.BooleanStringConverter();

    private static Order selectedOrder;
    FileHandler fileHandler = new FileHandler();
    DeleteElements deleteElements = new DeleteElements();
    Lists list = new Lists();

    private newThread delayThread;

    @FXML
    private Label lblOutName;

    @FXML
    private Label lblOutPhone;

    @FXML
    private Label lblMailOut;

    @FXML
    private TextField txtFilterOrders;

    @FXML
    private Button buttonBack;

    @FXML
    private Button deleteOrder;

    @FXML
    private Button addComponent;

    @FXML
    private Button deleteComponent;

    @FXML
    private Button addAdjustment;

    @FXML
    private Button deleteAdjustrment;

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
            } catch (FileNotFoundException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }

            for(Order i : Lists.getOrders()){
                tableViewComponents.setItems(i.getComponentList());
            }
            for(Order i : Lists.getOrders()){
                tableViewAdjustments.setItems(i.getAdjustmentList());
                    if(tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr() == i.getOrderNr()){
                        tableViewComponents.setItems(i.getComponentList());
                        tableViewAdjustments.setItems(i.getAdjustmentList());
                        outPersonId = i.getPersonId();
                    }
            }

            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);

        }
        else if(ControllerOrdersAddAdjustment.toBeChanged){
            Order editedOrder = ControllerOrdersAddAdjustment.getNewAdjustments();

            for(Order i : tableViewOrder.getItems()){
                if(i.getOrderNr().equals(editedOrder.getOrderNr())){
                    i.getAdjustmentList().clear();
                    i.setAdjustmentList(editedOrder.getAdjustmentList());
                }
            }
            tableViewOrder.setItems(Lists.getOrders());
            tableViewOrder.getSelectionModel().select(selectedOrder);
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
            } catch (FileNotFoundException e) {
                Dialogs.showErrorDialog(e.getMessage());
            }

            for(Order i : Lists.getOrders()){
                tableViewComponents.setItems(i.getComponentList());
            }
            for(Order i : Lists.getOrders()){
                tableViewAdjustments.setItems(i.getAdjustmentList());
                if(tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr() == i.getOrderNr()){
                    tableViewComponents.setItems(i.getComponentList());
                    tableViewAdjustments.setItems(i.getAdjustmentList());
                    outPersonId = i.getPersonId();
                }
            }

            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
        }
        else{
            if(firstInlog == 0){
                //Starts delay thread
                delayThread = new newThread();
                delayThread.setOnSucceeded(this::threadDone);
                delayThread.setOnFailed(this::threadFailed);
                Thread th = new Thread(delayThread);

                tableViewOrder.setDisable(true);
                tableViewComponents.setDisable(true);
                tableViewAdjustments.setDisable(true);

                buttonBack.setDisable(true);
                txtFilterOrders.setDisable(true);
                deleteOrder.setDisable(true);

                deleteComponent.setDisable(true);
                addComponent.setDisable(true);
                addAdjustment.setDisable(true);
                deleteAdjustrment.setDisable(true);


                th.setDaemon(true);
                th.start();
                firstInlog = 1;
            }
            else{
                tableViewOrder.getItems().clear();
                tableViewComponents.getItems().clear();
                tableViewAdjustments.getItems().clear();

                fileHandler.readAllFiles(stage);


                tableViewOrder.setItems(Lists.getOrders());
                //Finds person in first order
                int outPersonId = 0;
                tableViewOrder.requestFocus();
                tableViewOrder.getSelectionModel().select(0);
                tableViewOrder.getFocusModel().focus(0);
                tableViewOrder.scrollTo(0);
                System.out.println();

                //tableViewOrder.getSelectionModel().selectFirst();
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
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                }

                tableViewComponents.setItems(tableViewOrder.getItems().get(0).getComponentList());
                tableViewAdjustments.setItems(tableViewOrder.getItems().get(0).getAdjustmentList());
            }

        }
        tableViewOrder.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewComponents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAdjustments.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        personId.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        totalPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        componentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        adjustmentPrice.setCellFactory(TextFieldTableCell.forTableColumn(intStrConverter));
        orderStatus.setCellFactory(TextFieldTableCell.forTableColumn(booleanStringConverter));

    }

    private void threadDone(WorkerStateEvent e) {

        tableViewOrder.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();

        fileHandler.readAllFiles(stage);


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
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }

        tableViewComponents.setItems(tableViewOrder.getItems().get(0).getComponentList());
        tableViewAdjustments.setItems(tableViewOrder.getItems().get(0).getAdjustmentList());

        tableViewOrder.setDisable(false);
        tableViewComponents.setDisable(false);
        tableViewAdjustments.setDisable(false);

        buttonBack.setDisable(false);
        txtFilterOrders.setDisable(false);
        deleteOrder.setDisable(false);

        deleteComponent.setDisable(false);
        addComponent.setDisable(false);
        addAdjustment.setDisable(false);
        deleteAdjustrment.setDisable(false);
    }

    private void threadFailed(WorkerStateEvent event) {
        Exception e = (Exception) event.getSource().getException();
        lblMailOut.setText("Thread fail because of: " + e.getMessage());
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FileHandler.saveSelectedFile(stage);
        FXMLLoader loader = new FXMLLoader();
        ControllerOrdersAddComponent.toBeChanged = false;

        loader.setController("../superUser/ControllerSuperUser");
        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.
                newScene("Superuser", root, 470 ,300, event);
    }

    @FXML
    void onClickView(MouseEvent event) throws FileNotFoundException, UserAlreadyExistException {
        int outPersonId = 0;
        try{
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
        }catch (NullPointerException e){

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
    void btnAddAdjustment(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrderAddAdjustment.fxml"));
        OpenScene.newScene("Adjustments", root, 870, 460, event );
    }

    @FXML
    void btnDeleteAdjustment(ActionEvent event) {
        try{
            if(deleteElements.deleteAdjustments(tableViewAdjustments.getSelectionModel().getSelectedItems())){
                //Deletes tableRow(s)
                tableViewAdjustments.getItems().removeAll(tableViewAdjustments.getSelectionModel().getSelectedItems());
            }
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewAdjustments.refresh();
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
    void orderStatusEdited(TableColumn.CellEditEvent<TableView<Order>, Boolean> tableViewBooleanCellEditEvent) {
        Order o = tableViewOrder.getSelectionModel().getSelectedItem();
        boolean newOrderStatus = !tableViewBooleanCellEditEvent.getOldValue();
        try {
            if(newOrderStatus){
                if(Dialogs.showChooseDialog("Edit order status to finished?")){
                    o.setOrderStatus(newOrderStatus);
                }
            }
            else{
                if(Dialogs.showChooseDialog("Edit order status to not finished?")){
                    o.setOrderStatus(newOrderStatus);
                }
            }


        } catch (Exception e){
            tableViewOrder.getSelectionModel().clearSelection();
        }
        tableViewOrder.refresh();
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
                Dialogs.showErrorDialog("Invalid component price");
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

    @FXML
    void txtFilterTyped(KeyEvent event) {
            FilteredList<Order> filtered = new FilteredList<>(Lists.getOrders(), b -> true);

            txtFilterOrders.textProperty().addListener((observable, oldValue, newValue) -> {

                filtered.setPredicate(order -> list.filterOrderList(order, newValue));

                SortedList<Order> sorted = new SortedList<>(filtered);
                sorted.comparatorProperty().bind(tableViewOrder.comparatorProperty());
                tableViewOrder.setItems(sorted);
            });
        }


}


