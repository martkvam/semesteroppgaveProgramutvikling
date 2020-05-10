
package javaCode.user;

import com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;
import javaCode.*;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    //Using these to change an ongoing order
    public static Order changeOrder;
    public static boolean toBeChanged;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblHeader;

    @FXML
    private TableView<Order> ordersTV;

    @FXML
    private TableView<Component> orderedComponentsTV;

    @FXML
    private TableView<Adjustment> orderedAdjustmentsTV;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFinish;

    @FXML
    private Button btnShowFinished;

    @FXML
    private Button btnShowOngoing;

    @FXML
    private Button btnExportFinished;

    @FXML
    private Button btnExportOngoing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Order> orders = FXCollections.observableArrayList();

        for (Order o : Lists.getOrders()){
            if(o.getPersonId() == LoggedIn.getId()){
                orders.add(o);
            }
        }

        ordersTV.setItems(orders);

        //Fills out the personal info section
        String ID = "" + LoggedIn.getId();
        try {
            String name = ReadUsers.getInfo(ID, "FirstName") + " " + ReadUsers.getInfo(ID, "LastName");
            String phone = ReadUsers.getInfo(ID, "Phone");
            String email = ReadUsers.getInfo(ID, "Email");
            lblName.setText("Name: " + name);
            lblPhone.setText("Phone: " + phone);
            lblEmail.setText("Email: " + email);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        toBeChanged = false;
    }

    @FXML  //Method for changing an ongoing order
    void changeOrder(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
       //This sets the static order object to the chosen order, which makes it possible to open it in the user-GUI.
        if(!ordersTV.getSelectionModel().isEmpty()) {
            Order chosen = ordersTV.getSelectionModel().getSelectedItem();
            changeOrder = chosen;
            toBeChanged = true;
            Lists.getOngoingOrders().remove(chosen);

            //Updating the ongoing orders-file.
            try {
                String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
            OpenScene.newScene("Order", root, 650, 700, event);
        }
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        if(!ordersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this ongoing order?"
                    , ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Order chosen = ordersTV.getSelectionModel().getSelectedItem();
                Lists.getOngoingOrders().remove(chosen);
                ordersTV.getItems().remove(chosen);
                ordersTV.refresh();

                //Updating the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML //Method for finishing ongoing orders
    void finishOrder(Event event) {
        if(!ordersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
            //If color is not chosen a choice dialog will pop up.
            String [] color = {"Red", "Black", "White", "Gray"};
            ChoiceDialog<String> choice = new ChoiceDialog(color[1], color);
            if(ordersTV.getSelectionModel().getSelectedItem().getCarColor().equals("Not chosen")){
                choice.setTitle("Finish order");
                choice.setContentText("Please choose a color for the car before you finish your order: ");
                choice.showAndWait();
                String chosenColor = choice.getSelectedItem();
                ordersTV.getSelectionModel().getSelectedItem().setCarColor(chosenColor);
            }
            //If the order is ready
            else {
               alert.setContentText("Are you sure you want to finish this order?");
               alert.showAndWait();
            }

            if (alert.getResult() == ButtonType.YES || !choice.getResult().isEmpty()) {
                Order chosen = ordersTV.getSelectionModel().getSelectedItem();

                //Finding the largest existing orderNr, and setting the new orderNr to largest + 1.
                int largest = 0;
                for (Order o : Lists.getOrders()) {
                    if (Integer.parseInt(o.getOrderNr()) > largest) {
                        largest = Integer.parseInt(o.getOrderNr());
                    }
                }
                int orderNr = largest + 1;
                String newOrderNr = "" + orderNr;
                chosen.setOrderNr(newOrderNr);

                chosen.setOrderStatus(true);

                Lists.getOrders().add(chosen);
                Lists.getOngoingOrders().remove(chosen);

                ordersTV.getItems().remove(chosen);

                //Adding the order to the finsished orders-file.
                try {
                    String formattedOrder = OrderFormatter.formatOrder(chosen);
                    BufferedWriter update;
                    update = new BufferedWriter(new FileWriter("src/dataBase/FinishedOrders.txt", true));
                    update.append(formattedOrder);
                    update.newLine();
                    update.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Removing the order from the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTVfinished(event);
            }
        }

    }

    @FXML //Opens the GUI for updating personal info.
    void updateInfo(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/updatePersonalInfo.fxml"));
        OpenScene.newScene("Change info", root, 300, 400, event);
    }

    //Method for showing the components and adjustments in a selected order
    public void updateTVfinished(Event mouseEvent) {
        if(!ordersTV.getSelectionModel().isEmpty()) {
            ObservableList<Component> componentList = ordersTV.getSelectionModel().getSelectedItem().getComponentList();
            ObservableList<Adjustment> adjustmentList = ordersTV.getSelectionModel().getSelectedItem().getAdjustmentList();
            orderedComponentsTV.setItems(componentList);
            orderedAdjustmentsTV.setItems(adjustmentList);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
        }
        //If there is no order selected, the tableviews for components and adjustments will be set to empty.
        if(ordersTV.getSelectionModel().isEmpty()){
            ObservableList<Component> components = FXCollections.observableArrayList();
            ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
            orderedComponentsTV.setItems(components);
            orderedAdjustmentsTV.setItems(adjustments);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
        OpenScene.newScene("Order", root, 1200, 700, actionEvent);
    }

    //Shows finished orders
    public void showFinsihed(Event event) {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        for (Order o : Lists.getOrders()){
            if(o.getPersonId() == LoggedIn.getId()){
                orders.add(o);
            }
        }
        ordersTV.setItems(orders);

        btnChange.setVisible(false);
        btnFinish.setVisible(false);
        btnDelete.setVisible(false);
        btnShowFinished.setVisible(false);
        btnShowOngoing.setVisible(true);
        btnExportFinished.setVisible(true);

        lblHeader.setText("Finished orders (click on an order to see content)");
        updateTVfinished(event);

    }

    //Shows ongoing orders
    public void showOngoing(Event event) {
        ObservableList<Order> ongoing = FXCollections.observableArrayList();

        for (Order ongoingOrder : Lists.getOngoingOrders()){
            if (ongoingOrder.getPersonId() == LoggedIn.getId()){
                ongoing.add(ongoingOrder);
            }
        }
        ordersTV.setItems(ongoing);

        btnDelete.setVisible(true);
        btnFinish.setVisible(true);
        btnChange.setVisible(true);
        btnShowFinished.setVisible(true);
        btnShowOngoing.setVisible(false);
        btnExportFinished.setVisible(false);

        lblHeader.setText("Ongoing orders (click on an order to see content)");
        updateTVfinished(event);
    }

    public void btnExportFinishedOnClick(ActionEvent actionEvent) throws IOException {
        if(ordersTV.getItems().size() != 0) {
            javaCode.ReaderWriter.Order.fileWriterExcel.writeExcel(ordersTV.getItems());
        } else{
            Dialogs.showErrorDialog("List is empty");
        }
    }
}