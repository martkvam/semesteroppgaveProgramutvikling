package javaCode;

import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Lists<E> implements Serializable {

    private static transient ObservableList<Car> carList = FXCollections.observableArrayList();
    private static transient ObservableList<Car> deletedCarList = FXCollections.observableArrayList();
    private static ObservableList<Component> componentList = FXCollections.observableArrayList();
    private static ObservableList<Component> deletedComponentList = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> adjustmentList = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> deletedAdjustmentList = FXCollections.observableArrayList();
    private static ObservableList<Order> orderList = FXCollections.observableArrayList();
    private static ObservableList<Order> ongoingOrderList = FXCollections.observableArrayList();
    private static ObservableList<Component> basePackageComponents = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> basePackageAdjustments = FXCollections.observableArrayList();

    //Metoder for å legge til objekter i listene

    public void addCar (Car car){
        if(car!=null){
            for(Car i : carList){
                if(i.getCarID().equals(car.getCarID())){
                    Dialogs.showErrorDialog("This car already exists");
                }
            }
            carList.add(car);
        }
    }
    public void addDeletedCar (Car car){
        if(car!=null){
            deletedCarList.add(car);
        }
    }

    public static void deleteCars(){
        carList.clear();
    }

    public void addComponent(Component component){
        if(component!=null){
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
    }
    public void addDeletedComponent(Component component){
        if(component!=null){
            deletedComponentList.add(component);
        }
    }
    public static void deleteComponents(){
        componentList.clear();
    }

    public void addAdjustment (Adjustment adj){
        if(adj!=null){
            for(Adjustment i : adjustmentList){
                if(i.getAdjustmentID().equals(adj.getAdjustmentID())){
                    Dialogs.showErrorDialog("This Adjustment already exists");
                }
            }
            adjustmentList.add(adj);
        }
    }
    public void addDeletedAdjustment (Adjustment adjustment){

        if(adjustment!=null){
            deletedAdjustmentList.add(adjustment);
        }
    }
    public static void deleteAdjustments(){
        adjustmentList.clear();
    }

    public void addOrder(Order order){
        if (order != null) {
            orderList.add(order);
        }
    }

    public void addOngoingOrder (Order order){
        if (order != null) {
            ongoingOrderList.add(order);
        }
    }
    public static void deleteOrders(){
        orderList.clear();
    }
    public static void deleteOngoing(){ ongoingOrderList.clear();}

    public static void deleteDeletedComponentList() {
        deletedComponentList.clear();
    }

    public static void deleteDeletedAdjustmentList(){
        deletedAdjustmentList.clear();
    }
    public static void deleteDeletedCarlist(){
        deletedCarList.clear();
    }

    public static ObservableList<Car> getCars(){
        ArrayList<Car> newCarList =new ArrayList<>();
        for(Car i : carList){
            newCarList.add(i);
        }
        return carList;
    }
    public static ObservableList<Car> getDeletedCars(){
        return deletedCarList;
    }

    public static ObservableList<Component> getComponents(){
        ArrayList<Component> newComponentList =new ArrayList<>();
        for(Component i : componentList){
            newComponentList.add(i);
        }
        return componentList;
    }

    public static ObservableList<Component> getDeletedComponents(){
        return deletedComponentList;
    }
    public static ObservableList<Adjustment> getAdjustment(){
        return adjustmentList;
    }
    public static ObservableList<Adjustment> getDeletedAdjustment(){
        return deletedAdjustmentList;
    }
    public static ObservableList<Order> getOrders(){
        return orderList;
    }
    public static ObservableList<Order> getOngoingOrders(){ return ongoingOrderList; }

    public static ObservableList<Component> getBasePackageComponents(){
        return basePackageComponents;
    }
    public static ObservableList<Adjustment> getBasePackageAdjustments(){
        return basePackageAdjustments;
    }
    public static void deleteBasePackageComponents(){
        basePackageComponents.clear();
    }
    public static void deleteBasePackageAdjustments(){
        basePackageAdjustments.clear();
    }



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
        } else return component.getCarID().toLowerCase().contains(lowerCase);
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
        } else if (adjustment.getAdjustmentDescription().toLowerCase().contains(lowerCase)) {
            return true;
        } else return Integer.toString(adjustment.getAdjustmentPrice()).toLowerCase().contains(lowerCase);
    }


    public boolean filterOrderList(Order order, String newValue ){

        if(newValue == null || newValue.isEmpty()){
            return true;
        }
        String lowerCase = newValue.toLowerCase();

        if(String.valueOf(order.getOrderNr()).toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(order.getPersonId()).toLowerCase().equals(lowerCase)){
            return true;
        } else if (order.getCarId().toLowerCase().contains(lowerCase)){
            return true;
        } else if (Integer.toString(order.getTotalPrice()).toLowerCase().equals(lowerCase)) {
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
            if(i.getAdjustmentID().toLowerCase().equals(lowerCase)){
                return true;
            }
            else if(i.getAdjustmentType().toLowerCase().contains(lowerCase)){
                return true;
            }
        }

        return false;
    }
}
