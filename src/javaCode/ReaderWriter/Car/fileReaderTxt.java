package javaCode.ReaderWriter.Car;

import javaCode.Lists;
import javaCode.ReaderWriter.Reader;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
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
                    lists.addCar(parseCar(line));
                }
                else lists.addCar(parseCar(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Car parseCar (String line) throws Exception{
        String[] split = line.split(";");
        if(split.length != 4) {
            throw new Exception("There is an error in the file containing the orders.");
        }


        String carId = split[0];
        String carType = split[1];
        String carDescription = split[2];

        int carPrice = parseNumber(split[3], "Total price is not correct");


        Car car = new Car(carId, carType, carDescription, carPrice);
        return car;
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
}
