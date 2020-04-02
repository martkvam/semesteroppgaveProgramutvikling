package javaCode.user;

import javaCode.Adjustment;
import javaCode.Car;
import javaCode.Component;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Methods {
    //Metode for å lage liste som skal fylle biltype-choicebox
    public static ArrayList<String> typeList(ObservableList<Car> inList) {
        ArrayList<Car> aList = new ArrayList<>();
        for (Car car : inList) {
            char ID = car.getCarID().charAt(0);
            boolean found = false;
            for (Car value : aList) {
                char ID2 = value.getCarID().charAt(0);
                if (ID == ID2) {
                    found = true;
                    break;
                }
            }
            if (!found){
                aList.add(car);
            }
        }

        ArrayList<String> outList = new ArrayList<>();
        for(Car car : aList){
            outList.add(car.getCarType());
        }
        return outList;

    }

    //Metode for å lage liste som skal fylle komponent-choicebox
    public static ArrayList<String> componentList(ObservableList<Component> inList){
        ArrayList<Component> aList = new ArrayList<>();
        for (Component component : inList) {
            char ID = component.getComponentID().charAt(0);
            boolean found = false;
            for (Component value : aList) {
                char ID2 = value.getComponentID().charAt(0);
                if (ID == ID2) {
                    found = true;
                    break;
                }
            }
            if (!found){
                aList.add(component);
            }
        }

        ArrayList<String> outList = new ArrayList<>();
        for(Component component : aList){
            outList.add(component.getComponentType());
        }
        return outList;
    }
}
