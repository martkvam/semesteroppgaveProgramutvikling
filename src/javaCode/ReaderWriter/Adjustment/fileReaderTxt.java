package javaCode.ReaderWriter.Adjustment;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.ReaderWriter.Reader;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class fileReaderTxt implements Reader {
    private static ObservableList<Adjustment> saveList = FXCollections.observableArrayList();
    private int lineNr = 0;
    private int highestID = 0;
    public String errorMessage = "Invalid formatting in: \n";

    @Override
    public void read(Path path) throws IOException {
        boolean invalidImplementation = false;
        Lists lists = new Lists();
        for(Adjustment i : Lists.getAdjustment()){
            if(Integer.parseInt(i.getAdjustmentID())>highestID){
                highestID = Integer.parseInt(i.getAdjustmentID());
            }

        }
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            while ((line = reader.readLine()) != null){
                try{
                    String [] split = line.split(";");
                    if (line.length()!=0) {
                        lineNr++;
                        lists.addAdjustment(parseAdjustment(line));
                    }
                }catch(IllegalArgumentException e){
                    invalidImplementation = true;

                    errorMessage += "Line " + lineNr + " : " +e.getMessage() +"\n";
                }
            }
        } catch (IllegalArgumentException e) {
            invalidImplementation = true;
        }
        if(invalidImplementation){
            Dialogs.showErrorDialog("There is invalid formatting and data in the file. Invalid formatting will not be saved, but invalid data will be saved as 0 price");
            System.out.println(errorMessage);
        }
    }

    private Adjustment parseAdjustment (String line) throws IllegalArgumentException{

        String[] split = line.split(";");
        if(split.length != 4) {
            throw new IllegalArgumentException("There is an error in the line formatting.");
        }

        String adjustmentID = split[0];
        String adjustmentType = split[1];
        String adjustmentDescription = split[2];
        try{
            int adjustmentPrice = parseNumber(split[3], "Total price is not correct");
            return new Adjustment(adjustmentID, adjustmentType, adjustmentDescription, adjustmentPrice);
        }catch (IllegalArgumentException e){
            try{

                highestID +=1;
                if(adjustmentType.length()==0){
                    adjustmentType = "?";
                }
                if(adjustmentDescription.length()==0){
                    adjustmentDescription = "?";
                }
                return new Adjustment(Integer.toString(highestID),adjustmentType,adjustmentDescription,0);

            } catch (IllegalArgumentException f){
                throw new IllegalArgumentException(f.getMessage());
            }
        }
    }
    private int parseNumber(String str, String errorMessage) throws IllegalArgumentException{
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
        return number;
    }

}


