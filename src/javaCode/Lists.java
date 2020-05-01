package javaCode;

import javaCode.Car;
import javaCode.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lists implements Serializable {

    private static transient ObservableList<Car> carList = FXCollections.observableArrayList();
    private static ObservableList<Component> componentList = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> adjustmentList = FXCollections.observableArrayList();
    private static ObservableList<Order> orderList = FXCollections.observableArrayList();
    private static ObservableList<Order> ongoingOrderList = FXCollections.observableArrayList();

    //Metoder for å legge til objekter i listene

    public void addCar (Car car){
        for(Car i : carList){
            if(i.getCarID().equals(car.getCarID())){
                Dialogs.showErrorDialog("This car already exists");
            }
        }
        carList.add(car);
    }
    public static void deleteCars(){
        carList.clear();
    }

    public void addComponent(Component component){
        boolean found = false;
        for(Component c : componentList){
            if(c.getComponentID().equals(component.getComponentID())){
                found = true;
            }
        }
        if(!found) {
            componentList.add(component);
        }
    }
    public static void deleteComponents(){
        componentList.clear();
    }

    public void addAdjustment (Adjustment adj){
        boolean found = false;
        for(Adjustment a : adjustmentList) {
            if(a.getAdjustmentID().equals(adj.getAdjustmentID())){
                found = true;
            }
        }
        if(!found) {
            adjustmentList.add(adj);
        }
    }
    public static void deleteAdjustments(){
        adjustmentList.clear();
    }

    public void addOrder(Order order){
        orderList.add(order);
    }

    public void addOngoingOrder (Order order){
        ongoingOrderList.add(order);
    }
    public static void deleteOrders(){
        orderList.clear();
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
    public static ObservableList<Order> getOngoingOrders(){ return ongoingOrderList; }


    /*private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(carList));
    }

    /*private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        List<Person> list = (List<Person>) s.readObject();
        pregister = FXCollections.observableArrayList();
        pregister.addAll(list);
    }

     */
}
