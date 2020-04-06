package javaCode.user;

import javaCode.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javaCode.Lists;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class UserController implements Initializable {

    private Lists lists = new Lists();
    ObservableList<Component> chosenComponents = FXCollections.observableArrayList();
    ObservableList<Adjustment> chosenAdjustments = FXCollections.observableArrayList();


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
            totalprice += adj.getPrice();
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
            totalprice += adj.getPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Totalpris: " + totalprice);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            totalprice += adj.getPrice();
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
            totalprice += adj.getPrice();
        }
        for(Component c : chosenComponents){
            totalprice += c.getComponentPrice();
        }
        lblTotalprice.setText("Totalpris: " + totalprice);
    }
}


