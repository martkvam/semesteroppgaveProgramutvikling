package javaCode.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Collection;

public class Component {
    private SimpleIntegerProperty carID;
    private SimpleIntegerProperty componentID;
    private SimpleStringProperty type;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public Component(int carID, int componentID, String type, String description, int price) {
        this.carID = new SimpleIntegerProperty(carID);
        this.componentID = new SimpleIntegerProperty(componentID);
        this.type = new SimpleStringProperty(type);
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

    public String getType() {
        return type.getValue();
    }


    public void setType(String type) {
        this.type.set(type);
    }

    public int getPrice() {
        return price.getValue();
    }


    public void setPrice(int price) {
        this.price.set(price);
    }
}

class Motor extends Component {
    private SimpleIntegerProperty hk;

    public Motor(int ID, String type, int price, int hk) {
        super(ID, type, price);
        this.hk = new SimpleIntegerProperty(hk);
    }

    public int getHk() {
        return hk.getValue();
    }

    public void setHk(int hk) {
        this.hk.set(hk);
    }
}

class Wheel extends Component{
    public Wheel(int ID, String type, int price) {
        super(ID, type, price);
    }
}

class Rims extends Component{
    public Rims(int ID, String type, int price) {
        super(ID, type, price);
    }
}

class Setetrekk extends Component{
    public Setetrekk(int ID, String type, int price) {
        super(ID, type, price);
    }
}
