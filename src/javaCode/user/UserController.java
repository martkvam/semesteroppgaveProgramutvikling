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

    private Lister lister = new Lister();
    ObservableList<Component> valgteKomponenter = FXCollections.observableArrayList();


    @FXML
    private TableView<Component> komponentTv;

    @FXML
    private TableView<Component> valgTv;

    @FXML
    private TableView<?> tidligereTv;

    @FXML
    private ComboBox<String> velgBilType;

    @FXML
    private ComboBox<String> velgKomponent;

    @FXML
    private Button leggTill;

    //Metode for å sette verdiene i tableviewet for komponenter. Denne kalles på når choiceboxene endres.
    @FXML
    void settListe(ActionEvent event) {
        ObservableList<Component> outList = FXCollections.observableArrayList();
        //Finner verdiene i choiceboxene
        String type = velgBilType.getValue();
        String ID = "";
        for (Car car : lister.carList){
            if (type.equals(car.getCarType())){
                ID = car.getCarID();
            }
        }
        String component = velgKomponent.getValue();

        //Hvis et element i komponentlisten matcher verdien i begge choiceboxene legges de til i en ny liste
        if(type != null && component != null) {
            for (int i = 0; i < lister.componentList.size(); i++) {
                if (ID.equals(lister.componentList.get(i).getCarID()) && component.equals(lister.componentList.get(i).getComponentType())) {
                    outList.add(lister.componentList.get(i));
                }
            }
        }

        //Den nye listen vises i tableviewet
        komponentTv.setItems(outList);
        komponentTv.refresh();
    }

    @FXML
    void leggTil(ActionEvent event) {
        Component valgt = komponentTv.getSelectionModel().getSelectedItem();
        valgteKomponenter.add(valgt);
        valgTv.setItems(valgteKomponenter);
        velgBilType.setDisable(true);
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
        lister.addComponent(motor1);
        lister.addComponent(wheel1);
        lister.addComponent(rim1);
        lister.addComponent(setetrekk);
        lister.addComponent(motor2);
        lister.addCar(bensin);
        lister.addCar(diesel);
        lister.addCar(elektrisk);
        lister.addCar(hybrid);

        //Setter valgmuligheter i choiceboxene
        velgBilType.setPromptText("Velg biltype: ");
        velgKomponent.setPromptText("Velg komponent: ");
        velgBilType.getItems().setAll(Metoder.typeList(lister.carList));
        velgKomponent.getItems().setAll(Metoder.componentList(lister.componentList));
    }
}


