package javaCode;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javaCode.superUser.addElements;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.helpers.ValidationEventLocatorImpl;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

public class Component implements Serializable {
    private transient SimpleStringProperty carID;
    private transient SimpleStringProperty componentID;
    private transient SimpleStringProperty componentType;
    private transient SimpleStringProperty componentDescription;
    private transient SimpleIntegerProperty componentPrice;

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

    public void setCarID(String ID){
        if(!Validator.carIdComponents(ID)) {
            throw new IllegalArgumentException("This is not a valid car id");
        }
        this.carID.set(ID);
    }

    public String getComponentID() {
        return componentID.getValue();
    }


    public void setComponentID(String componentID) {
        if(!Validator.componentId(componentID)){
            if(Dialogs.showChooseDialog("The id is not valid? Add new component?")){
                addElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents());
            }
        }
        else{
            this.componentID.set(componentID);
        }

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
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(carID.getValue());
        s.writeUTF(componentID.getValue());
        s.writeUTF(componentType.getValue());
        s.writeUTF(componentDescription.getValue());
        s.writeInt(componentPrice.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException {
        String carID = s.readUTF();
        String componentID = s.readUTF();
        String componentType = s.readUTF();
        String componentDescription = s.readUTF();
        int componentPrice = s.readInt();

        this.carID = new SimpleStringProperty(carID);
        this.componentID = new SimpleStringProperty(componentID);
        this.componentType = new SimpleStringProperty(componentType);
        this.componentDescription = new SimpleStringProperty(componentDescription);
        this.componentPrice = new SimpleIntegerProperty(componentPrice);
    }
}

