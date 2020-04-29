
package javaCode.user;

import com.sun.deploy.security.SelectableSecurityManager;
import javaCode.*;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
    private TableView<Order> finishedOrdersTV;

    @FXML
    private TableView<Order> ongoingOrdersTV;

    @FXML
    private TableView<Component> orderedComponentsTV;

    @FXML
    private TableView<Adjustment> orderedAdjustmentsTV;

    @FXML
    public Button btnChange;

    @FXML
    private Button btnExportFinished;

    @FXML
    private Button btnExportOngoing;


    @FXML
    void changeOrder(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Order chosen = ongoingOrdersTV.getSelectionModel().getSelectedItem();
        changeOrder = chosen;
        toBeChanged = true;
        Lists.getOngoingOrders().remove(chosen);
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
        OpenScene.newScene("Order", root, 650, 700, event);
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
            //If color is not chosen
            String [] color = {"Red", "Black", "White", "Gray"};
            ChoiceDialog<String> choice = new ChoiceDialog(color[1], color);
            if(ongoingOrdersTV.getSelectionModel().getSelectedItem().getCarColor().equals("Not chosen")){
                choice.setTitle("Finish order");
                choice.setContentText("Please choose a color for the car before you finish your order: ");
                choice.showAndWait();
                String chosenColor = choice.getSelectedItem();
                ongoingOrdersTV.getSelectionModel().getSelectedItem().setCarColor(chosenColor);
            }
            //If the order is ready
            else {
               alert.setContentText("Are you sure you want to finish this order?");
               alert.showAndWait();
            }
            if (alert.getResult() == ButtonType.YES || !choice.getResult().isEmpty()) {
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

    toBeChanged = false;
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
        OpenScene.newScene("Order", root, 650, 700, actionEvent);
    }

    public void btnExportFinishedOnClick(ActionEvent actionEvent) throws IOException {
        if(finishedOrdersTV.getItems().size() != 0) {
            Excel.writeExcel(finishedOrdersTV.getItems());
        } else{
            Dialogs.showErrorDialog("List is empty");
        }

    }

    public void btnExportOngoingOnClick(ActionEvent actionEvent) throws IOException {
        if (!ongoingOrdersTV.getItems().isEmpty()){
        Excel.writeExcel(ongoingOrdersTV.getItems());
        }else {
            Dialogs.showErrorDialog("List is empty");
        }
    }
}