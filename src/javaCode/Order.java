package javaCode;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order implements Serializable {
    private transient SimpleStringProperty orderNr;
    private transient SimpleIntegerProperty personId;
    private transient SimpleStringProperty carId;
    private transient SimpleObjectProperty<Date> orderStarted;
    private transient SimpleObjectProperty<Date> orderFinished;
    private transient SimpleListProperty<Component> componentList;
    private transient SimpleListProperty<Adjustment> adjustmentList;
    private transient SimpleIntegerProperty totPrice;
    private transient SimpleStringProperty carColor;
    private transient SimpleBooleanProperty orderStatus;

    public Order(String orderNr, int personId, String carId, Date orderStarted, Date orderFinished, ObservableList<Component> componentList, ObservableList<Adjustment> adjustmentList, int totPrice, String carColor, boolean orderStatus) {
        this.orderNr = new SimpleStringProperty(orderNr);
        this.personId = new SimpleIntegerProperty(personId);
        this.carId = new SimpleStringProperty(carId);
        this.orderStarted = new SimpleObjectProperty<>(orderStarted);
        this.orderFinished = new SimpleObjectProperty<>(orderFinished);
        this.componentList = new SimpleListProperty<Component>(componentList);
        this.adjustmentList = new SimpleListProperty<Adjustment>(adjustmentList);
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


    public String getCarId() {
        return carId.getValue();
    }


    public void setCarId(String carId) {
        this.carId.set(carId);
    }


    public Date getOrderStarted(){
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
        s.writeUTF(getCarId());
        s.writeObject(getOrderStarted());
        s.writeObject(getOrderFinished());
        writeListProp(s,componentList);
        writeListProp(s,adjustmentList);
        s.writeInt(getTotalPrice());
        s.writeUTF(getCarColor());
        s.writeBoolean(getOrderStatus());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        String orderNr = s.readUTF();
        int personId = s.readInt();
        String carId = s.readUTF();
        Date orderStarted =(Date) s.readObject();
        Date orderFinished = (Date) s.readObject();
        ListProperty<Component> componentList = readListProp(s);
        ListProperty<Adjustment> adjustmentList = readListProp(s);
        int totalPrice = s.readInt();
        String carColor = s.readUTF();
        boolean orderStatus = s.readBoolean();

        this.orderNr = new SimpleStringProperty(orderNr);
        this.personId = new SimpleIntegerProperty(personId);
        this.carId = new SimpleStringProperty(carId);
        this.orderStarted = new SimpleObjectProperty<>(orderStarted);
        this.orderFinished = new SimpleObjectProperty<>(orderFinished);
        this.componentList = new SimpleListProperty<>(FXCollections.observableArrayList(componentList));
        this.adjustmentList = new SimpleListProperty<>(FXCollections.observableArrayList(adjustmentList));
        this.totPrice = new SimpleIntegerProperty(totalPrice);
        this.carColor = new SimpleStringProperty(carColor);
        this.orderStatus = new SimpleBooleanProperty(orderStatus);
    }
    public static void writeListProp(ObjectOutputStream s, SimpleListProperty lstProp) throws IOException {
        if (lstProp == null || lstProp.getValue() == null) {
            s.writeInt(0);
            return;
        }
        s.writeInt(lstProp.size());
        for (Object elt : lstProp.getValue()){
            s.writeObject(elt);
        }
    }

    public static ListProperty readListProp(ObjectInputStream s) throws IOException, ClassNotFoundException {
        ListProperty lst=new SimpleListProperty(FXCollections.observableArrayList());
        int loop=s.readInt();
        for(int i = 0;i<loop;i++) {
            lst.add(s.readObject());
        }

        return lst;
    }

    public String formatComponents(ObservableList<Component> componentList) {
        String out = "";
        String DELIMITER = ",";
        for (int i = 0; i < componentList.size(); i++){
            out += componentList.get(i).getComponentID();
            if(i < componentList.size() - 1){
                out += DELIMITER;
            }
        }
        return out;
    }

    public String formatAdjustments(ObservableList<Adjustment> adjustmentList) {
        String out = "";
        String DELIMITER = ",";
        for (int i = 0; i < adjustmentList.size(); i++){
            out += adjustmentList.get(i).getAdjustmentID();
            if (i < adjustmentList.size() - 1){
                out += DELIMITER;
            }
        }
        return out;
    }
}
