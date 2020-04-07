package javaCode;

import javaCode.Car;
import javaCode.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Lists {

    private static ObservableList<Car> carList = FXCollections.observableArrayList();
    private static ObservableList<Component> componentList = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> adjustmentList = FXCollections.observableArrayList();
    private static ObservableList<Order> orderList = FXCollections.observableArrayList();

    //Metoder for å legge til objekter i listene
    public void addComponent(Component component){
        componentList.add(component);
    }

    public void addCar (Car car){
        carList.add(car);
    }

    public void addAdjustment (Adjustment adj){
        adjustmentList.add(adj);
    }

    public void addOrder(Order order){
        orderList.add(order);
    }


    public static ObservableList<Car> getCars(){
        ArrayList<Car> newCarList =new ArrayList<>();
        for(Car i : carList){
            newCarList.add(i);
        }
        return carList;
    }
    public static ObservableList<Component> getComponents(){
        ArrayList<Component> newComponentList =new ArrayList<>();
        for(Component i : componentList){
            newComponentList.add(i);
        }
        return componentList;
    }
    public static ObservableList<Adjustment> getAdjustment(){
        return adjustmentList;
    }
    public static ObservableList<Order> getOrders(){
        return orderList;
    }



}
