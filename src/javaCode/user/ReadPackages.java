package javaCode.user;


import javaCode.Adjustment;
import javaCode.Component;
import javaCode.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadPackages {
    public void read(Path path, String carID, String packageId) throws Exception {
        Lists lists = new Lists();
        Lists.deleteBasePackageAdjustments();
        Lists.deleteBasePackageComponents();
        try {
           BufferedReader reader = Files.newBufferedReader(path);
           String line;
           while((line = reader.readLine()) != null ){
               String [] split = line.split(";");
               if(split[0].equals(carID) && split[1].equals(packageId)){
                   Lists.getBasePackageAdjustments().addAll(parseAdjustmentList(split[3]));
                   Lists.getBasePackageComponents().addAll(parseComponentList(split[2]));
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ObservableList<Component> parseComponentList(String str) throws Exception{
        ObservableList<Component> components = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for(String string : split){
            for(Component c : Lists.getComponents()){
                if (c.getComponentID().equals(string)){
                    components.add(c);
                }
            }
        }
        return components;
    }

    private ObservableList<Adjustment> parseAdjustmentList(String str) throws Exception{
        ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for(String string : split){
            for(Adjustment a : Lists.getAdjustment()){
                if (a.getAdjustmentID().equals(string)){
                    adjustments.add(a);
                }
            }
        }
        return adjustments;
    }
}