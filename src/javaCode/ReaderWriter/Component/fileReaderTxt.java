package javaCode.ReaderWriter.Component;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.ReaderWriter.Reader;
import javaCode.Validator;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class fileReaderTxt implements Reader {
    private static ObservableList<Component> saveList = FXCollections.observableArrayList();
    private int lineNr = 0;
    private int highestID = 0;
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
                        highestID = 0;
                        lineNr++;
                        lists.addComponent(parseComponent(line));
                    }
                }catch(IllegalArgumentException e){
                    invalidImplementation = true;
                    errorMessage += "Line " + lineNr + " : " + e.getMessage() + "\n";
                }
            }
        } catch (IllegalArgumentException e) {
            invalidImplementation = true;
        }
        if(invalidImplementation){
            Dialogs.showErrorDialog("There is invalid formatting and data in the file. Invalid lines are shown in output");
            System.out.println(errorMessage);
        }

    }

    private Component parseComponent (String line) throws IllegalArgumentException{

        String[] split = line.split(";");
        if(split.length != 5) {
            throw new IllegalArgumentException("There is an error in the file containing the orders.");
        }

        String carID = split[0];
        String componentID = split[1];
        String componentType = split[2];
        String [] splitID = componentID.split("-");

        String componentDescription = split[3];

        try{
            int componentPrice = parseNumber(split[4], "Total price is not correct");
            return new Component(carID,componentID, componentType, componentDescription, componentPrice);
        }catch (IllegalArgumentException e){
            try{
                boolean isValid = false;
                for(Component i : Lists.getComponents()){
                    String [] splitExistingID = i.getComponentID().split("-");
                    if(splitID[0].equals(splitExistingID[0])){
                        isValid = true;
                        componentType = i.getComponentType();
                        for(Component j : Lists.getComponents()){
                            String [] splitExistingID2 = j.getComponentID().split("-");
                            if(Integer.parseInt(splitExistingID2[1]) > highestID){
                                highestID = Integer.parseInt(splitExistingID2[1])+1;
                            }
                        }
                        componentID = splitID[0]+"-"+highestID;
                        break;
                    }
                }
                if(!isValid){
                    throw new IllegalArgumentException("The componentID is invalid");
                }
                return new Component("1",componentID, componentType, componentDescription, 0);

            }catch (IllegalArgumentException f){
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
