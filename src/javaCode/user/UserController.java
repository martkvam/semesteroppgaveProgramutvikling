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

    private KomponentLister lister = new KomponentLister();


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
        String ID;
        switch (type){
            case "Bensin":
                ID = "1";
                break;
            case "Diesel":
                ID = "2";
                break;
            case "Elektrisk":
                ID = "3";
                break;
            case "Hybrid":
                ID = "4";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        String component = velgKomponent.getValue();

        //Hvis et element i komponentlisten matcher verdien i begge choiceboxene legges de til i en ny liste
        if(type != null && component != null) {
            for (int i = 0; i < lister.list.size(); i++) {
                if (ID.equals(lister.list.get(i).getCarID()) && component.equals(lister.list.get(i).getComponentType())) {
                    outList.add(lister.list.get(i));
                }
            }
        }

        //Den nye listen vises i tableviewet
        komponentTv.setItems(outList);
        komponentTv.refresh();
    }

    //Metode for å lage liste som skal fylle biltype-choicebox
    public ArrayList<String> typeList(ObservableList<Component> inList) {
        ArrayList<String> aList = new ArrayList<>();
        for (int i = 0; i < inList.size(); i++) {
            if (!aList.contains(inList.get(i).getCarID())) {
                aList.add(inList.get(i).getCarID());
            }
        }
        ArrayList<String> outList = new ArrayList<>();
        for (String s : aList) {
            switch (s) {
                case "1":
                    String type = "Bensin";
                    outList.add(type);
                    break;
                case "2":
                    String type2 = "Diesel";
                    outList.add(type2);
                    break;
                case "3":
                    String type3 = "Elektrisk";
                    outList.add(type3);
                    break;
                case "4":
                    String type4 = "Hybrid";
                    outList.add(type4);
                    break;
                default:
                    String type5 = "Annen type";
                    outList.add(type5);
                    break;
            }
        }
        return outList;
    }

    //Metode for å lage liste som skal fylle komponent-choicebox
    public ArrayList<String> componentList(ObservableList<Component> inList){
        ArrayList<Component> aList = new ArrayList<>();
        for (Component component : inList) {
            char ID = component.getComponentID().charAt(0);
            boolean found = false;
            for(int i = 0; i<aList.size(); i++){
                char ID2 = aList.get(i).getComponentID().charAt(0);
                if (ID == ID2) {
                    found = true;
                    break;
                }
             }
            if (!found){
                aList.add(component);
            }
        }

        ArrayList<String> outList = new ArrayList<>();
        for(Component component : aList){
            outList.add(component.getComponentType());
            };
        return outList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Component motor1 = new Component("1", "1-01","Motor", "Rask jævel", 20000);
        Component wheel1 = new Component("2", "2-01", "Ratt" ,"Billig", 2000);
        Component rim1 = new Component("3", "3-01", "Felg" ,"Dyreste", 2000);
        Component setetrekk = new Component("4", "4-01", "Setetrekk" ,"Skinn", 2000);
        Component motor2 = new Component("3", "1-02", "Motor" ,"Effektiv", 35000);
        lister.addComponent(motor1);
        lister.addComponent(wheel1);
        lister.addComponent(rim1);
        lister.addComponent(setetrekk);
        lister.addComponent(motor2);

        //Setter valgmuligheter i choiceboxene
        velgBilType.setPromptText("Velg biltype: ");
        velgKomponent.setPromptText("Velg komponent: ");
        velgBilType.getItems().setAll(typeList(lister.list));
        velgKomponent.getItems().setAll(componentList(lister.list));
    }
}


