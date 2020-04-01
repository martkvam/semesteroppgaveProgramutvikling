package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class UserController implements Initializable {

    private Lists lists = new Lists();
    ObservableList<Component> chosenComponents = FXCollections.observableArrayList();


    @FXML
    private TableView<Component> componentTV;

    @FXML
    private TableView<Component> chosenTV;

    @FXML
    private TableView<?> previousTV;

    @FXML
    private ComboBox<String> chooseCarType;

    @FXML
    private Button addBtn;

    @FXML
    private ComboBox<String> chooseComponent;

    @FXML
    private Button leggTill;

    //Metode for å sette verdiene i tableviewet for komponenter. Denne kalles på når choiceboxene endres.
    @FXML
    void setList(ActionEvent event) {
        ObservableList<Component> outList = FXCollections.observableArrayList();
        //Finner verdiene i choiceboxene
        String type = chooseCarType.getValue();
        String ID = "";
        for (Car car : lists.carList){
            if (type.equals(car.getCarType())){
                ID = car.getCarID();
            }
        }
        String component = chooseComponent.getValue();

        //Hvis et element i komponentlisten matcher verdien i begge choiceboxene legges de til i en ny liste
        if(type != null && component != null) {
            for (int i = 0; i < lists.componentList.size(); i++) {
                if (ID.equals(lists.componentList.get(i).getCarID()) && component.equals(lists.componentList.get(i).getComponentType())) {
                    outList.add(lists.componentList.get(i));
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
        chosenTV.setItems(chosenComponents);
        chooseCarType.setDisable(true);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Component motor1 = new Component("1", "1-01","Motor", "Rask jævel", 20000);
        Component wheel1 = new Component("2", "2-01", "Ratt" ,"Billig", 2000);
        Component rim1 = new Component("3", "3-01", "Felg" ,"Dyreste", 2000);
        Component setetrekk = new Component("4", "4-01", "Setetrekk" ,"Skinn", 2000);
        Component motor2 = new Component("3", "1-02", "Motor" ,"Effektiv", 35000);

        Car bensin = new Car("1", "Bensin", "Bensinbil", 150000);
        Car diesel = new Car("2", "Diesel", "Dieselbil", 150000);
        Car elektrisk = new Car("3", "Elektrisk", "Elektrisk bil", 150000);
        Car hybrid = new Car("4", "Hybrid", "Hybridbil", 150000);
        lists.addComponent(motor1);
        lists.addComponent(wheel1);
        lists.addComponent(rim1);
        lists.addComponent(setetrekk);
        lists.addComponent(motor2);
        lists.addCar(bensin);
        lists.addCar(diesel);
        lists.addCar(elektrisk);
        lists.addCar(hybrid);

        //Setter valgmuligheter i choiceboxene
        chooseCarType.setPromptText("Velg biltype: ");
        chooseComponent.setPromptText("Velg komponent: ");
        chooseCarType.getItems().setAll(Methods.typeList(lists.carList));
        chooseComponent.getItems().setAll(Methods.componentList(lists.componentList));
    }
}


