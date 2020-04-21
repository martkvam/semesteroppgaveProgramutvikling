package javaCode.superUser;

import javaCode.Component;
import javaCode.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.IOException;
import java.util.stream.Collectors;

public class tableFilter {

//Filtered list
    public ObservableList<Component> filteredList = new FilteredList<>(Lists.getComponents());

    //Method to make the filtered list
    public ObservableList<Component> filterComponents(String inputString, String choosenFilter) throws IOException {
        inputString.toLowerCase();

        //if input i a number
        if(inputString.matches("[0-9]+")){
                if(choosenFilter.equals("Component price")){
                    //Sets up the filtered list by a filtered stream
                    filteredList = Lists.getComponents().stream().filter(s -> s.getComponentPrice() <= Integer.parseInt(inputString)).collect(Collectors.toCollection(FXCollections::observableArrayList));
            }
        }

        //If input is a string
        else{
            switch (choosenFilter){
                case "Component ID":
                    filteredList = Lists.getComponents().stream().filter(s -> s.getComponentID().toLowerCase().contains(inputString)).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    break;

                case "Component type":
                    filteredList = Lists.getComponents().stream().filter(s -> s.getComponentType().toLowerCase().contains(inputString)).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    break;

                case "Component description":
                    filteredList = Lists.getComponents().stream().filter(s -> s.getComponentDescription().toLowerCase().contains(inputString)).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    break;

                case "Car ID":
                    System.out.println("jada");
                    filteredList = Lists.getComponents().stream().filter(s -> s.getCarID().toLowerCase().equals(inputString)).collect(Collectors.toCollection(FXCollections::observableArrayList));
                    break;
            }

        }
        return filteredList;
    }
}
