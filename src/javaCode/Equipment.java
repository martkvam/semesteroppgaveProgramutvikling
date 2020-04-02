package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Equipment{
    private SimpleStringProperty type;
    private SimpleIntegerProperty price;

    public Equipment(String type, int price) {
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleIntegerProperty(price);
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
