package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lists {

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
