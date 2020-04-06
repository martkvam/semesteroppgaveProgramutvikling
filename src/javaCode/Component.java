package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Collection;

public class Component {
    private SimpleStringProperty carID;
    private SimpleStringProperty componentID;
    private SimpleStringProperty componentType;
    private SimpleStringProperty componentDescription;
    private SimpleIntegerProperty componentPrice;

    public Component(String carID, String componentID, String componentType, String componentDescription, int componentPrice) {
        this.carID = new SimpleStringProperty(carID);
        this.componentID = new SimpleStringProperty(componentID);
        this.componentType = new SimpleStringProperty(componentType);
        this.componentDescription = new SimpleStringProperty(componentDescription);
        this.componentPrice = new SimpleIntegerProperty(componentPrice);
    }

    public String getCarID() {
        return carID.getValue();
    }

    public void setCarID(String ID) {
        this.carID.set(ID);
    }

    public String getComponentID() {
        return componentID.getValue();
    }


    public void setComponentID(String componentID) {
        this.componentID.set(componentID);
    }


    public String getComponentType(){
        return componentType.getValue();
    }

    public void setComponentType(String type){
        this.componentType.set(type);
    }

    public int getComponentPrice() {
        return componentPrice.getValue();
    }


    public void setComponentPrice(int componentPrice) {
        this.componentPrice.set(componentPrice);
    }

    public String getComponentDescription() {
        return componentDescription.getValue();
    }


    public void setComponentDescription(String componentDescription) {
        this.componentDescription.set(componentDescription);
    }
}

