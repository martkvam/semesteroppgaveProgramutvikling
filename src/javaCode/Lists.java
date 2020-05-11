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
    private static ObservableList<Component> componentList = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> adjustmentList = FXCollections.observableArrayList();
    private static ObservableList<Order> orderList = FXCollections.observableArrayList();
    private static ObservableList<Order> ongoingOrderList = FXCollections.observableArrayList();
    private static ObservableList<Component> basePackageComponents = FXCollections.observableArrayList();
    private static ObservableList<Adjustment> basePackageAdjustments = FXCollections.observableArrayList();

    //Metoder for å legge til objekter i listene

    public static void deleteCars() {
        carList.clear();
    }

    public static void deleteComponents() {
        componentList.clear();
    }

    public static void deleteAdjustments() {
        adjustmentList.clear();
    }

    public static void deleteOrders() {
        orderList.clear();
    }

    public static void deleteOngoing() {
        ongoingOrderList.clear();
    }

    public static ObservableList<Car> getCars() {
        return carList;
    }

    public static ObservableList<Component> getComponents() {
        return componentList;
    }

    public static ObservableList<Adjustment> getAdjustment() {
        return adjustmentList;
    }

    public static ObservableList<Order> getOrders() {
        return orderList;
    }

    public static ObservableList<Order> getOngoingOrders() {
        return ongoingOrderList;
    }

    public static ObservableList<Component> getBasePackageComponents() {
        return basePackageComponents;
    }

    public static ObservableList<Adjustment> getBasePackageAdjustments() {
        return basePackageAdjustments;
    }

    public static void deleteBasePackageComponents() {
        basePackageComponents.clear();
    }

    public static void deleteBasePackageAdjustments() {
        basePackageAdjustments.clear();
    }

    public void addCar(Car car) {
        for (Car i : carList) {
            if (i.getCarID().equals(car.getCarID())) {
                Dialogs.showErrorDialog("This car already exists");
            }
        }
        carList.add(car);
    }

    public void addComponent(Component component) {
        boolean found = false;
        for (Component c : componentList) {
            if (c.getComponentID().equals(component.getComponentID())) {
                found = true;
            }
        }
        if (!found) {
            componentList.add(component);
        }
    }

    public void addAdjustment(Adjustment adj) {
        boolean found = false;
        for (Adjustment a : adjustmentList) {
            if (a.getAdjustmentID().equals(adj.getAdjustmentID())) {
                found = true;
            }
        }
        if (!found) {
            adjustmentList.add(adj);
        }
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void addOngoingOrder(Order order) {
        ongoingOrderList.add(order);
    }

    public boolean filterComponentList(Component component, String newValue) {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String lowerCase = newValue.toLowerCase();

        if (String.valueOf(component.getComponentID()).toLowerCase().contains(lowerCase)) {
            return true;
        } else if (component.getComponentType().toLowerCase().contains(lowerCase)) {
            return true;
        } else if (component.getComponentDescription().toLowerCase().contains(lowerCase)) {
            return true;
        } else if (Integer.toString(component.getComponentPrice()).toLowerCase().contains(lowerCase)) {
            return true;
        } else return component.getCarID().toLowerCase().contains(lowerCase);
    }

    public boolean filterAdjustmentList(Adjustment adjustment, String newValue) {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String lowerCase = newValue.toLowerCase();

        if (String.valueOf(adjustment.getAdjustmentID()).toLowerCase().contains(lowerCase)) {
            return true;
        } else if (adjustment.getAdjustmentType().toLowerCase().contains(lowerCase)) {
            return true;
        } else if (adjustment.getAdjustmentDescription().toLowerCase().contains(lowerCase)) {
            return true;
        } else return Integer.toString(adjustment.getAdjustmentPrice()).toLowerCase().contains(lowerCase);
    }


    public boolean filterOrderList(Order order, String newValue) {

        if (newValue == null || newValue.isEmpty()) {
            return true;
        }
        String lowerCase = newValue.toLowerCase();

        if (String.valueOf(order.getOrderNr()).toLowerCase().contains(lowerCase)) {
            return true;
        } else if (Integer.toString(order.getPersonId()).toLowerCase().equals(lowerCase)) {
            return true;
        } else if (order.getCarId().toLowerCase().contains(lowerCase)) {
            return true;
        } else if (Integer.toString(order.getTotalPrice()).toLowerCase().equals(lowerCase)) {
            return true;
        } else if (order.getCarColor().toLowerCase().contains(lowerCase)) {
            return true;
        } else if (Boolean.toString(order.getOrderStatus()).toLowerCase().contains(lowerCase)) {
            return true;
        }
        for (Component i : order.getComponentList()) {
            if (i.getComponentID().toLowerCase().contains(lowerCase)) {
                return true;
            } else if (i.getComponentType().toLowerCase().contains(lowerCase)) {
                return true;
            } else if (i.getComponentDescription().toLowerCase().contains(lowerCase)) {
                return true;
            }

        }
        for (Adjustment i : order.getAdjustmentList()) {
            if (i.getAdjustmentID().toLowerCase().equals(lowerCase)) {
                return true;
            } else if (i.getAdjustmentType().toLowerCase().contains(lowerCase)) {
                return true;
            }
        }

        return false;
    }
}
