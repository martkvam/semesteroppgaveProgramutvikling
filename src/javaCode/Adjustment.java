package javaCode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Adjustment implements Serializable {
    private transient  SimpleStringProperty adjustmentID;
    private transient  SimpleStringProperty type;
    private transient  SimpleStringProperty description;
    private transient  SimpleIntegerProperty price;

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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(adjustmentID.getValue());
        s.writeUTF(type.getValue());
        s.writeUTF(description.getValue());
        s.writeInt(price.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException {
        String adjustmentID = s.readUTF();
        String type = s.readUTF();
        String description = s.readUTF();
        int price = s.readInt();

        this.adjustmentID = new SimpleStringProperty(adjustmentID);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
    }
}

