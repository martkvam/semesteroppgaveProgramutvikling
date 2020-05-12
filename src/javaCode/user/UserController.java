package javaCode.user;

import javaCode.*;
import javaCode.InLog.LoggedIn;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @FXML
    private ComboBox<String> choosePackage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //If the GUI is opened in order to change an ongoing order
        if (ProfileController.toBeChanged) {
            ObservableList<Adjustment> adjustmenlist = ProfileController.changeOrder.getAdjustmentList();
            ObservableList<Component> componentlist = ProfileController.changeOrder.getComponentList();
            chosenComponents.addAll(componentlist);
            chosenCompTV.setItems(chosenComponents);
            chosenAdjustments.addAll(adjustmenlist);
            chosenAdjustTV.setItems(chosenAdjustments);

            String type = ProfileController.changedOrderCarType;
            chooseCarType.getSelectionModel().select(type);
            if(!chosenComponents.isEmpty()) {
                chooseCarType.setDisable(true);
            }
            adjustmentTV.setItems(Lists.getAdjustment());
            updatePrice();
        } else {
            adjustmentTV.setItems(Lists.getAdjustment());
        }

        chooseCol.setPromptText("Color: ");
        chooseCol.getItems().setAll("Red", "Black", "White", "Gray");

        //Setting options in the choiceboxes for car type and component type.
        chooseCarType.setPromptText("Car type: ");
        chooseComponent.setPromptText("Component type: ");
        chooseCarType.getItems().setAll(Methods.typeList(lists.getCars()));
        chooseComponent.getItems().setAll(Methods.componentList(lists.getComponents()));
        choosePackage.getItems().setAll("Basic+", "Sport", "Premium");
        choosePackage.setPromptText("Choose a base package: ");
    }


    //Method that sets a list in the tableview for components.
    //Is called upon when car type and component type is selected.
    @FXML
    void setList(ActionEvent event) {
        if(chooseCarType.getValue() != null) {

            //List that is diplayed in the tableview for components
            ObservableList<Component> outList = FXCollections.observableArrayList();

            //Gets the values from the choiceboxes
            String type = chooseCarType.getValue();
            String ID = "";
            for (Car car : lists.getCars()) {
                if (type.contains(car.getCarType())) {
                    ID = car.getCarID();
                }
            }
            String component = chooseComponent.getValue();

            //If an element in the list of components matches the value of both choiceboxes they are added
            //to the outlist.
            if (type != null && component != null) {
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
    }

    //Method for adding a component to the order.
    @FXML
    void addComponent(ActionEvent event) {
        if (!componentTV.getSelectionModel().isEmpty()) {
            Component chosen = componentTV.getSelectionModel().getSelectedItem();
            //Checking if a component of the same type is already chosen
            boolean found = false;
            for (Component c : chosenComponents) {
                if (chosen.getComponentType().equals(c.getComponentType())) {
                    found = true;
                }
            }

            if (!found) {
                chosenComponents.add(chosen);
                chosenCompTV.setItems(chosenComponents);
                chooseCarType.setDisable(true); //Disabling the car type choice-box to prevent the user from choosing
                //components that belong to different car types.

                updatePrice();
            } else {
                Dialogs.showErrorDialog("You have already added a component of this type. If you wish to select this" +
                        " component you will first have to remove the previously chosen " + chosen.getComponentType());
            }
        }
    }

    @FXML
    void removeComponent(ActionEvent event) {
        if (!chosenCompTV.getSelectionModel().isEmpty()) {
            Component chosen = chosenCompTV.getSelectionModel().getSelectedItem();
            chosenComponents.remove(chosen);
            chooseComponent.getItems().remove(chosen);
            chosenCompTV.refresh();

            if (chosenComponents.isEmpty()) {
                chooseCarType.setDisable(false);
            }
            updatePrice();
        }
    }

    public void addAdjust(ActionEvent actionEvent) {
        if (chooseCarType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please choose a car type before you add any adjustments");
            alert.showAndWait();
        } else {
            if (!adjustmentTV.getSelectionModel().isEmpty()) {
                Adjustment chosen = adjustmentTV.getSelectionModel().getSelectedItem();
                boolean found = false;
                for (Adjustment a : chosenAdjustments) {
                    if (a.getAdjustmentType().equals(chosen.getAdjustmentType())) {
                        found = true;
                    }
                }
                if (!found) {
                    chosenAdjustments.add(chosen);
                    chosenAdjustTV.setItems(chosenAdjustments);
                    updatePrice();
                } else {
                    Dialogs.showErrorDialog("You have already chosen an adjustment of the same type. " +
                            "If you wish to select this adjustment you will first have to remove the previously " +
                            "chosen " + chosen.getAdjustmentType());
                }
            }
        }

    }

    public void removeAdjust(ActionEvent actionEvent) {
        if (!chosenAdjustTV.getSelectionModel().isEmpty()) {
            Adjustment chosen = chosenAdjustTV.getSelectionModel().getSelectedItem();
            chosenAdjustments.remove(chosen);
            //adjustmentTV.getItems().add(chosen);
            updatePrice();
        }
    }

    //Method that opens the My Profile-GUI.
    public void myProfile(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (chosenComponents.isEmpty() && chosenAdjustments.isEmpty()) {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
            OpenScene.newScene("My profile", root, 550, 660, actionEvent);
            for (Adjustment a : Lists.getDeletedAdjustment()){
                System.out.println(a.getAdjustmentID());
            }
        }

        //If there is an ongoing order that has not been saved, the user will be informed of this.
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have not saved your choices, and if you continue your progress will be lost." +
                    " If you wish to continue this order at a later time, you will have to go back and click on 'save order'.",
                    ButtonType.CANCEL, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                //adjustmentTV.getItems().addAll(chosenAdjustments);
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
        if (chooseCarType.getValue() != null) {
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
        if (orderedAdjustments.isEmpty() && orderedComponents.isEmpty()) {
            Dialogs.showErrorDialog("Your order is empty");
            rightInput = false;
        }

        String color = "";
        if (chooseCol.getValue() != null) {
            color = chooseCol.getValue();
        } else {
            color += "Not chosen";
        }
        boolean correctComponents = true;
        for (Component c : orderedComponents){
            if (c.getComponentDescription().equals("No longer available")){
                correctComponents = false;
            }
        }
        if(!correctComponents){
            Dialogs.showErrorDialog("This order contains one or more components that no longer are available. " +
                    "Please remove these components before you save.");
        }

        boolean correctAdjustments = true;
        for (Adjustment a : orderedAdjustments){
            if (a.getAdjustmentDescription().equals("No longer available")){
                correctAdjustments = false;
            }
        }
        if (!correctAdjustments){
            Dialogs.showErrorDialog("This order contains one or more adjustments that no longer are available. "
            + "Please remove these adjustments before you save");
        }
        boolean correctCarID = false;
        for (Car c : Lists.getCars()){
            if(c.getCarID().equals(carId)){
                correctCarID = true;
            }
        }
        if(!correctCarID){
            Dialogs.showErrorDialog("This car type is no longer available");
        }

        //Creates a new order-object, and adds it to the list of ongoing orders.
        if (rightInput && correctComponents && correctAdjustments && correctCarID) {
            Order order = new Order("", persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, false);
            lists.addOngoingOrder(order);
            Path path = Paths.get("src/dataBase/OngoingOrders.txt");
            String formattedOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
            //Adds the ongoing order to the txt-file, and resets the GUI.
            try {
                FileWriter.WriteFile(path, formattedOrders);
                Dialogs.showSuccessDialog("Your order has been saved! You can view your ongoing orders on your profile");
                //  adjustmentTV.getItems().addAll(chosenAdjustments);
                chosenComponents.clear();
                chosenAdjustments.clear();
                chosenAdjustTV.refresh();
                adjustmentTV.refresh();
                ProfileController.toBeChanged = false;
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("Order", root, 1200, 700, actionEvent);
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Dialogs.showErrorDialog("Something went wrong.");
            }
        }
    }

    //Method for ordering a car
    public void order(ActionEvent actionEvent) {
        boolean rightInput = true;

        Date date = new Date();
        int price = totalprice;
        int persID = LoggedIn.getId();

        int largest = 0;
        for (Order o : Lists.getOrders()) {
            if (Integer.parseInt(o.getOrderNr()) > largest) {
                largest = Integer.parseInt(o.getOrderNr());
            }
        }

        //Finding the largest taken orderNr, and setting the new OrderNr to one larger.
        int orderNr = largest + 1;
        String newOrderNr = "" + orderNr;

        //Finding the ID that belongs to the chosen car type
        String carId = "";
        if (chooseCarType.getValue() != null) {
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


        if (orderedAdjustments.isEmpty() && orderedComponents.isEmpty()) {
            Dialogs.showErrorDialog("Your order is empty");
            rightInput = false;
        }

        String color = "";
        if (chooseCol.getValue() != null) {
            color = chooseCol.getValue();
        } else {
            Dialogs.showErrorDialog("Choose a color for the car!");
            rightInput = false;
        }

        boolean correctComponents = true;
        for (Component c : orderedComponents){
            if (c.getComponentDescription().equals("No longer available")){
                correctComponents = false;
            }
        }
        if (!correctComponents){
            Dialogs.showErrorDialog("This order contains one or more components that no longer are available. " +
                    "Please remove these components before you order");
        }

        boolean correctAdjustments = true;
        for (Adjustment a : orderedAdjustments){
            if (a.getAdjustmentDescription().equals("No longer available")){
                correctAdjustments = false;
            }
        }
        if (!correctAdjustments){
            Dialogs.showErrorDialog("This order contains one or more adjustments that no longer are available. "
                    + "Please remove these adjustments before you order");
        }

        boolean correctCarID = false;
        for (Car c : Lists.getCars()){
            if(c.getCarID().equals(carId)){
                correctCarID = true;
            }
        }
        if(!correctCarID){
            Dialogs.showErrorDialog("This car type is no longer available");
        }


        //If the input is correct, the order will be added to the list of finished orders and to the txt-file.
        if (rightInput && correctComponents && correctAdjustments && correctCarID) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to finish this order?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                Order order = new Order(newOrderNr, persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, true);
                lists.addOrder(order);
                Path path = Paths.get("src/dataBase/FinishedOrders.txt");
                String formattedOrders = OrderFormatter.formatOrders(Lists.getOrders());
                try {
                    FileWriter.WriteFile(path, formattedOrders);
                    Dialogs.showSuccessDialog("Your order was succesful!");
                    //adjustmentTV.getItems().addAll(chosenAdjustments);
                    chosenComponents.clear();
                    chosenAdjustments.clear();
                    chosenAdjustTV.refresh();
                    adjustmentTV.refresh();
                    ProfileController.toBeChanged = false;
                    Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                    OpenScene.newScene("Order", root, 1200, 700, actionEvent);
                } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    Dialogs.showErrorDialog("Something went wrong.");
                }
            }
        }
    }

    //Method that updates the price when a component or adjustment is chosen/removed.
    public void updatePrice() {
        totalprice = 0;
        for (Component c : chosenComponents) {
            totalprice += c.getComponentPrice();
        }
        for (Adjustment a : chosenAdjustments) {
            totalprice += a.getAdjustmentPrice();
        }

        String chosenCar = chooseCarType.getSelectionModel().getSelectedItem();
        if (chosenCar != null) {
            for (Car c : Lists.getCars()) {
                if (chosenCar.contains(c.getCarType())) {
                    totalprice += c.getPrice();
                }
            }
        }
        lblTotalprice.setText("Total price: " + totalprice + " kr");
    }

    public void logOut(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 600, 450, actionEvent);
    }

    public void setPackage(ActionEvent actionEvent) {
        boolean setPackage = true;
        if (chooseCarType.getSelectionModel().isEmpty()) {
            Dialogs.showErrorDialog("Start by choosing a car type");
        }
        if(!chosenAdjustTV.getItems().isEmpty() || !chosenCompTV.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will set your chosen components and" +
                    " adjustments to a predetermined package, and the progress you have made with your order might be lost." +
                    " Are you sure you want to continue?", ButtonType.CANCEL, ButtonType.YES);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.CANCEL)){
                setPackage = false;
            }
        }
        if (!choosePackage.getSelectionModel().isEmpty() && !chooseCarType.getSelectionModel().isEmpty() && setPackage) {
            Path path = Paths.get("src/dataBase/CarPackages.txt");
            ReadPackages read = new ReadPackages();
            String carID = "";
            String packageID = "";
            String chosenCar = chooseCarType.getSelectionModel().getSelectedItem();
            String chosenPackage = choosePackage.getSelectionModel().getSelectedItem();

            switch (chosenPackage) {
                case "Basic+":
                    packageID += "1";
                    break;
                case "Sport":
                    packageID += "2";
                    break;
                case "Premium":
                    packageID += "3";
                    break;
            }
            switch (chosenCar) {
                case "Petrol":
                    carID += "1";
                    break;
                case "Diesel":
                    carID += "2";
                    break;
                case "Hybrid":
                    carID += "3";
                    break;
                case "Electric":
                    carID += "4";
                    break;
            }

            try {
                read.read(path, carID, packageID);
                chosenAdjustments.setAll(Lists.getBasePackageAdjustments());
                chosenComponents.setAll(Lists.getBasePackageComponents());
                chosenCompTV.setItems(chosenComponents);
                chosenAdjustTV.setItems(chosenAdjustments);
                updatePrice();
                chooseCarType.setDisable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeAllComp(ActionEvent actionEvent) {
        if (!chosenCompTV.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove all the chosen components?",
                    ButtonType.CANCEL, ButtonType.YES);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)) {
                chosenCompTV.getItems().clear();
                chosenComponents.removeAll();
                chooseCarType.setDisable(false);
                updatePrice();
            }
        }
    }

    public void removeAllAdjust(ActionEvent actionEvent) {
        if (!chosenAdjustTV.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove all the chosen adjustments?",
                    ButtonType.CANCEL, ButtonType.YES);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)) {
                chosenAdjustTV.getItems().clear();
                chosenAdjustments.removeAll();
                updatePrice();
            }
        }
    }
}
