package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Lister {

    ObservableList<Component> componentList = FXCollections.observableArrayList();
    ObservableList<Car> carList = FXCollections.observableArrayList();

    //Metoder for å legge til objekter i listene
    public void addComponent(Component component){
        componentList.add(component);
    }

    public void addCar (Car car){
        carList.add(car);
    }
}
