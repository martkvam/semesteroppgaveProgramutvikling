package javaCode.superUser;

import javaCode.Dialogs;
import javaCode.InLog.LoggedIn;
import javaCode.Lists;
import javaCode.OpenScene;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOrdersAddComponent implements Initializable {
    //Private order
    private static Order selectedOrder;
    //Public boolean variable to see if adjustment list has been changed
    public static boolean toBeChanged = false;
    //Private variable for confirming changes
    private boolean notConfirmed = false;
    DeleteElements deleteElements = new DeleteElements();


    @FXML
    private AnchorPane Ap;

    @FXML
    private TableView<Component> tableViewPossibleElements;

    @FXML
    private TableView<Component> tableViewChosenElements;

    @FXML
    private Label lblCarType;

    @FXML
    private Button btnConfirm;

    //Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConfirm.setDisable(true);
        //Sets selectedOrder from the selected order in order gui
        selectedOrder = ControllerOrders.getSelectedOrder();

        //Set both tableviews. One with all possible, and one with chosen components
        for(Component i : selectedOrder.getComponentList()){
            tableViewChosenElements.getItems().add(i);
        }
        for(Component i : Lists.getComponents()){
            if(i.getCarID().equals(selectedOrder.getCarId())){
                tableViewPossibleElements.getItems().add(i);
            }
        }
        //Sets selected car type
        for(Car i : Lists.getCars()){
            if(i.getCarID().equals(selectedOrder.getCarId())){
                lblCarType.setText(i.getCarType());
            }
        }
    }

    //Add chosen component to the chosen tableview
    @FXML
    void btnAddComponent(ActionEvent event) {
        boolean alreadyChosen = false;
        //If a tablerow has been selected the component will be added if the component type is not already chosen
        if(tableViewPossibleElements.getSelectionModel().getSelectedItem()!=null){
            for(Component i : tableViewChosenElements.getItems()){
                if(i.getComponentType().equals(tableViewPossibleElements.getSelectionModel().getSelectedItem().getComponentType())){
                    Dialogs.showErrorDialog("You have already chosen a component of this type");
                    alreadyChosen= true;
                }
            }
            if(!alreadyChosen){
                tableViewChosenElements.getItems().addAll(tableViewPossibleElements.getSelectionModel().getSelectedItems());
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
            selectedOrder.setComponentList(tableViewChosenElements.getItems());
            notConfirmed = false;
            toBeChanged = true;
        }
    }

    //Changes selected car type. If the change is confirmed the chosen components are deleted
    @FXML
    void btnChangeCar(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(Dialogs.showChooseDialog("If you want to change car type, the order will be deleted and you can start a new one for this user.\nDo you still want to change car?")){
            toBeChanged = true;
            LoggedIn.setId(Integer.toString(selectedOrder.getPersonId()));
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
            OpenScene.
                    newScene("Edit orders", root, 700 ,700, event);
        }
    }

    //Removes chosen adjustment
    @FXML
    void btnRemoveComponent(ActionEvent event) {
        try{
            if(deleteElements.deleteComponents(tableViewChosenElements.getSelectionModel().getSelectedItems())){
                //Deletes tableRow(s)
                tableViewChosenElements.getItems().removeAll(tableViewChosenElements.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged = true;
            }
        }catch (IOException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
    }

    //Returns new component
    public static Order getNewComponents(){
        return selectedOrder;
    }


}
