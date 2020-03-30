package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class UserController implements Initializable {

    private KomponentLister list = new KomponentLister();


    @FXML
    private TableView<Component> komponentTv;

    @FXML
    private TableView<?> valgTv;

    @FXML
    private TableView<?> tidligereTv;

    @FXML
    private ComboBox<String> velgBilType;

    @FXML
    private ComboBox<String> velgKomponent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Kobler tableviewet opp mot listen med komponenter
        list.attachTableView(komponentTv);

        //Setter valgmuligheter i choiceboxene
        velgBilType.getItems().setAll("Bensin", "Diesel", "Elektrisk", "Hybrid");
        velgBilType.setPromptText("Velg biltype: ");
        velgKomponent.getItems().setAll("Motor", "Ratt", "Setetrekk", "Felger");
        velgKomponent.setPromptText("Velg komponent: ");

        //Legger til en komponent i listen
        Motor motor1 = new Motor(1, "Rask jævel", 20000, 220);
        list.addComponent(motor1);
    }
}


