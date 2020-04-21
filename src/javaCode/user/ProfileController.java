
package javaCode.user;

import javaCode.*;
import javaCode.InLog.Inlog;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sun.rmi.runtime.Log;

import java.io.*;
import java.io.FileWriter;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblEmail;

    @FXML
    private TableView<Order> finishedOrdersTV;

    @FXML
    private TableView<Order> ongoingOrdersTV;

    @FXML
    private TableView<Component> orderedComponentsTV;

    @FXML
    private TableView<Adjustment> orderedAdjustmentsTV;


    @FXML
    void changeOrder(ActionEvent event) {

    }

    @FXML
    void deleteOrder(ActionEvent event) {
        if(!ongoingOrdersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this ongoing order?"
                    , ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Order chosen = ongoingOrdersTV.getSelectionModel().getSelectedItem();
                Lists.getOngoingOrders().remove(chosen);
                ongoingOrdersTV.getItems().remove(chosen);
                ongoingOrdersTV.refresh();

                //Updating the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    void finishOrder(ActionEvent event) {
        if(!ongoingOrdersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to finish this order?"
                    , ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Order chosen = ongoingOrdersTV.getSelectionModel().getSelectedItem();

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

                finishedOrdersTV.getItems().add(chosen);
                finishedOrdersTV.refresh();
                ongoingOrdersTV.getItems().remove(chosen);
                ongoingOrdersTV.refresh();

                //Adding the order to the finsished orders-file.
                try {
                    String formattedOrder = OrderFormatter.formatOrder(chosen);
                    BufferedWriter update;
                    update = new BufferedWriter(new FileWriter("FinishedOrders.txt", true));
                    update.append(formattedOrder);
                    update.newLine();
                    update.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Updating the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    void updateInfo(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/updatePersonalInfo.fxml"));
        OpenScene.newScene("Change info", root, 300, 400, event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        ObservableList<Order> ongoing = FXCollections.observableArrayList();

        for (Order o : Lists.getOrders()){
            if(o.getPersonId() == LoggedIn.getId()){
                orders.add(o);
            }
        }

        for (Order ongoingOrder : Lists.getOngoingOrders()){
            if (ongoingOrder.getPersonId() == LoggedIn.getId()){
                ongoing.add(ongoingOrder);
            }
        }

        finishedOrdersTV.setItems(orders);
        ongoingOrdersTV.setItems(ongoing);

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


    }

    public void updateTVfinished(MouseEvent mouseEvent) {
            ObservableList<Component> componentList = finishedOrdersTV.getSelectionModel().getSelectedItem().getComponentList();
            ObservableList<Adjustment> adjustmentList = finishedOrdersTV.getSelectionModel().getSelectedItem().getAdjustmentList();
            orderedComponentsTV.setItems(componentList);
            orderedAdjustmentsTV.setItems(adjustmentList);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
    }

    public void updateTVongoing(MouseEvent mouseEvent) {
        if(Lists.getOngoingOrders() != null) {
            ObservableList<Component> componentList = ongoingOrdersTV.getSelectionModel().getSelectedItem().getComponentList();
            ObservableList<Adjustment> adjustmentList = ongoingOrdersTV.getSelectionModel().getSelectedItem().getAdjustmentList();
            orderedComponentsTV.setItems(componentList);
            orderedAdjustmentsTV.setItems(adjustmentList);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
        OpenScene.newScene("Order", root, 650, 650, actionEvent);
    }
}