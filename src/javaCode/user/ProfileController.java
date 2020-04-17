
package javaCode.user;

import javaCode.*;
import javaCode.InLog.Inlog;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

    }

    @FXML
    void finishOrder(ActionEvent event) {

    }

    @FXML
    void updateInfo(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        finishedOrdersTV.setItems(Lists.getOrders());
        ongoingOrdersTV.setItems(Lists.getOngoingOrders());

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