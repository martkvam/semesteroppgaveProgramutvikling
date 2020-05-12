package javaCode.user;


import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadPackages {
    public void read(Path path, String carID, String packageId){
        //Lists lists = new Lists();
        Lists.deleteBasePackageAdjustments();
        Lists.deleteBasePackageComponents();
        try {
           BufferedReader reader = Files.newBufferedReader(path);
           String line;
           while((line = reader.readLine()) != null ){
               String [] split = line.split(";");
               if(split.length != 4){
                   Dialogs.showErrorDialog("There is an error in the file containing the base packages."
                   + " All packages might not be loaded correctly.");
                   System.err.println("There is an error in the file containing the base packages. The line: " + line
                   + " is not in the right format");
               }
               if(split[0].equals(carID) && split[1].equals(packageId)){
                   Lists.getBasePackageAdjustments().addAll(parseAdjustmentList(split[3]));
                   Lists.getBasePackageComponents().addAll(parseComponentList(split[2], split[0]));
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ObservableList<Component> parseComponentList(String componentList, String carID) {
        ObservableList<Component> components = FXCollections.observableArrayList();
        String[] split = componentList.split(",");

        //Iterating through the list of components from the file and matching them with the components in the register.
        //If they belong to the right car they are added to the base package, if not they are ignored and an error message
        //is displayed to the user.
        for (String string : split) {
            boolean componentExists = false;
            for (Component c : Lists.getComponents()) {
                if (c.getComponentID().equals(string)) {
                    componentExists = true;
                    if(c.getCarID().equals(carID)) {
                        components.add(c);
                    }
                    else {
                        Dialogs.showErrorDialog("There is an error in the file containing the packages, as a component" +
                                " belonging to another car type was added to this package. That component has been removed," +
                                " but as a result this package might not be complete.");
                        System.err.println("There is an error in this base package. The component list: " + componentList +
                                "contains component from different car types.");
                    }
                }
            }
            //If the file is in the right format, but a component-Id in the file is not found in the register, an error
            //message is displayed in the terminal.
            if (!componentExists){
                System.err.println("There is an error in the file containing the packages. The component list: " + componentList
                + " is contains a componentID that does not exist: " + string);
            }
        }
        return components;
    }

    private ObservableList<Adjustment> parseAdjustmentList(String str) {
        ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for (String string : split) {
            boolean adjustmentExists = false;
            for (Adjustment a : Lists.getAdjustment()) {
                if (a.getAdjustmentID().equals(string)) {
                    adjustmentExists = true;
                    adjustments.add(a);
                }
            }
            //If the file is in the right format, but an adjustmentID in the file is not found in the
            //adjustment register, an error message is displayed in the terminal.
            if (!adjustmentExists){
                System.err.println("There is an error in the file containing the packages. The adjustmenlist: " +
                        str + " contains an adjustmentID that does not exist: " + string);
            }
        }
        return adjustments;
    }
}