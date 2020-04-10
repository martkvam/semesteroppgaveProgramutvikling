
package javaCode.user;

import javaCode.Adjustment;
import javaCode.Component;
import javaCode.Lists;
import javaCode.Order;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

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
        ObservableList<Component> componentList = ongoingOrdersTV.getSelectionModel().getSelectedItem().getComponentList();
        ObservableList<Adjustment> adjustmentList = ongoingOrdersTV.getSelectionModel().getSelectedItem().getAdjustmentList();
        orderedComponentsTV.setItems(componentList);
        orderedAdjustmentsTV.setItems(adjustmentList);
        orderedComponentsTV.refresh();
        orderedAdjustmentsTV.refresh();
    }
}