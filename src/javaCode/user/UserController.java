package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    //Metode for å sette verdiene i tableviewet for komponenter. Denne kalles på når choiceboxene endres.
    @FXML
    void settListe(ActionEvent event) {
        ObservableList<Component> outList = FXCollections.observableArrayList();
        //Finner verdiene i choiceboxene
        String type = velgBilType.getValue();
        String component = velgKomponent.getValue();

        //Hvis et element i komponentlisten matcher verdien i begge choiceboxene legges de til i en ny liste
        if(type != null && component != null) {
            for (int i = 0; i < list.list.size(); i++) {
                if (type.equals(list.list.get(i).getCarType()) && component.equals(list.list.get(i).getComponentType())) {
                    outList.add(list.list.get(i));
                }
            }
        }

        //Den nye listen vises i tableviewet
        komponentTv.setItems(outList);
        komponentTv.refresh();
    }

    //Metode for å lage liste som skal fylle biltype-choicebox
    public ArrayList<String> typeList(ObservableList<Component> inList) {
        ArrayList<Component> aList = new ArrayList<>();
        for(int i = 0; i<inList.size(); i++){
            if(!aList.contains(inList.get(i).getCarID())){
                aList.add(inList.get(i));
            }
        }
        ArrayList<String> outList = new ArrayList<>();
        for (int i = 0; i<aList.size(); i++){
            outList.add(aList.get(i).getCarType());
        }
        return outList;
    }

    //Metode for å lage liste som skal fylle komponent-choicebox
    public ArrayList<String> componentList(ObservableList<Component> inList){
        ArrayList<Component> aList = new ArrayList<>();
        for(int i = 0; i<inList.size(); i++){
            if(!aList.contains(inList.get(i).getComponentID())){
                aList.add(inList.get(i));
            }
        }
        ArrayList<String> outList = new ArrayList<>();
        for(int i = 0; i < aList.size(); i++){
            outList.add(inList.get(i).getComponentType());
        } return outList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Component motor1 = new Component(1, 1, "Bensin", "Motor", "..", 20000);
        Component wheel1 = new Component(2, 2, "Diesel", "Ratt" ,"..", 2000);
        Component rim1 = new Component(3, 3, "Elektrisk", "Felg" ,"..", 2000);
        Component setetrekk = new Component(4, 4, "Hybrid", "Setetrekk" ,"..", 2000);
        Component motor2 = new Component(3, 1, "Elekrisk", "Motor" ,"..", 2000);
        list.addComponent(motor1);
        list.addComponent(wheel1);
        list.addComponent(rim1);
        list.addComponent(setetrekk);
        list.addComponent(motor2);

        //Setter valgmuligheter i choiceboxene
        velgBilType.setPromptText("Velg biltype: ");
        velgKomponent.setPromptText("Velg komponent: ");
        velgBilType.getItems().setAll(typeList(list.list));
        velgKomponent.getItems().setAll(componentList(list.list));

    }
}


