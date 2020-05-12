package javaCode.ReaderWriter.Car;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.ReaderWriter.Reader;
import javaCode.objects.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class fileReaderTxt implements Reader {
    private static ObservableList<Car> saveList = FXCollections.observableArrayList();
    private int lineNr = 0;
    public String errorMessage = "Invalid formatting in: \n";
    @Override
    public void read(Path path) throws IOException {
        boolean invalidImplementation = false;
        Lists lists = new Lists();
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            while ((line = reader.readLine()) != null){
                try{
                    String [] split = line.split(";");
                    if (line.length()!=0) {
                        lineNr++;
                        saveList.add(parseCar(line));
                    }
                }catch(IllegalArgumentException e){
                    invalidImplementation = true;
                    errorMessage += "Line " + lineNr + "\n";
                }
            }
        } catch (IllegalArgumentException e) {
            invalidImplementation = true;
        }
        if(invalidImplementation){
            Dialogs.showErrorDialog("There is invalid formatting and data in the file. Invalid formatting will not be saved, but invalid data will be saved as 0 price");
        }
        for(Car i : saveList){
            lists.addCar(i);
        }
        System.out.println(errorMessage);
    }

    private Car parseCar (String line) throws IllegalArgumentException{

        String[] split = line.split(";");
        if(split.length != 4) {
            throw new IllegalArgumentException("There is an error in the file containing the orders.");
        }

        String carId = split[0];
        String carType = split[1];
        String carDescription = split[2];
        try{
            int carPrice = parseNumber(split[3]);
            return new Car(carId, carType, carDescription, carPrice);
        }catch (IllegalArgumentException e){
            try{
                int highestId = 0;
                for(Car i : Lists.getCars()){
                    highestId = Integer.parseInt(i.getCarID());
                }
                highestId += lineNr;
                if (carType.length() == 0) {
                    carType = "?";
                }
                if (carDescription.length() == 0) {
                    carDescription = "?";
                }
                return new Car(Integer.toString(highestId), carType, carDescription, 0);

            } catch (IllegalArgumentException ignored) {

            }
        }
        return null;
    }

    private int parseNumber(String str) throws IllegalArgumentException {
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Total price is not correct");
        }
        return number;
    }
}
