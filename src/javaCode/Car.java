package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Car implements Serializable {
    private transient SimpleStringProperty carID;
    private transient  SimpleStringProperty carType;
    private transient  SimpleStringProperty description;
    private transient  SimpleIntegerProperty price;

    public Car(String carID, String carType, String description, int price) {
        this.carID = new SimpleStringProperty(carID);
        this.carType = new SimpleStringProperty(carType);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getCarID() {
        return carID.getValue();
    }


    public void setCarID(String carID) {
        this.carID.set(carID);
    }

    public String getCarType() {
        return carType.getValue();
    }

    public void setCarType(String carType) {
        this.carType.set(carType);
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getPrice() {
        return price.getValue();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(carID.getValue());
        s.writeUTF(carType.getValue());
        s.writeUTF(description.getValue());
        s.writeInt(price.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException {
        String carID = s.readUTF();
        String carType = s.readUTF();
        String description = s.readUTF();
        int price = s.readInt();


        this.carID = new SimpleStringProperty(carID);
        this.carType = new SimpleStringProperty(carType);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);

    }
}
