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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOrdersAddAdjustment implements Initializable {

    //Private order
    private static Order selectedOrder;
    //Public boolean variable to see if adjustment list has been changed
    public static boolean toBeChanged = false;
    //Private variable for confirming changes
    private boolean notConfirmed = false;
    DeleteElements deleteElements = new DeleteElements();

    @FXML
    private TableView<Adjustment> tableViewChosen;

    @FXML
    private TableView<Adjustment> tableViewPossible;

    @FXML
    private Button btnConfirm;

    //Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConfirm.setDisable(true);
        //Sets selectedOrder from the selected order in order gui
        selectedOrder = ControllerOrders.getSelectedOrder();

        //Set both tableviews. One with all possible, and one with chosen adjustments
        for(Adjustment i : selectedOrder.getAdjustmentList()){
            tableViewChosen.getItems().add(i);
        }
        for(Adjustment i : Lists.getAdjustment()){
            tableViewPossible.getItems().add(i);
        }
    }

    //Add adjustment to chosen adjustments
    @FXML
    void btnAddAdjustment(ActionEvent event) {
        boolean alreadyChosen = false;
        //If a tablerow has been selected the adjustment will be added if the adjustment type is not already chosen
        if(tableViewPossible.getSelectionModel().getSelectedItem()!=null){
            for(Adjustment i : tableViewChosen.getItems()){
                if(i.getAdjustmentType().equals(tableViewPossible.getSelectionModel().getSelectedItem().getAdjustmentType())){
                    Dialogs.showErrorDialog("You have already choosen a component of this type");
                    alreadyChosen= true;
                }
            }
            if(!alreadyChosen){
                tableViewChosen.getItems().addAll(tableViewPossible.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged=true;
            }
        }
    }

    //Back to order gui if changes is confirmed
    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(notConfirmed){
            if(Dialogs.showChooseDialog("Are you sure you want to exit without saving changes")){
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
                OpenScene.
                        newScene("Edit orders", root, 1100 ,730, event);
            }
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
            OpenScene.
                    newScene("Edit orders", root, 1100 ,730, event);
        }
    }

    //Confirm changes
    @FXML
    void btnConfirm(ActionEvent event) {
        if(Dialogs.showChooseDialog("Confirm changes?")){
            selectedOrder.setAdjustmentList(tableViewChosen.getItems());
            notConfirmed = false;
            toBeChanged = true;
        }
    }

    //Removes chosen adjustment
    @FXML
    void btnRemove(ActionEvent event) {
        try{
            if(deleteElements.deleteAdjustments(tableViewChosen.getSelectionModel().getSelectedItems())){
                //Deletes tableRow(s)
                tableViewChosen.getItems().removeAll(tableViewChosen.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged = true;
            }
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    //Returns new adjustment
    public static Order getNewAdjustments(){
        return selectedOrder;
    }

}


