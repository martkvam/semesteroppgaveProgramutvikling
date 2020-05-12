package javaCode;

import javaCode.ReaderWriter.Adjustment.fileReaderDeletedAdjustments;
import javaCode.ReaderWriter.Adjustment.fileWriteDeletedAdjustments;
import javaCode.ReaderWriter.Car.*;
import javaCode.ReaderWriter.Component.fileReaderDeletedComponents;
import javaCode.ReaderWriter.Component.fileWriterDeletedComponents;
import javaCode.ReaderWriter.Order.fileWriterTxt;
import javaCode.ReaderWriter.Reader;
import javaCode.ReaderWriter.Writer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class FileHandler{

    private enum DialogMode {Open, Save}

    public static void saveAllFiles(){
        File selectedFileCar = new File("src/dataBase/SuperUser/Cars.jobj");
        File selectedFileDeletedCar = new File("src/dataBase/SuperUser/deletedCars.jobj");
        File selectedFileComponent = new File("src/dataBase/SuperUser/Components.jobj");
        File selectedFileDeletedComponent = new File("src/dataBase/SuperUser/DeletedComponents.jobj");
        File selectedFileAdjustments = new File("src/dataBase/SuperUser/Adjustments.jobj");
        File selectedFileDeletedAdjustments = new File("src/dataBase/SuperUser/DeletedAdjustments.jobj");

        Writer writeCar = null;
        Writer writeDeletedCar = null;
        Writer writeComponent = null;
        Writer writeDeletedComponent = null;
        Writer writeAdjustment = null;
        Writer writeDeletedAdjustment = null;

        writeCar = new fileWriterJobj();
        writeDeletedCar = new fileWriterDeletedCars();
        writeComponent = new javaCode.ReaderWriter.Component.fileWriterJobj();
        writeDeletedComponent = new fileWriterDeletedComponents();
        writeAdjustment = new javaCode.ReaderWriter.Adjustment.fileWriterJobj();
        writeDeletedAdjustment = new fileWriteDeletedAdjustments();


        if(writeCar != null) {
            try {
                selectedFileCar.delete();
                selectedFileDeletedCar.delete();
                selectedFileComponent.delete();
                selectedFileDeletedComponent.delete();
                selectedFileAdjustments.delete();
                selectedFileDeletedAdjustments.delete();
                writeCar.save(selectedFileCar.toPath());
                writeDeletedCar.save(selectedFileDeletedCar.toPath());
                writeComponent.save(selectedFileComponent.toPath());
                writeDeletedComponent.save(selectedFileDeletedComponent.toPath());
                writeAdjustment.save(selectedFileAdjustments.toPath());
                writeDeletedAdjustment.save(selectedFileDeletedAdjustments.toPath());
                Dialogs.showSuccessDialog("The register successfully got saved");
            } catch (IOException e) {
                Dialogs.showErrorDialog("The saving failed because of: " + e.getMessage());
            }
        }
    }

    public static void saveSelectedFile(Stage stage) throws IOException {
        File selectedFile = new File("src/dataBase/FinishedOrders.txt");

        if (selectedFile != null) {
           try{
                Writer writeOrder = new fileWriterTxt();
                selectedFile.delete();
                writeOrder.save(selectedFile.toPath());
                Dialogs.showSuccessDialog("The register got saved");
                } catch (IOException e) {
                    Dialogs.showErrorDialog("Saving to file did not work because of: " + e.getMessage());
                }
            }
    }
    public static void readAllFiles(Stage stage) {

        File selectedFileCar = new File("src/dataBase/SuperUser/Cars.jobj");
        File selectedFileDeleteCar = new File("src/dataBase/SuperUser/deletedCars.jobj");
        File selectedFileComponent = new File("src/dataBase/SuperUser/Components.jobj");
        File selectedFileDeletedComponent = new File("src/dataBase/SuperUser/DeletedComponents.jobj");
        File selectedFileAdjustments = new File("src/dataBase/SuperUser/Adjustments.jobj");
        File selectedFileDeletedAdjustments = new File("src/dataBase/SuperUser/DeletedAdjustments.jobj");
        File selectedFileOrdersTxt = new File("src/dataBase/FinishedOrders.txt");
        File selectedFileOngoingOrders = new File("src/dataBase/OngoingOrders.txt");
        File selectedFileUsers = new File("src/dataBase/Users.txt");

        Reader readerCar = null;
        Reader readerDeletedCars = null;
        Reader readerComponents = null;
        Reader readerDeletedComponents = null;
        Reader readerAdjustments=null;
        Reader readerDeletedAdjustments = null;
        Reader readerTxtOrders = null;
        Reader readerOngoingOrders = null;
        Reader readerUsers = null;


        readerCar = new fileReaderJobj();
        readerDeletedCars = new fileReaderDeletedCars();
        readerComponents=new javaCode.ReaderWriter.Component.fileReaderJobj();
        readerDeletedComponents = new fileReaderDeletedComponents();
        readerAdjustments = new javaCode.ReaderWriter.Adjustment.fileReaderJobj();
        readerDeletedAdjustments = new fileReaderDeletedAdjustments();
        readerTxtOrders = new javaCode.ReaderWriter.Order.fileReaderTxt();
        readerOngoingOrders = new javaCode.ReaderWriter.Order.fileReaderTxt();
        readerUsers = new javaCode.ReaderWriter.User.fileReaderTxt();


        Lists.deleteCars();
        Lists.deleteComponents();
        Lists.deleteAdjustments();
        Lists.deleteOrders();
        Lists.deleteOngoing();
        Lists.deleteDeletedComponentList();
        Lists.deleteDeletedAdjustmentList();
        Lists.deleteDeletedCarlist();
            if(readerCar != null && readerComponents != null && readerAdjustments !=null && readerTxtOrders != null && readerOngoingOrders != null) {
                try {
                    readerCar.read(selectedFileCar.toPath());
                    readerDeletedCars.read(selectedFileDeleteCar.toPath());
                    readerComponents.read(selectedFileComponent.toPath());
                    readerDeletedComponents.read(selectedFileDeletedComponent.toPath());
                    readerAdjustments.read(selectedFileAdjustments.toPath());
                    readerDeletedAdjustments.read(selectedFileDeletedAdjustments.toPath());
                    readerTxtOrders.read(selectedFileOrdersTxt.toPath());
                    readerOngoingOrders.read(selectedFileOngoingOrders.toPath());
                    readerUsers.read(selectedFileUsers.toPath());
                } catch (IOException | IllegalArgumentException e) {
                    Dialogs.showErrorDialog("Opening the file failed because of: " + e.getMessage());
                } catch (Exception e){
                    Dialogs.showErrorDialog("There is an error in the file containig the finished orders." +
                            "The order-register might not be complete.");
                }
            }
        }


    public static void fileSaver(Stage stage){
        File selectedFile = getFile(DialogMode.Save, stage);

        if (selectedFile != null) {
            String fileExt = getFileExt(selectedFile);
            Writer writeCar = null;
            Writer writeComponent = null;
            Writer writeAdjustment = null;
            Writer writeOrder = null;

            switch (fileExt) {
                case ".txt" :
                   writeOrder = new fileWriterTxt();

                break;
                case ".jobj" :
                    writeCar = new fileWriterJobj();
                    writeComponent = new javaCode.ReaderWriter.Component.fileWriterJobj();
                    writeAdjustment = new javaCode.ReaderWriter.Adjustment.fileWriterJobj();

                    break;
                default : Dialogs.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");
            }

            if(fileExt.equals(".txt")){
                if(writeOrder != null) {
                    try {
                        writeOrder.save(selectedFile.toPath());
                        Dialogs.showSuccessDialog("Registeret ble lagret!");
                    } catch (IOException e) {
                        Dialogs.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
                    }
                }
            }
            else if(fileExt.equals(".jobj")){
                try{
                    writeCar.save(selectedFile.toPath());
                }catch(IOException e){

                }
            }

        }
    }
    public static void openSelectedFile(Stage stage, String type) {
        File selectedFile = getFile(DialogMode.Open, stage);
        try{
            if (selectedFile != null) {
                String fileExt = getFileExt(selectedFile);
                Reader reader = null;
                if(type == "Car"){
                    switch (fileExt) {
                        case ".txt" : reader = new fileReaderTxt(); break;
                        case ".jobj" : reader = new fileReaderJobj(); break;
                        default : Dialogs.showErrorDialog("You can only open txt or jobj files");
                    }
                }
                else if(type =="Component"){
                    switch (fileExt) {
                        case ".txt" : reader = new javaCode.ReaderWriter.Component.fileReaderTxt(); break;
                        case ".jobj" : reader = new javaCode.ReaderWriter.Component.fileReaderJobj(); break;
                        default : Dialogs.showErrorDialog("You can only open txt or jobj files");
                    }
                }
                else if(type == "Adjustment"){
                    switch (fileExt) {
                        case ".txt" : reader = new javaCode.ReaderWriter.Adjustment.fileReaderTxt(); break;
                        case ".jobj" : reader = new javaCode.ReaderWriter.Adjustment.fileReaderJobj(); break;
                        default : Dialogs.showErrorDialog("You can only open txt or jobj files");
                    }
                }

                if(reader != null) {
                    try {
                        reader.read(selectedFile.toPath());
                    } catch (IOException e) {
                        Dialogs.showErrorDialog("This file could not open because of: " + e.getMessage());
                    }
                }
                else{
                    Dialogs.showErrorDialog("This file could not be opened");
                }
            }
        }catch (Exception e){
            Dialogs.showErrorDialog("This file could not get loaded");
        }

    }
    static File getFile(DialogMode mode, Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");

        fileChooser.setInitialDirectory(new File("src/dataBase"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Serializable files", "*.jobj"));

        if(mode == DialogMode.Open) {
            return fileChooser.showOpenDialog(stage);
        }
        else {
            return fileChooser.showSaveDialog(stage);
        }
    }

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));

    }

}
