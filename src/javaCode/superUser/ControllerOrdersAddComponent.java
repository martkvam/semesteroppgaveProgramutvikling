package javaCode.superUser;

import javaCode.*;
import javaCode.InLog.LoggedIn;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOrdersAddComponent implements Initializable {
    private Stage stage;
    private static Order selectedOrder;
    public static boolean toBeChanged = false;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConfirm.setDisable(true);
        selectedOrder = ControllerOrders.getSelectedOrder();

        for(Component i : selectedOrder.getComponentList()){
            tableViewChosenElements.getItems().add(i);
        }
        for(Component i : Lists.getComponents()){
            if(i.getCarID().equals(selectedOrder.getCarId())){
                tableViewPossibleElements.getItems().add(i);
            }
        }
        for(Car i : Lists.getCars()){
            if(i.getCarID().equals(selectedOrder.getCarId())){
                lblCarType.setText(i.getCarType());
            }
        }
    }

    @FXML
    void btnAddComponent(ActionEvent event) {
        boolean alreadyChoosen = false;
        if(tableViewPossibleElements.getSelectionModel().getSelectedItem()!=null){
            for(Component i : tableViewChosenElements.getItems()){
                if(i.getComponentType().equals(tableViewPossibleElements.getSelectionModel().getSelectedItem().getComponentType())){
                    Dialogs.showErrorDialog("You have already choosen a component of this type");
                    alreadyChoosen= true;
                }
            }
            if(!alreadyChoosen){
                tableViewChosenElements.getItems().addAll(tableViewPossibleElements.getSelectionModel().getSelectedItems());
                btnConfirm.setDisable(false);
                btnConfirm.setStyle("-fx-background-color: #00ff00");
                notConfirmed = true;
                toBeChanged=true;
            }
        }
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(notConfirmed){
            if(Dialogs.showChooseDialog("Are you sure you want to exit without saving changes")){
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
                OpenScene.
                        newScene("Edit orders", root, 1000 ,730, event);
            }
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
            OpenScene.
                    newScene("Edit orders", root, 1000 ,730, event);
        }

    }
    @FXML
    void btnConfirm(ActionEvent event) {
        if(Dialogs.showChooseDialog("Confirm changes?")){
            selectedOrder.setComponentList(tableViewChosenElements.getItems());
            notConfirmed = false;
            toBeChanged = true;
        }
    }

    @FXML
    void btnChangeCar(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(Dialogs.showChooseDialog("If you want to change car type, the order will be deleted and you can start a new one for this user.\nDo you still want to change car?")){
            toBeChanged = true;
            LoggedIn.setId(Integer.toString(selectedOrder.getPersonId()));
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
            OpenScene.
                    newScene("Edit orders", root, 700 ,700, event);
        }
        else{

        }
    }

    @FXML
    void btnNewComponent(ActionEvent event) {

    }

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



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static Order getNewComponents(){
        return selectedOrder;
    }


}
