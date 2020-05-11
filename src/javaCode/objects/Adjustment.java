package javaCode.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Adjustment implements Serializable {
    private transient SimpleStringProperty adjustmentID;
    private transient SimpleStringProperty adjustmentType;
    private transient SimpleStringProperty adjustmentDescription;
    private transient SimpleIntegerProperty adjustmentPrice;

    public Adjustment(String adjustmentID, String type, String description, int price) {
        this.adjustmentID = new SimpleStringProperty(adjustmentID);
        this.adjustmentType = new SimpleStringProperty(type);
        this.adjustmentDescription = new SimpleStringProperty(description);
        this.adjustmentPrice = new SimpleIntegerProperty(price);
    }

    public String getAdjustmentID() {
        return adjustmentID.getValue();
    }

    public void setAdjustmentID(String adjustmentID) {
        this.adjustmentID.set(adjustmentID);
    }

    public String getAdjustmentType() {
        return adjustmentType.getValue();
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType.set(adjustmentType);
    }

    public String getAdjustmentDescription() {
        return adjustmentDescription.getValue();
    }

    public void setAdjustmentDescription(String adjustmentDescription) {
        this.adjustmentDescription.set(adjustmentDescription);
    }

    public int getAdjustmentPrice() {
        return adjustmentPrice.getValue();
    }

    public void setAdjustmentPrice(int adjustmentPrice) {
        this.adjustmentPrice.set(adjustmentPrice);
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(adjustmentID.getValue());
        s.writeUTF(adjustmentType.getValue());
        s.writeUTF(adjustmentDescription.getValue());
        s.writeInt(adjustmentPrice.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException {
        String adjustmentID = s.readUTF();
        String type = s.readUTF();
        String description = s.readUTF();
        int price = s.readInt();

        this.adjustmentID = new SimpleStringProperty(adjustmentID);
        this.adjustmentType = new SimpleStringProperty(type);
        this.adjustmentDescription = new SimpleStringProperty(description);
        this.adjustmentPrice = new SimpleIntegerProperty(price);
    }
}

