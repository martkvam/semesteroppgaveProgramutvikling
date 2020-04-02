package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Car {
    private SimpleStringProperty carID;
    private SimpleStringProperty carType;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

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
}
