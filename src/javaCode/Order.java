package javaCode;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.Date;


public class Order {
    private SimpleStringProperty orderNr;
    private SimpleIntegerProperty personId;
    private SimpleIntegerProperty carId;
    private SimpleObjectProperty<Date> orderStarted;
    private SimpleObjectProperty<Date> orderFinished;
    private ObservableList<Component> componentList;
    private ObservableList<Adjustment> adjustmentList;
    private SimpleIntegerProperty totPrice;
    private SimpleStringProperty carColor;
    private SimpleBooleanProperty orderStatus;

    public Order(String orderNr, int personId, int carId, Date orderStarted, Date orderFinished, ObservableList<Component> componentList, ObservableList<Adjustment> adjustmentList, int totPrice, String carColor, boolean orderStatus) {
        this.orderNr = new SimpleStringProperty(orderNr);
        this.personId = new SimpleIntegerProperty(personId);
        this.carId = new SimpleIntegerProperty(carId);
        this.orderStarted = new SimpleObjectProperty<>(orderStarted);
        this.orderFinished = new SimpleObjectProperty<>(orderFinished);
        this.componentList = componentList;
        this.adjustmentList = adjustmentList;
        this.totPrice = new SimpleIntegerProperty(totPrice);
        this.carColor = new SimpleStringProperty(carColor);
        this.orderStatus = new SimpleBooleanProperty(orderStatus);
    }

    public String getOrderNr() {
        return orderNr.getValue();
    }

    public void setOrderNr(String orderNr) {
        this.orderNr.set(orderNr);
    }


    public int getPersonId() {
        return personId.getValue();
    }

    public void setPersonId(int personId) {
        this.personId.set(personId);
    }


    public int getCarId() {
        return carId.getValue();
    }


    public void setCarId(int carId) {
        this.carId.set(carId);
    }


    public Date getOrderStarted() {
        return orderStarted.getValue();
    }

    public void setOrderStarted(Date orderStarted) {
        this.orderStarted.set(orderStarted);
    }


    public Date getOrderFinished() {
        return orderFinished.getValue();
    }

    public void setOrderFinished(Date orderFinished) {
        this.orderFinished.set(orderFinished);
    }


    public ObservableList<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(ObservableList<Component> componentList) {
        this.componentList = componentList;
    }


    public ObservableList<Adjustment> getAdjustmentList() {
        return adjustmentList;
    }

    public void setAdjustmentList(ObservableList<Adjustment> adjustmentList) {
        this.adjustmentList = adjustmentList;
    }


    public int getTotalPrice() {
        return totPrice.getValue();
    }

    public void setTotPrice(int totPrice) {
        this.totPrice.set(totPrice);
    }


    public String getCarColor() {
        return carColor.getValue();
    }

    public void setCarColor(String carColor) {
        this.carColor.set(carColor);
    }


    public boolean getOrderStatus() {
        return orderStatus.getValue();
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus.set(orderStatus);
    }

    public String formatComponents(ObservableList<Component> list){
        String DELIMITER = ";";
        String formatted = "";
        for (Component c : list){
            formatted += c.getComponentType() + DELIMITER;
        }
        return formatted;
    }

    public String formatAdjustments(ObservableList<Adjustment> list){
        String DELIMITER = ";";
        String formatted = "";
        for (Adjustment a : list){
            formatted += a.getType() + DELIMITER;
        }
        return formatted;
    }
}
