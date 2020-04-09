package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Adjustment {
    private SimpleStringProperty adjustmentID;
    private SimpleStringProperty type;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public Adjustment(String adjustmentID, String type, String description, int price) {
        this.adjustmentID = new SimpleStringProperty(adjustmentID);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
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

    public String getAdjustmentID() {
        return adjustmentID.getValue();
    }

    public void setAdjustmentID(String adjustmentID) {
        this.adjustmentID.set(adjustmentID);
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}

