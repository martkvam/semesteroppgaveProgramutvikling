package javaCode.user;

import javaCode.*;
import javaCode.InLog.LoggedIn;
import javaCode.superUser.ControllerOrdersAddComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    private Lists lists = new Lists();
    ObservableList<Component> chosenComponents = FXCollections.observableArrayList();
    ObservableList<Adjustment> chosenAdjustments = FXCollections.observableArrayList();
    private int totalprice;

    @FXML
    private TableView<Component> componentTV;

    @FXML
    private TableView<Component> chosenCompTV;

    @FXML
    private ComboBox<String> chooseCarType;

    @FXML
    private Button addBtn;

    @FXML
    private Button btnRemove;

    @FXML
    private ComboBox<String> chooseComponent;

    @FXML
    private Label lblTotalprice;

    @FXML
    private TableView<Adjustment> adjustmentTV;

    @FXML
    private TableView<Adjustment> chosenAdjustTV;

    @FXML
    private Button addAdjust;

    @FXML
    private Button removeAdjust;

    @FXML
    private ComboBox<String> chooseCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //If the GUI is opened in order to change an ongoing order
        if(ProfileController.toBeChanged){
            ObservableList<Adjustment> adjustmenlist = ProfileController.changeOrder.getAdjustmentList();
            ObservableList<Component> componentlist = ProfileController.changeOrder.getComponentList();
            chosenComponents.addAll(componentlist);
            chosenCompTV.setItems(chosenComponents);
            chosenAdjustments.addAll(adjustmenlist);
            chosenAdjustTV.setItems(chosenAdjustments);

            String type = "";
            String ID = chosenComponents.get(0).getCarID();
            for (Car c : Lists.getCars()){
                if (c.getCarID().equals(ID)){
                    type = c.getCarType();
                }
            }
            chooseCarType.getSelectionModel().select(type);
            chooseCarType.setDisable(true);

            adjustmentTV.setItems(Lists.getAdjustment());
            for (Adjustment a : chosenAdjustments){
                adjustmentTV.getItems().remove(a);
            }
            updatePrice();
        }

        else {
            adjustmentTV.setItems(Lists.getAdjustment());
        }

        chooseCol.setPromptText("Color: ");
        chooseCol.getItems().setAll("Red", "Black", "White", "Gray");

        //Setting options in the choiceboxes for car type and component type.
        chooseCarType.setPromptText("Car type: ");
        chooseComponent.setPromptText("Component type: ");
        chooseCarType.getItems().setAll(Methods.typeList(lists.getCars()));
        chooseComponent.getItems().setAll(Methods.componentList(lists.getComponents()));
    }


    //Method that sets a list in the tableview for components.
    //Is called upon when car type and component type is selected.
    @FXML
    void setList(ActionEvent event) {
        String chosen = chooseCarType.getSelectionModel().getSelectedItem();

       /* int totalprice = 0;
        for (Car car : Lists.getCars()){
            if(chosen.contains(car.getCarType())){
                totalprice += car.getPrice();
            }
        }
        lblTotalprice.setText("Total price: " + totalprice);*/

        //List that is diplayed in the tableview for components
        ObservableList<Component> outList = FXCollections.observableArrayList();

        //Gets the values from the choiceboxes
        String type = chooseCarType.getValue();
        String ID = "";
        for (Car car : lists.getCars()){
            if (type.contains(car.getCarType())){
                ID = car.getCarID();
            }
        }
        String component = chooseComponent.getValue();

        //If an element in the list of components matches the value of both choiceboxes they are added
        //to the outlist.
        if(type != null && component != null) {
            for (int i = 0; i < lists.getComponents().size(); i++) {
                if (ID.equals(lists.getComponents().get(i).getCarID()) && component.equals(lists.getComponents().get(i).getComponentType())) {
                    outList.add(lists.getComponents().get(i));
                }
            }
        }

        //The outlist is displayed in the tableview
        componentTV.setItems(outList);
        componentTV.refresh();
        updatePrice();
    }

    //Method for adding a component to the order.
    @FXML
    void addComponent(ActionEvent event) {
        Component chosen = componentTV.getSelectionModel().getSelectedItem();
        //Checking if a component of the same type is already chosen
        boolean found = false;
        for (Component c : chosenComponents){
            if(chosen.getComponentType().equals(c.getComponentType())){
                found = true;
            }
        }

        if(!found) {
            chosenComponents.add(chosen);
            chosenCompTV.setItems(chosenComponents);
            chooseCarType.setDisable(true); //Disabling the car type choice-box to prevent the user from choosing
            //components that belong to different car types.

           /* int totalprice = 0;
            for (Adjustment adj : chosenAdjustments) {
                totalprice += adj.getAdjustmentPrice();
            }
            for (Component c : chosenComponents) {
                totalprice += c.getComponentPrice();
            }

            lblTotalprice.setText("Total price: " + totalprice);*/
           updatePrice();
        }
        else{
            Dialogs.showErrorDialog("You have already added a component of this type. If you wish to select this" +
                    " component you will first have to remove the previously chosen " + chosen.getComponentType());
        }
    }

    @FXML
    void removeComponent(ActionEvent event) {
        Component chosen = chosenCompTV.getSelectionModel().getSelectedItem();
        chosenComponents.remove(chosen);
        chooseComponent.getItems().remove(chosen);
        chosenCompTV.refresh();

        if(chosenComponents.isEmpty()){
            chooseCarType.setDisable(false);
        }

        /*int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);*/

        updatePrice();
    }

    public void addAdjust(ActionEvent actionEvent) {
        Adjustment chosen = adjustmentTV.getSelectionModel().getSelectedItem();
        chosenAdjustments.add(chosen);
        chosenAdjustTV.setItems(chosenAdjustments);

        /*int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);*/

        for(Adjustment a : chosenAdjustments){
            adjustmentTV.getItems().remove(a); //Removing the chosen adjustment from the adjustment-tableview
            //to prevent the user from selecting the same adjustment several times.
        }
        updatePrice();

    }

    public void removeAdjust(ActionEvent actionEvent) {
        Adjustment chosen = chosenAdjustTV.getSelectionModel().getSelectedItem();
        int price = chosen.getAdjustmentPrice();
        chosenAdjustments.remove(chosen);
        adjustmentTV.getItems().add(chosen);
        updatePrice();

       /* int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);*/
    }

    //Method that opens the My Profile-GUI.
    public void myProfile(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(chosenComponents.isEmpty() && chosenAdjustments.isEmpty()) {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
            OpenScene.newScene("My profile", root, 610, 650, actionEvent);
        }
        //If there is an ongoing order that has not been saved, the user will be informed of this.
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have not saved your choices, and if you continue your progress will be lost." +
                    " If you wish to continue this order at a later time, you will have to go back and click on 'save order'.",
                    ButtonType.CANCEL, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)){
                adjustmentTV.getItems().addAll(chosenAdjustments);
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
                OpenScene.newScene("My profile", root, 610, 650, actionEvent);
            }
        }
    }

    //Method that saves the ongoing order, so the user can access it at a later time
    public void saveChoices(ActionEvent actionEvent) {
        boolean rightInput = true;
        Date date = new Date();

        int price = totalprice;

        int persID = LoggedIn.getId();

        String carId = "";
        if(chooseCarType.getValue() != null) {
            for (Car car : Lists.getCars()) {
                if (chooseCarType.getValue().contains(car.getCarType())) {
                    carId = car.getCarID();
                }
            }
        } else {
            Dialogs.showErrorDialog("Choose a car type");
            rightInput = false;
        }

        ObservableList<Component> orderedComponents = FXCollections.observableArrayList();
        ObservableList<Adjustment> orderedAdjustments = FXCollections.observableArrayList();
        orderedComponents.setAll(chosenComponents);
        orderedAdjustments.setAll(chosenAdjustments);

        //Makes sure the order is not empty
        if (orderedAdjustments.isEmpty() && orderedComponents.isEmpty()){
            Dialogs.showErrorDialog("Your order is empty");
            rightInput = false;
        }

        String color = "";
        if(chooseCol.getValue() != null) {
            color = chooseCol.getValue();
        }
        else {
            color += "Not chosen";
        }

        //Creates a new order-object, and adds it to the list of ongoing orders.
        if(rightInput) {
            Order order = new Order("", persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, false);
            lists.addOngoingOrder(order);
            Path path = Paths.get("src/dataBase/OngoingOrders.txt");
            String formattedOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
            //Adds the ongoing order to the txt-file, and resets the GUI.
            try {
                FileWriter.WriteFile(path, formattedOrders);
                Dialogs.showSuccessDialog("Your order has been saved! You can view your ongoing orders on your profile");
                adjustmentTV.getItems().addAll(chosenAdjustments);
                chosenComponents.clear();
                chosenAdjustments.clear();
                chosenAdjustTV.refresh();
                adjustmentTV.refresh();
                ProfileController.toBeChanged = false;
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("Order", root, 650, 700, actionEvent);
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Dialogs.showErrorDialog("Something went wrong.");
            }
        }
    }

    public void order(ActionEvent actionEvent){
        boolean rightInput = true;

        Date date = new Date();

        int price = totalprice;

        int persID = LoggedIn.getId();

        int largest = 0;
        for (Order o : Lists.getOrders()){
            if(Integer.parseInt(o.getOrderNr()) > largest){
                largest = Integer.parseInt(o.getOrderNr());
            }
        }

        int orderNr = largest + 1;
        String newOrderNr = "" + orderNr;
        String carId = "";
        if(chooseCarType.getValue() != null) {
            for (Car car : Lists.getCars()) {
                if (chooseCarType.getValue().contains(car.getCarType())) {
                    carId = car.getCarID();
                }
            }
        } else {
            Dialogs.showErrorDialog("Choose a car type");
            rightInput = false;
        }

        ObservableList<Component> orderedComponents = FXCollections.observableArrayList();
        ObservableList<Adjustment> orderedAdjustments = FXCollections.observableArrayList();
        orderedComponents.setAll(chosenComponents);
        orderedAdjustments.setAll(chosenAdjustments);


        if (orderedAdjustments.isEmpty() && orderedComponents.isEmpty()){
            Dialogs.showErrorDialog("Your order is empty");
            rightInput = false;
        }

        String color = "";
        if(chooseCol.getValue() != null) {
            color = chooseCol.getValue();
        }
        else {
            Dialogs.showErrorDialog("Choose a color for the car!");
            rightInput = false;
        }

        //If the input is correct, the order will be added to the list of finished orders and to the txt-file.
        if(rightInput) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to finish this order?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if(alert.getResult().equals(ButtonType.OK)) {
                Order order = new Order(newOrderNr, persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, true);
                lists.addOrder(order);
                Path path = Paths.get("src/dataBase/FinishedOrders.txt");
                String formattedOrders = OrderFormatter.formatOrders(Lists.getOrders());
                try {
                    FileWriter.WriteFile(path, formattedOrders);
                    Dialogs.showSuccessDialog("Your order was succesful!");
                    adjustmentTV.getItems().addAll(chosenAdjustments);
                    chosenComponents.clear();
                    chosenAdjustments.clear();
                    chosenAdjustTV.refresh();
                    adjustmentTV.refresh();
                    ProfileController.toBeChanged = false;
                    Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                    OpenScene.newScene("Order", root, 650, 700, actionEvent);
                } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    Dialogs.showErrorDialog("Something went wrong.");
                }
            }
        }
    }

    public void updatePrice (){
        totalprice = 0;
        for (Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        for (Adjustment a : chosenAdjustments){
            totalprice += a.getAdjustmentPrice();
        }

        String chosenCar = chooseCarType.getSelectionModel().getSelectedItem();
        for (Car c : Lists.getCars()){
            if(chosenCar.contains(c.getCarType())){
                totalprice += c.getPrice();
            }
        }
        lblTotalprice.setText("Total price: " + totalprice);
    }

    public void logOut(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 650, 650, actionEvent);
    }
}
