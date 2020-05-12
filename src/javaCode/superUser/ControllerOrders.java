package javaCode.superUser;

import javaCode.*;
import javaCode.InLog.ReadUsers;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javaCode.objects.User;
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
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerOrders implements Initializable {

    //Sets up different elements
    Stage stage = new Stage();
    FileHandler fileHandler = new FileHandler();
    DeleteElements deleteElements = new DeleteElements();
    Lists list = new Lists();

    private static Order selectedOrder;
    private static int firstInlog;

    //Converters
    private ConverterErrorHandler.IntegerStringConverter intStrConverter = new ConverterErrorHandler.IntegerStringConverter();
    private ConverterErrorHandler.BooleanStringConverter booleanStringConverter = new ConverterErrorHandler.BooleanStringConverter();

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

    //Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //If a component list has been altered
        if(ControllerOrdersAddComponent.toBeChanged){

            //Edit the selected order and sets labels
            Order editedOrder = ControllerOrdersAddComponent.getNewComponents();

            for(Order i : tableViewOrder.getItems()){
                if(i.getOrderNr().equals(editedOrder.getOrderNr())){
                    i.getComponentList().clear();
                    i.setComponentList(editedOrder.getComponentList());
                }
            }
            //Set selected order
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
            //Sets selected order and tableviews
            for(Order i : Lists.getOrders()){
                if (tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr().equals(i.getOrderNr())) {
                    tableViewComponents.setItems(selectedOrder.getComponentList());
                    tableViewAdjustments.setItems(selectedOrder.getAdjustmentList());
                    outPersonId = i.getPersonId();
                }
            }
            //Sets new finished date
            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);

        }
        //If a adjustment list has been altered
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

            //Sets selected order and tableviews
            for(Order i : Lists.getOrders()){
                if (tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr().equals(i.getOrderNr())) {
                    tableViewComponents.setItems(i.getComponentList());
                    tableViewAdjustments.setItems(i.getAdjustmentList());
                    outPersonId = i.getPersonId();
                }
            }
            //Sets new finished date
            Date dateNow = new Date();
            tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
        }

        else{
            if(firstInlog == 0){
                //First inlog. Starts delay thread
                newThread delayThread = new newThread();
                delayThread.setOnSucceeded(this::threadDone);
                delayThread.setOnFailed(this::threadFailed);
                Thread th = new Thread(delayThread);

                //While delay thread is running the gui wil not have any functional buttons or tableviews
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

                FileHandler.readAllFiles(stage);


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
    //Method that wil run when thread is done. Reads files and gives all the data to tableviews and stops disabling of gui elements
    private void threadDone(WorkerStateEvent e) {

        tableViewOrder.getItems().clear();
        tableViewComponents.getItems().clear();
        tableViewAdjustments.getItems().clear();

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
    //Method that wil run when thread is failed
    private void threadFailed(WorkerStateEvent event) {
        Exception e = (Exception) event.getSource().getException();
        lblMailOut.setText("The exception is: " + e.getMessage());
    }

    //Back to last gui
    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FileHandler.saveSelectedFile(stage);
        FXMLLoader loader = new FXMLLoader();
        ControllerOrdersAddComponent.toBeChanged = false;
        ControllerOrdersAddAdjustment.toBeChanged = false;

        loader.setController("../superUser/ControllerSuperUser");
        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.
                newScene("Superuser", root, 470 ,300, event);
    }


    //While choosing a order, the other tableviews wil show the components and adjustments in the order
    @FXML
    void onClickView(MouseEvent event) throws FileNotFoundException{
        int outPersonId = 0;
        try{
            selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
            //Matching selected order with order list. Prints the components and adjustments in order
            for(Order i : Lists.getOrders()){
                if (tableViewOrder.getSelectionModel().getSelectedItem().getOrderNr().equals(i.getOrderNr())) {
                    tableViewComponents.setItems(i.getComponentList());
                    tableViewAdjustments.setItems(i.getAdjustmentList());
                    outPersonId = i.getPersonId();
                    break;
                }
            }
            for (User i : ReadUsers.getUserList()) {
                if (i.getId() == outPersonId) {
                    lblOutName.setText(i.getFirstName() + " " + i.getLastName());
                    lblOutPhone.setText((i.getPhone()));
                    lblMailOut.setText(i.getEmail());
                }
            }
        } catch (NullPointerException ignored) {

        }
    }
    //Deletes order
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

    //Add component to order in new gui
    @FXML
    void btnAddComponent(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserAddComponentAndAdjustment.fxml"));
        OpenScene.newScene("Components", root, 870, 460, event );

       }
    //Delete selected component
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

    //Add adjustment to order in new gui
    @FXML
    void btnAddAdjustment(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrderAddAdjustment.fxml"));
        OpenScene.newScene("Adjustments", root, 870, 460, event );
    }

    //Delete selected adjustment
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

    //Edit table columns for tableview order
    //Edit car id
    @FXML
    void carIdEdited(TableColumn.CellEditEvent<Order, String> event) {
        try {
            event.getRowValue().setCarId(event.getNewValue());
        } catch (IllegalArgumentException ignored) {

        }
        tableViewOrder.refresh();

    }

    //Edit totprice
    @FXML
    void totPriceEdited(TableColumn.CellEditEvent<Order, Integer> event) {
        try{
            //Calling price dialog
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
    //Edit car color
    @FXML
    void carColorEdited(TableColumn.CellEditEvent<Order, String> event) {
        try{
            event.getRowValue().setCarColor(event.getNewValue());
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        tableViewOrder.refresh();
    }
    //Edit order status
    @FXML
    void orderStatusEdited(TableColumn.CellEditEvent<TableView<Order>, Boolean> tableViewBooleanCellEditEvent) {
        Order o = tableViewOrder.getSelectionModel().getSelectedItem();
        //Finds the opposite of the old order status
        boolean newOrderStatus = !tableViewBooleanCellEditEvent.getOldValue();
        try {
            //Sets new order status
            if(newOrderStatus){
                if(Dialogs.showChooseDialog("Edit order status to finished?")){
                    o.setOrderStatus(true);
                }
            }
            else{
                if(Dialogs.showChooseDialog("Edit order status to not finished?")){
                    o.setOrderStatus(false);
                }
            }


        } catch (Exception e){
            tableViewOrder.getSelectionModel().clearSelection();
        }
        tableViewOrder.refresh();
    }

    //Edit component tableview
    //Component price edited
    @FXML
    void componentPriceEdited(TableColumn.CellEditEvent<Component, Integer> event) {
        try{
            //Calling price dialog
            int price = EditOrders.editOrderPrice(event.getOldValue());
            if(price <= 0) {
                Dialogs.showErrorDialog("Invalid component price");
            }
            else {
                event.getRowValue().setComponentPrice(price);
                //Sets total price calculated by car, components and adjustments
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

    //Edit adjustment tableview
    //Edit adjustment price
    @FXML
    void adjustmentPriceEdited(TableColumn.CellEditEvent<Adjustment, Integer> event) {
        try{
            //Calling price dialog
            int price = EditOrders.editOrderPrice(event.getOldValue());
            if(price <= 0) {
                Dialogs.showErrorDialog("Invalid total price");
            }

            else {
                event.getRowValue().setAdjustmentPrice(price);
                Date dateNow = new Date();
                tableViewOrder.getSelectionModel().getSelectedItem().setOrderFinished(dateNow);
                //Sets total price calculated by car, components and adjustments
                tableViewOrder.getSelectionModel().getSelectedItem().setTotPrice(EditOrders.recalculateTotalPrice(tableViewOrder.getSelectionModel().getSelectedItem()));
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }

        tableViewAdjustments.refresh();
        tableViewOrder.refresh();
    }
    //Returns selected order
    public static Order getSelectedOrder(){
        return selectedOrder;
    }

    //Updates and filters order tableview by key event
    @FXML
    void txtFilterTyped(KeyEvent event) {
            FilteredList<Order> filteredList = new FilteredList<>(Lists.getOrders(), a -> true);

            //Event listener on input field with lambda expression that sets a new filtered list by method call
            txtFilterOrders.textProperty().addListener((observable, oldValue, newValue) -> {

                filteredList.setPredicate(order -> list.filterOrderList(order, newValue));

                SortedList<Order> sorted = new SortedList<>(filteredList);
                sorted.comparatorProperty().bind(tableViewOrder.comparatorProperty());
                tableViewOrder.setItems(sorted);
            });
        }
}
