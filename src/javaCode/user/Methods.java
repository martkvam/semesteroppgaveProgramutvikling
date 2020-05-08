package javaCode.user;

import javaCode.Adjustment;
import javaCode.Car;
import javaCode.Component;
import javaCode.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Methods {
    private Lists lists = new Lists();
    //Method that makes a list to fill the "choose cartype"-choicebox
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

    //Method that makes a list to fill the component-choicebox
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

    public static ObservableList<Component> setBasicComponentList(String carID){
        ObservableList<Component> list = FXCollections.observableArrayList();
        for (Component c : Lists.getComponents()){
            if (c.getCarID().equals(carID) ){
                list.add(c);
            }
        }
        return list;
    }

    /*public static ObservableList<Adjustment> setBasicAdjustmentList(){
        ObservableList<Adjustment> list = FXCollections.observableArrayList();
        for(Adjustment a : Lists.getAdjustment()){
           if(a.getAdjustmentID().equals("1") || a.getAdjustmentID().equals("4") || a.getAdjustmentID().equals("5")
                   || a.getAdjustmentID().equals("7") || a.getAdjustmentID().equals("9") || a.getAdjustmentID().equals("11")){
               list.add(a);
            }
        }
        return list;
    }


    public static ObservableList<Component> setPremiumComponentList(String carID){


    }

    public static ObservableList<Adjustment> setPremiumAdjustmentList(String carID){


    }

    public static ObservableList<Component> setSportComponentList(String carID){

    }

    public static ObservableList<Adjustment> setSportAdjustmentList(String carID){

    }*/
}
