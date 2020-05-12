package javaCode.ReaderWriter.Order;

import javaCode.Dialogs;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javaCode.Lists;
import javaCode.objects.Order;
import javaCode.ReaderWriter.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class fileReaderTxt implements Reader {

    @Override
    public void read(Path path) throws IOException {
        Lists lists = new Lists();
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            while ((line = reader.readLine()) != null){
                String [] split = line.split(";");
                if (!split[0].isEmpty()) {
                    lists.addOrder(parseOrder(line));
                }
                else lists.addOngoingOrder(parseOrder(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showErrorDialog("There is an error in the file containing the finished orders." +
                    " Therefore, all users might not be able to see all their finished orders.");
        }
    }


    private Order parseOrder (String line) throws Exception{
        String[] split = line.split(";");
        //Checking to see that the line is in the right format
        if(!line.isEmpty() && split.length != 10) {
            throw new Exception("There is an error in the file containing the finished orders. The line: " + line +
                    " is not in the right format");
        }

        String orderNr = split[0];
        int personId = parseNumber(split[1], "Person ID is incorrect");
        String carId = split[2];

        String strOrderStarted = split[3];
        String strOrderFinished = split[4];
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzzz yyyy", Locale.ENGLISH);
        Date orderStarted = df.parse(strOrderStarted);
        Date orderFinished = df.parse(strOrderFinished);


        ObservableList<Component> componentList = parseComponentList(split[5], carId,  "The component list is not correct");
        ObservableList<Adjustment> adjustmentList = parseAdjustmentList(split[6], "The adjustment list is not correct");
        int totPrice = parseNumber(split[7], "Total price is not correct");
        String carColor = split[8];
        boolean orderStatus;
        String stringOrderStatus = split[9];
        if(stringOrderStatus.toLowerCase().equals("true")){
            orderStatus = true;
        }
        else if (stringOrderStatus.toLowerCase().equals("false")){
            orderStatus = false;
        }
        else throw new Exception("The order status is not correct");

        Order order = new Order(orderNr, personId, carId, orderStarted, orderFinished, componentList, adjustmentList, totPrice, carColor, orderStatus);
        return order;
    }

    private int parseNumber(String str, String errorMessage) throws Exception{
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new Exception(errorMessage);
        }
        return number;
    }

    private ObservableList<Component> parseComponentList(String str, String carID, String errorMessage) throws Exception{
        ObservableList<Component> components = FXCollections.observableArrayList();
        String[] split = str.split(",");
        //Iterating through all componentIDs from the file
        for(String string : split) {
            if (!string.isEmpty()) {
                boolean componentExists = false;
                boolean deleted = false;
                //Trying to match the componentIDs from the file with the list of existing components.
                for (Component c : Lists.getComponents()) {
                    if (c.getComponentID().equals(string)) {
                        componentExists = true;
                        //If a componentID is found in the list of existing components, this validates that the component
                        //belongs to the car type in the order.
                        if (c.getCarID().equals(carID)) {
                            components.add(c);
                        } else {
                            System.err.println("There is an error in the list containing the orders. " +
                                    "The component list : " + str + "contains a component belonging to a different" +
                                    " car type. ComponentID: " + c.getComponentID());
                        }
                    }
                }
                //If the componentID from the file does not match with a componentID in the existing components list,
                //we check if the componentID matches a componentID in the deleted component list. If they match, the
                //componentDescription is set to no longer available.
                if (!componentExists) {
                    for (Component c : Lists.getDeletedComponents()) {
                        if (c.getComponentID().equals(string)) {
                            deleted = true;
                            c.setComponentDescription("No longer available");
                            if (c.getCarID().equals(carID)) {
                                components.add(c);
                            }
                        }
                    }
                }
                //If the componentID is not found in either list, the ID must be wrong.
                if (!componentExists && !deleted) {
                    System.err.println("There is an error in the list containing the orders. The component list: " +
                            str + " contains a componentID that does not exist. Component id: " + string);
                }
            }
        }
        return components;
    }

    private ObservableList<Adjustment> parseAdjustmentList(String str, String errorMessage) throws Exception{
        ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for(String string : split) {
            if (!string.isEmpty()) {
                boolean exists = false;
                boolean deleted = false;
                for (Adjustment a : Lists.getAdjustment()) {
                    if (a.getAdjustmentID().equals(string)) {
                        exists = true;
                        adjustments.add(a);
                    }
                }
                if (!exists) {
                    for (Adjustment a : Lists.getDeletedAdjustment()) {
                        if (a.getAdjustmentID().equals(string)) {
                            deleted = true;
                            a.setAdjustmentDescription("No longer available");
                            adjustments.add(a);
                        }
                    }
                }
                if (!exists && !deleted) {
                    System.err.println("There is an error in the list containing the orders. The adjustment list: "
                            + str + " contains an adjustmentID that does not exist. AdjustmentID " + string);
                }
            }
        }
        return adjustments;
    }
}
