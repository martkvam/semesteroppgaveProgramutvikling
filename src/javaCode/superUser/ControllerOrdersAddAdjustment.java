package javaCode.superUser;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.OpenScene;
import javaCode.objects.Adjustment;
import javaCode.objects.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOrdersAddAdjustment implements Initializable {

    public static boolean toBeChanged = false;
    private static Order selectedOrder;
    DeleteElements deleteElements = new DeleteElements();
    private Stage stage;
    private boolean notConfirmed = false;
    @FXML
    private TableView<Adjustment> tableViewChoosen;

    @FXML
    private TableView<Adjustment> tableViewPossible;

    @FXML
    private Button btnConfirm;

    public static Order getNewAdjustments() {
        return selectedOrder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConfirm.setDisable(true);
        selectedOrder = ControllerOrders.getSelectedOrder();

        for (Adjustment i : selectedOrder.getAdjustmentList()) {
            tableViewChoosen.getItems().add(i);
        }
        for (Adjustment i : Lists.getAdjustment()) {
            tableViewPossible.getItems().add(i);
        }
    }

    @FXML
    void btnAddAdjustment(ActionEvent event) {
        boolean alreadyChoosen = false;
        if (tableViewPossible.getSelectionModel().getSelectedItem() != null) {
            for (Adjustment i : tableViewChoosen.getItems()) {
                if (i.getAdjustmentType().equals(tableViewPossible.getSelectionModel().getSelectedItem().getAdjustmentType())) {
                    Dialogs.showErrorDialog("You have already choosen a component of this type");
                    alreadyChoosen = true;
                }
            }
            if (!alreadyChoosen) {
                tableViewChoosen.getItems().addAll(tableViewPossible.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged = true;
            }
        }
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (notConfirmed) {
            if (Dialogs.showChooseDialog("Are you sure you want to exit without saving changes")) {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
                OpenScene.
                        newScene("Edit orders", root, 1100, 730, event);
            }
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
            OpenScene.
                    newScene("Edit orders", root, 1100, 730, event);
        }
    }

    @FXML
    void btnConfirm(ActionEvent event) {
        if (Dialogs.showChooseDialog("Confirm changes?")) {
            selectedOrder.setAdjustmentList(tableViewChoosen.getItems());
            notConfirmed = false;
            toBeChanged = true;
        }
    }

    @FXML
    void btnRemove(ActionEvent event) {
        try {
            if (deleteElements.deleteAdjustments(tableViewChoosen.getSelectionModel().getSelectedItems())) {
                //Deletes tableRow(s)
                tableViewChoosen.getItems().removeAll(tableViewChoosen.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged = true;
            }
        } catch (IOException e) {
            Dialogs.showErrorDialog(e.getMessage());
        }
    }


}


