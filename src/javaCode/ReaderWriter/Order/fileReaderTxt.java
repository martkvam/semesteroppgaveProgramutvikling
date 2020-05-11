package javaCode.ReaderWriter.Order;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.ReaderWriter.Reader;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javaCode.objects.Order;
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
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                if (!split[0].isEmpty()) {
                    lists.addOrder(parseOrder(line));
                } else lists.addOngoingOrder(parseOrder(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showErrorDialog("There is an error in the file containing the finished orders." +
                    " Therefore, all users might not be able to see all their finished orders.");
        }
    }


    private Order parseOrder(String line) throws Exception {
        String[] split = line.split(";");
        if (split.length != 10) {
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


        ObservableList<Component> componentList = parseComponentList(split[5], "The component list is not correct");
        ObservableList<Adjustment> adjustmentList = parseAdjustmentList(split[6], "The adjustment list is not correct");
        int totPrice = parseNumber(split[7], "Total price is not correct");
        String carColor = split[8];
        boolean orderStatus;
        String stringOrderStatus = split[9];
        if (stringOrderStatus.equals("true") || stringOrderStatus.equals("True")) {
            orderStatus = true;
        } else if (stringOrderStatus.equals("false") || stringOrderStatus.equals("False")) {
            orderStatus = false;
        } else throw new Exception("The order status is not correct");

        return new Order(orderNr, personId, carId, orderStarted, orderFinished, componentList, adjustmentList, totPrice, carColor, orderStatus);
    }

    private int parseNumber(String str, String errorMessage) throws Exception {
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new Exception(errorMessage);
        }
        return number;
    }

    private ObservableList<Component> parseComponentList(String str, String errorMessage) throws Exception {
        ObservableList<Component> components = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for (String string : split) {
            for (Component c : Lists.getComponents()) {
                if (c.getComponentID().equals(string)) {
                    components.add(c);
                }
            }
        }
        return components;
    }

    private ObservableList<Adjustment> parseAdjustmentList(String str, String errorMessage) throws Exception {
        ObservableList<Adjustment> adjustments = FXCollections.observableArrayList();
        String[] split = str.split(",");
        for (String string : split) {
            for (Adjustment a : Lists.getAdjustment()) {
                if (a.getAdjustmentID().equals(string)) {
                    adjustments.add(a);
                }
            }
        }
        return adjustments;
    }
}
