package javaCode;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order implements Serializable {
    private transient SimpleStringProperty orderNr;
    private transient SimpleIntegerProperty personId;
    private transient SimpleIntegerProperty carId;
    private transient SimpleObjectProperty<Date> orderStarted;
    private transient SimpleObjectProperty<Date> orderFinished;
    private transient SimpleListProperty<Component> componentList;
    private transient SimpleListProperty<Adjustment> adjustmentList;
    private transient SimpleIntegerProperty totPrice;
    private transient SimpleStringProperty carColor;
    private transient SimpleBooleanProperty orderStatus;

    public Order(String orderNr, int personId, int carId, Date orderStarted, Date orderFinished, ObservableList<Component> componentList, ObservableList<Adjustment> adjustmentList, int totPrice, String carColor, boolean orderStatus) {
        this.orderNr = new SimpleStringProperty(orderNr);
        this.personId = new SimpleIntegerProperty(personId);
        this.carId = new SimpleIntegerProperty(carId);
        this.orderStarted = new SimpleObjectProperty<>(orderStarted);
        this.orderFinished = new SimpleObjectProperty<>(orderFinished);
        this.componentList = new SimpleListProperty<>(FXCollections.observableArrayList(componentList));
        this.adjustmentList = new SimpleListProperty<>(FXCollections.observableArrayList(adjustmentList));
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
        return componentList.getValue();
    }

    public void setComponentList(ObservableList<Component> componentList) {
        this.componentList.set(componentList);
    }


    public ObservableList<Adjustment> getAdjustmentList() {
        return adjustmentList.getValue();
    }

    public void setAdjustmentList(ObservableList<Adjustment> adjustmentList) {
        this.adjustmentList.set(adjustmentList);
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getOrderNr());
        s.writeInt(getPersonId());
        s.writeObject(getOrderStarted());
        s.writeObject(getOrderFinished());
        writeListProp(s, (ListProperty) getComponentList());
        s.writeObject(getAdjustmentList());
        s.writeInt(getTotalPrice());
        s.writeUTF(getCarColor());
        s.writeBoolean(getOrderStatus());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        String orderNr = s.readUTF();
        int personId = s.readInt();
        Date orderStarted =(Date) s.readObject();
        Date orderFinished = (Date) s.readObject();
        ObservableList<Component> componentList = (ObservableList<Component>) s.readObject();
        ObservableList<Adjustment> adjustmentList = (ObservableList<Adjustment>) s.readObject();
        int totalPrice = s.readInt();
        String carColor = s.readUTF();
        boolean orderStatus = s.readBoolean();

        this.orderNr = new SimpleStringProperty(orderNr);
        this.personId = new SimpleIntegerProperty(personId);
        this.orderStarted = new SimpleObjectProperty<>(orderStarted);
        this.orderFinished = new SimpleObjectProperty<>(orderFinished);
        this.componentList = new SimpleListProperty<>(componentList);
        this.adjustmentList = new SimpleListProperty<>(adjustmentList);
        this.totPrice = new SimpleIntegerProperty(totalPrice);
        this.carColor = new SimpleStringProperty(carColor);
        this.orderStatus = new SimpleBooleanProperty(orderStatus);
    }
    public static void writeListProp(ObjectOutputStream s, ListProperty lstProp) throws IOException {
        if (lstProp == null || lstProp.getValue() == null) {
            s.writeInt(0);
            return;
        }
        s.writeInt(lstProp.size());
        for (Object elt : lstProp.getValue()) s.writeObject(elt);
    }

    public String formatComponents(ObservableList<Component> componentList) {
        String out = "";
        String DELIMITER = ";";
        for (Component c : componentList){
            out += c.getComponentType() + DELIMITER;
        }
        return out;
    }

    public String formatAdjustments(ObservableList<Adjustment> adjustmentList) {
        String out = "";
        String DELIMITER = ";";
        for (Adjustment a : adjustmentList){
            out += a.getAdjustmentType() + DELIMITER;
        }
        return out;
    }
}
