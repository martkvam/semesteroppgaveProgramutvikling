package javaCode.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Collection;

public class Component {
    private SimpleIntegerProperty carID;
    private SimpleIntegerProperty componentID;
    private SimpleStringProperty carType;
    private SimpleStringProperty componentType;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public Component(int carID, int componentID, String carType, String componentType, String description, int price) {
        this.carID = new SimpleIntegerProperty(carID);
        this.componentID = new SimpleIntegerProperty(componentID);
        this.carType = new SimpleStringProperty(carType);
        this.componentType = new SimpleStringProperty(componentType);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
    }

    public int getCarID() {
        return carID.getValue();
    }

    public void setCarID(int ID) {
        this.carID.set(ID);
    }

    public int getComponentID() {
        return componentID.getValue();
    }


    public void setComponentID(int componentID) {
        this.componentID.set(componentID);
    }

    public String getCarType() {
        return carType.getValue();
    }


    public void setCarType(String type) {
        this.carType.set(type);
    }

    public String getComponentType(){
        return componentType.getValue();
    }

    public void setComponentType(String type){
        this.componentType.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }


    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getDescription() {
        return description.getValue();
    }


    public void setDescription(String description) {
        this.description.set(description);
    }
}

