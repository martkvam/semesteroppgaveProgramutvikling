package javaCode.user;

import javaCode.*;
import javaCode.InLog.LoggedIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    FileHandler handler = new FileHandler();
    private Lists lists = new Lists();
    ObservableList<Component> chosenComponents = FXCollections.observableArrayList();
    ObservableList<Adjustment> chosenAdjustments = FXCollections.observableArrayList();
    Stage stage = new Stage();

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

        //handler.readAllFiles(stage);

        if(ProfileController.toBeChanged){
            chosenComponents.addAll(ProfileController.changeOrder.getComponentList());
            chosenCompTV.setItems(chosenComponents);
            chosenAdjustments.addAll(ProfileController.changeOrder.getAdjustmentList());
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
            for (Adjustment a : ProfileController.changeOrder.getAdjustmentList()){
                adjustmentTV.getItems().remove(a);
            }
        }
        else {
            adjustmentTV.setItems(Lists.getAdjustment());
        }
        chooseCol.setPromptText("Color: ");
        chooseCol.getItems().setAll("Red", "Black", "White", "Gray");

        //Setter valgmuligheter i choiceboxene
        chooseCarType.setPromptText("Car type: ");
        chooseComponent.setPromptText("Component type: ");
        chooseCarType.getItems().setAll(Methods.typeList(lists.getCars()));
        chooseComponent.getItems().setAll(Methods.componentList(lists.getComponents()));
    }

    //Metode for å sette verdiene i tableviewet for komponenter. Denne kalles på når choiceboxene endres.
    @FXML
    void setList(ActionEvent event) {
        ObservableList<Component> outList = FXCollections.observableArrayList();
        //Finner verdiene i choiceboxene
        String type = chooseCarType.getValue();
        String ID = "";
        for (Car car : lists.getCars()){
            if (type.equals(car.getCarType())){
                ID = car.getCarID();
            }
        }
        String component = chooseComponent.getValue();

        //Hvis et element i komponentlisten matcher verdien i begge choiceboxene legges de til i en ny liste
        if(type != null && component != null) {
            for (int i = 0; i < lists.getComponents().size(); i++) {
                if (ID.equals(lists.getComponents().get(i).getCarID()) && component.equals(lists.getComponents().get(i).getComponentType())) {
                    outList.add(lists.getComponents().get(i));
                }
            }
        }

        //Den nye listen vises i tableviewet
        componentTV.setItems(outList);
        componentTV.refresh();
    }

    @FXML
    void addComponent(ActionEvent event) {
        Component valgt = componentTV.getSelectionModel().getSelectedItem();
        boolean funnet = false;
        for (Component c : chosenComponents){
            if(valgt.getComponentType().equals(c.getComponentType())){
                funnet = true;
            }
        }
        if(!funnet) {
            chosenComponents.add(valgt);
            chosenCompTV.setItems(chosenComponents);
            chooseCarType.setDisable(true);

            int totalprice = 0;
            for (Adjustment adj : chosenAdjustments) {
                totalprice += adj.getAdjustmentPrice();
            }
            for (Component c : chosenComponents) {
                totalprice += c.getComponentPrice();
            }
            lblTotalprice.setText("Total price: " + totalprice);
        }
        else{
            Dialogs.showErrorDialog("You have already added a component of this type. If you wish to select this" +
                    " component you will first have to remove the previously chosen " + valgt.getComponentType());
        }
    }

    @FXML
    void removeComponent(ActionEvent event) {
        Component valgt = chosenCompTV.getSelectionModel().getSelectedItem();
        chosenComponents.remove(valgt);
        chooseComponent.getItems().remove(valgt);
        chosenCompTV.refresh();
        if(chosenComponents.isEmpty()){
            chooseCarType.setDisable(false);
        }

        int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);
    }

    public void addAdjust(ActionEvent actionEvent) {
        Adjustment chosen = adjustmentTV.getSelectionModel().getSelectedItem();
        chosenAdjustments.add(chosen);
        chosenAdjustTV.setItems(chosenAdjustments);

        int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);

        for(Adjustment a : chosenAdjustments){
            adjustmentTV.getItems().remove(a);
        }

    }

    public void removeAdjust(ActionEvent actionEvent) {
        Adjustment chosen = chosenAdjustTV.getSelectionModel().getSelectedItem();
        chosenAdjustments.remove(chosen);
        adjustmentTV.getItems().add(chosen);

        int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Total price: " + totalprice);
    }

    public void myProfile(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(chosenComponents.isEmpty() && chosenAdjustments.isEmpty()) {
            System.out.println(Lists.getOrders().size());
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
            OpenScene.newScene("My profile", root, 610, 650, actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have not saved your choices, and if you continue your progress will be lost." +
                    " If you wish to continue this order at a later time, you will have to go back and click on 'save order'.",
                    ButtonType.CANCEL, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)){
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
                OpenScene.newScene("My profile", root, 610, 650, actionEvent);
            }
        }
    }

    public void saveChoices(ActionEvent actionEvent) {
        boolean rightInput = true;
        Date date = new Date();

        int price = 0;
        for (Component c : chosenComponents){
            price += c.getComponentPrice();
        }
        for (Adjustment a : chosenAdjustments){
            price += a.getAdjustmentPrice();
        }

        int persID = LoggedIn.getId();

        String carId = "";
        if(chooseCarType.getValue() != null) {
            for (Car car : Lists.getCars()) {
                if (chooseCarType.getValue().equals(car.getCarType())) {
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
            color += "Not chosen";
        }

        if(rightInput) {
            Order order = new Order("", persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, false);
            lists.addOngoingOrder(order);
            Path path = Paths.get("OngoingOrders.txt");
            String formattedOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
            try {
                FileWriter.WriteFile(path, formattedOrders);
                Dialogs.showSuccessDialog("Your order was succesful!");
                adjustmentTV.getItems().addAll(chosenAdjustments);
                chosenComponents.clear();
                chosenAdjustments.clear();
                chosenAdjustTV.refresh();
                adjustmentTV.refresh();
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("Order", root, 650, 700, actionEvent);
                ProfileController.toBeChanged = false;
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Dialogs.showErrorDialog("Something went wrong.");
            }
        }
    }

    public void order(ActionEvent actionEvent) throws ParseException {
        boolean rightInput = true;

        Date date = new Date();

        int price = 0;
        for (Component c : chosenComponents){
            price += c.getComponentPrice();
        }
        for (Adjustment a : chosenAdjustments){
            price += a.getAdjustmentPrice();
        }

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
                if (chooseCarType.getValue().equals(car.getCarType())) {
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

        if(rightInput) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to finish this order?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if(alert.getResult().equals(ButtonType.OK)) {
                Order order = new Order(newOrderNr, persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, true);
                lists.addOrder(order);
                System.out.println(Lists.getOrders().size());
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

    public void logOut(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 650, 650, actionEvent);
    }
}
