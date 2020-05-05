
package javaCode.user;

import javaCode.*;
import javaCode.InLog.LoggedIn;
import javaCode.InLog.ReadUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    //Using these to change an ongoing order
    public static Order changeOrder;
    public static boolean toBeChanged;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblHeader;

    @FXML
    private TableView<Order> finishedOrdersTV;

    @FXML
    private TableView<Component> orderedComponentsTV;

    @FXML
    private TableView<Adjustment> orderedAdjustmentsTV;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFinish;

    @FXML
    private Button btnShowFinished;

    @FXML
    private Button btnShowOngoing;



    @FXML
    private Button btnExportFinished;

    @FXML
    private Button btnExportOngoing;


    @FXML
    void changeOrder(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(!finishedOrdersTV.getSelectionModel().isEmpty()) {
            Order chosen = finishedOrdersTV.getSelectionModel().getSelectedItem();
            changeOrder = chosen;
            toBeChanged = true;
            Lists.getOngoingOrders().remove(chosen);

            //Updating the ongoing orders-file.
            try {
                String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
            OpenScene.newScene("Order", root, 650, 700, event);
        }
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        if(!finishedOrdersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this ongoing order?"
                    , ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Order chosen = finishedOrdersTV.getSelectionModel().getSelectedItem();
                Lists.getOngoingOrders().remove(chosen);
                finishedOrdersTV.getItems().remove(chosen);
                finishedOrdersTV.refresh();

                //Updating the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    void finishOrder(Event event) {
        if(!finishedOrdersTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
            //If color is not chosen
            String [] color = {"Red", "Black", "White", "Gray"};
            ChoiceDialog<String> choice = new ChoiceDialog(color[1], color);
            if(finishedOrdersTV.getSelectionModel().getSelectedItem().getCarColor().equals("Not chosen")){
                choice.setTitle("Finish order");
                choice.setContentText("Please choose a color for the car before you finish your order: ");
                choice.showAndWait();
                String chosenColor = choice.getSelectedItem();
                finishedOrdersTV.getSelectionModel().getSelectedItem().setCarColor(chosenColor);
            }
            //If the order is ready
            else {
               alert.setContentText("Are you sure you want to finish this order?");
               alert.showAndWait();
            }
            if (alert.getResult() == ButtonType.YES || !choice.getResult().isEmpty()) {
                Order chosen = finishedOrdersTV.getSelectionModel().getSelectedItem();

                int largest = 0;
                for (Order o : Lists.getOrders()) {
                    if (Integer.parseInt(o.getOrderNr()) > largest) {
                        largest = Integer.parseInt(o.getOrderNr());
                    }
                }

                int orderNr = largest + 1;
                String newOrderNr = "" + orderNr;
                chosen.setOrderNr(newOrderNr);
                chosen.setOrderStatus(true);

                Lists.getOrders().add(chosen);
                Lists.getOngoingOrders().remove(chosen);

               // finishedOrdersTV.getItems().add(chosen);
                finishedOrdersTV.refresh();
                finishedOrdersTV.getItems().remove(chosen);
               // ongoingOrdersTV.refresh();

                //Adding the order to the finsished orders-file.
                try {
                    String formattedOrder = OrderFormatter.formatOrder(chosen);
                    BufferedWriter update;
                    update = new BufferedWriter(new FileWriter("src/dataBase/FinishedOrders.txt", true));
                    update.append(formattedOrder);
                    update.newLine();
                    update.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Updating the ongoing orders-file.
                try {
                    String formattedOngoingOrders = OrderFormatter.formatOrders(Lists.getOngoingOrders());
                    javaCode.FileWriter.WriteFile(Paths.get("src/dataBase/OngoingOrders.txt"), formattedOngoingOrders);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTVfinished(event);
            }
        }

    }

    @FXML
    void updateInfo(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/updatePersonalInfo.fxml"));
        OpenScene.newScene("Change info", root, 300, 400, event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Order> orders = FXCollections.observableArrayList();
        //ObservableList<Order> ongoing = FXCollections.observableArrayList();

        for (Order o : Lists.getOrders()){
            if(o.getPersonId() == LoggedIn.getId()){
                orders.add(o);
            }
        }

        /*for (Order ongoingOrder : Lists.getOngoingOrders()){
            if (ongoingOrder.getPersonId() == LoggedIn.getId()){
                ongoing.add(ongoingOrder);
            }
        }*/


            finishedOrdersTV.setItems(orders);
            //ongoingOrdersTV.setItems(ongoing);


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

    toBeChanged = false;
    }

    public void updateTVfinished(Event mouseEvent) {
        if(!finishedOrdersTV.getSelectionModel().isEmpty()) {
            ObservableList<Component> componentList = finishedOrdersTV.getSelectionModel().getSelectedItem().getComponentList();
            ObservableList<Adjustment> adjustmentList = finishedOrdersTV.getSelectionModel().getSelectedItem().getAdjustmentList();
            orderedComponentsTV.setItems(componentList);
            orderedAdjustmentsTV.setItems(adjustmentList);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
        }
        if(finishedOrdersTV.getSelectionModel().isEmpty()){
            ObservableList<Component> components = FXCollections.observableArrayList();
            ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
            orderedComponentsTV.setItems(components);
            orderedAdjustmentsTV.setItems(adjustments);
            orderedComponentsTV.refresh();
            orderedAdjustmentsTV.refresh();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
        OpenScene.newScene("Order", root, 650, 700, actionEvent);
    }

    public void showFinsihed(Event event) {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        for (Order o : Lists.getOrders()){
            if(o.getPersonId() == LoggedIn.getId()){
                orders.add(o);
            }
        }
        finishedOrdersTV.setItems(orders);
        btnChange.setVisible(false);
        btnFinish.setVisible(false);
        btnDelete.setVisible(false);
        btnShowFinished.setVisible(false);
        btnShowOngoing.setVisible(true);

        //orderedAdjustmentsTV.getItems().clear();
       // orderedComponentsTV.getItems().clear();

        lblHeader.setText("Finished orders (click on an order to see content)");
        updateTVfinished(event);
    }

    public void showOngoing(Event event) {
        ObservableList<Order> ongoing = FXCollections.observableArrayList();

        for (Order ongoingOrder : Lists.getOngoingOrders()){
            if (ongoingOrder.getPersonId() == LoggedIn.getId()){
                ongoing.add(ongoingOrder);
            }
        }
        finishedOrdersTV.setItems(ongoing);
        btnDelete.setVisible(true);
        btnFinish.setVisible(true);
        btnChange.setVisible(true);
        btnShowFinished.setVisible(true);
        btnShowOngoing.setVisible(false);

       // orderedComponentsTV.getItems().clear();
       // orderedAdjustmentsTV.getItems().clear();

        lblHeader.setText("Ongoing orders (click on an order to see content)");
        updateTVfinished(event);
    }

    public void btnExportFinishedOnClick(ActionEvent actionEvent) throws IOException {
        if(finishedOrdersTV.getItems().size() != 0) {
            Excel.writeExcel(finishedOrdersTV.getItems(), "User");
        } else{
            Dialogs.showErrorDialog("List is empty");
        }

    }

    public void btnExportOngoingOnClick(ActionEvent actionEvent) throws IOException {
        /*if (!ongoingOrdersTV.getItems().isEmpty()){
        Excel.writeExcel(ongoingOrdersTV.getItems());
        }else {
            Dialogs.showErrorDialog("List is empty");
        }*/
    }
}