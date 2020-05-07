package javaCode;

import javaCode.Car;
import javaCode.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lists<E> implements Serializable {

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
    public static void deleteOngoing(){ ongoingOrderList.clear();}

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

    public boolean filterComponentList(Component component, String newValue ){
        if(newValue == null || newValue.isEmpty()){
            return true;
        }

        String lowerCase = newValue.toLowerCase();

        if(String.valueOf(component.getComponentID()).toLowerCase().contains(lowerCase)){
            return true;
        } else if (component.getComponentType().toLowerCase().contains(lowerCase)){
            return true;
        } else if (component.getComponentDescription().toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(component.getComponentPrice()).toLowerCase().contains(lowerCase)) {
            return true;
        } else if (component.getCarID().toLowerCase().contains(lowerCase)){
            return true;
        }
        return false;
    }

    public boolean filterAdjustmentList(Adjustment adjustment, String newValue){
        if(newValue == null || newValue.isEmpty()){
            return true;
        }

        String lowerCase = newValue.toLowerCase();

        if(String.valueOf(adjustment.getAdjustmentID()).toLowerCase().contains(lowerCase)){
            return true;
        } else if (adjustment.getAdjustmentType().toLowerCase().contains(lowerCase)){
            return true;
        } else if (adjustment.getAdjustmentDescription().toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(adjustment.getAdjustmentPrice()).toLowerCase().contains(lowerCase)) {
            return true;
        }
        return false;
    }


    public boolean filterOrderList(Order order, String newValue ){

        if(newValue == null || newValue.isEmpty()){
            return true;
        }

        String lowerCase = newValue.toLowerCase();

        if(String.valueOf(order.getOrderNr()).toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(order.getPersonId()).toLowerCase().contains(lowerCase)){
            return true;
        } else if (order.getCarId().toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(order.getTotalPrice()).toLowerCase().contains(lowerCase)) {
            return true;
        } else if (order.getCarColor().toLowerCase().contains(lowerCase)){
            return true;
        } else if (Boolean.toString(order.getOrderStatus()).toLowerCase().contains(lowerCase)) {
            return true;
        }
        for(Component i : order.getComponentList()){
            if(i.getComponentID().toLowerCase().contains(lowerCase)){
                return true;
            }
            else if(i.getComponentType().toLowerCase().contains(lowerCase)){
                return true;
            }
            else if(i.getComponentDescription().toLowerCase().contains(lowerCase)){
                return true;
            }

        }
        for(Adjustment i : order.getAdjustmentList()){
            if(i.getAdjustmentID().toLowerCase().contains(lowerCase)){
                return true;
            }
            else if(i.getAdjustmentType().toLowerCase().contains(lowerCase)){
                return true;
            }
        }

        return false;
    }
}
