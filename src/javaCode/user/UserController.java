package javaCode.user;

import javaCode.*;
import javaCode.InLog.LoggedIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javaCode.Lists;
import javafx.stage.Stage;

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
        chosenComponents.add(valgt);
        chosenCompTV.setItems(chosenComponents);
        chooseCarType.setDisable(true);

        int totalprice = 0;
        for (Adjustment adj : chosenAdjustments){
            totalprice += adj.getAdjustmentPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Totalpris: " + totalprice);
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
        lblTotalprice.setText("Totalpris: " + totalprice);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler.readAllFiles(stage);
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

            for (Adjustment a : ProfileController.changeOrder.getAdjustmentList()){
                adjustmentTV.getItems().remove(a);
            }
        }

        adjustmentTV.setItems(Lists.getAdjustment());

        chooseCol.setPromptText("Velg farge: ");
        chooseCol.getItems().setAll("Rød", "Svart", "Hvit", "Grå");

        //Setter valgmuligheter i choiceboxene
        chooseCarType.setPromptText("Car type: ");
        chooseComponent.setPromptText("Component type: ");
        chooseCarType.getItems().setAll(Methods.typeList(lists.getCars()));
        chooseComponent.getItems().setAll(Methods.componentList(lists.getComponents()));
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
        lblTotalprice.setText("Totalpris: " + totalprice);

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
        lblTotalprice.setText("Totalpris: " + totalprice);
    }

    public void myProfile(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/myProfile.fxml"));
        OpenScene.newScene("My profile", root, 610, 650, actionEvent);
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

        int carId = 0;
        if(chooseCarType.getValue() != null) {
            for (Car car : Lists.getCars()) {
                if (chooseCarType.getValue().equals(car.getCarType())) {
                    carId = Integer.parseInt(car.getCarID());
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
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Dialogs.showErrorDialog("Noe gikk galt.");
            }
        }
    }

    public void order(ActionEvent actionEvent) {
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
        int carId = 0;
        if(chooseCarType.getValue() != null) {
            for (Car car : Lists.getCars()) {
                if (chooseCarType.getValue().equals(car.getCarType())) {
                    carId = Integer.parseInt(car.getCarID());
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
            Order order = new Order(newOrderNr, persID, carId, date, date, orderedComponents, orderedAdjustments, price, color, true);
            lists.addOrder(order);
            Path path = Paths.get("FinishedOrders.txt");
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
                Dialogs.showErrorDialog("Noe gikk galt.");
            }
        }
    }

    public void logOut(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 650, 650, actionEvent);
    }
}


