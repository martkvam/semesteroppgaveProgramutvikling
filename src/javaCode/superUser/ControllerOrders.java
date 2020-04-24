package javaCode.superUser;
import javaCode.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerOrders implements Initializable {
    Stage stage = new Stage();
    private static ObservableList<Component> componentListSuperUser = FXCollections.observableArrayList();
    FileHandler fileHandler = new FileHandler();
    @FXML
    private TableView<Order> tableViewOrder;

    @FXML
    private TableView<Component> tableViewComponents;

    @FXML
    private TableView<Order> tableViewAdjustments;

    @FXML
    private TableColumn<TableView<Order>, Integer> personId;

    @FXML
    private TableColumn<TableView<Order>, String> carId;

    @FXML
    private TableColumn<TableView<Order>, Date> orderStarted;

    @FXML
    private TableColumn<TableView<Order>, Date> orderEnded;

    @FXML
    private TableColumn<TableView<Order>, Integer> totalPrice;

    @FXML
    private TableColumn<TableView<Order>, String> color;

    @FXML
    private TableColumn<TableView<Order>, Boolean> orderStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileHandler.readAllFiles(stage);
        for(int i =0; i < Lists.getOrders().size();i++){
            System.out.println(Lists.getOrders().get(i).getOrderNr() + Lists.getOrders().get(i).getCarColor() + Lists.getOrders().get(i).getCarId());
            for(int j = 0; j < Lists.getOrders().get(i).getComponentList().size(); j++){
                System.out.println(Lists.getOrders().get(i).getComponentList().get(j).getComponentType());

            }
        }

        tableViewOrder.setItems(Lists.getOrders());

        /*componentListSuperUser = Lists.getOrders().get(1).getComponentList();
        tableViewComponents.setItems(componentListSuperUser);*/

    }
    @FXML
    void btnBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerSuperUser");

        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.
                newScene("Edit orders", root, 710 ,500, event);
    }

}


