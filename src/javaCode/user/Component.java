package javaCode.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Collection;

public class Component {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty type;
    private SimpleIntegerProperty price;

    public Component(int ID, String type, int price) {
        this.ID = new SimpleIntegerProperty(ID);
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleIntegerProperty(price);
    }

    public int getID() {
        return ID.getValue();
    }

    public void setID(int ID) {
        this.ID.set(ID);
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
