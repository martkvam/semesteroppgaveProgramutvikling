package javaCode;

import javaCode.ReaderWriter.Car.fileReaderJobj;
import javaCode.ReaderWriter.Car.fileReaderTxt;
import javaCode.ReaderWriter.Car.fileWriterJobj;
import javaCode.ReaderWriter.Car.fileWriterTxt;
import javaCode.ReaderWriter.Reader;
import javaCode.ReaderWriter.Writer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;


public class FileHandler{

    private enum DialogMode {Open, Save}

    public static void saveAllFiles(){
        File selectedFileCar = new File("src/dataBase/SuperUser/Cars.jobj");
        File selectedFileComponent = new File("src/dataBase/SuperUser/Components.jobj");
        File selectedFileAdjustments = new File("src/dataBase/SuperUser/Adjustments.jobj");
        File selectedFileOrders = new File("src/dataBase/SuperUser/Orders.jobj");



        Writer writeCar = null;
        Writer writeComponent = null;
        Writer writeAdjustment = null;
        //Writer writeOrders = null;

        writeCar = new fileWriterJobj();
        writeComponent = new javaCode.ReaderWriter.Component.fileWriterJobj();
        writeAdjustment = new javaCode.ReaderWriter.Adjustment.fileWriterJobj();
        //writeOrders = new javaCode.ReaderWriter.Order.fileWriterJobj();


        if(writeCar != null) {
            try {
                writeCar.save(selectedFileCar.toPath());
                writeComponent.save(selectedFileComponent.toPath());
                writeAdjustment.save(selectedFileAdjustments.toPath());
                //writeOrders.save(selectedFileOrders.toPath());
                Dialogs.showSuccessDialog("Registeret ble lagret!");
            } catch (IOException e) {
                Dialogs.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }
    }
    public static void readAllFiles(Stage stage) {
        File selectedFileCar = new File("src/dataBase/SuperUser/Cars.jobj");
        File selectedFileComponent = new File("src/dataBase/SuperUser/Components.jobj");
        File selectedFileAdjustments = new File("src/dataBase/SuperUser/Adjustments.jobj");
        File selectedFileOrders = new File("src/dataBase/SuperUser/Orders.jobj");

        Reader readerCar = null;
        Reader readerComponents = null;
        Reader readerAdjustments=null;
        Reader readerOrders = null;

        readerCar = new fileReaderJobj();
        readerComponents=new javaCode.ReaderWriter.Component.fileReaderJobj();
        readerAdjustments = new javaCode.ReaderWriter.Adjustment.fileReaderJobj();
        readerOrders = new javaCode.ReaderWriter.Order.fileReaderJobj();


            if(readerCar != null) {
                try {
                    readerCar.read(selectedFileCar.toPath());
                    readerComponents.read(selectedFileComponent.toPath());
                    readerAdjustments.read(selectedFileAdjustments.toPath());
                    //readerOrders.read(selectedFileOrders.toPath());
                    Dialogs.showSuccessDialog("Registeret ble lest inn!");
                } catch (IOException e) {
                    Dialogs.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
                }
            }
        }


    public static void fileSaver(Stage stage){
        File selectedFile = getFile(DialogMode.Save, stage);

        if (selectedFile != null) {
            String fileExt = getFileExt(selectedFile);
            Writer writeCar = null;
           // Writer writeComponent = null;
            //Writer writeAdjustment = null;

            switch (fileExt) {
                case ".txt" :
                    writeCar = new fileWriterTxt();
                    /*writeComponent = new fileWriterTxt();
                    writeAdjustment = new fileWriterTxt();

                     */
                break;
                case ".jobj" :
                    writeCar = new fileWriterJobj();
                    /*writeComponent = new javaCode.ReaderWriter.Component.fileWriterJobj();
                    writeAdjustment = new javaCode.ReaderWriter.Adjustment.fileWriterJobj();

                     */
                    break;
                default : Dialogs.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");
            }

            if(writeCar != null) {
                try {
                    writeCar.save(selectedFile.toPath());
                    Dialogs.showSuccessDialog("Registeret ble lagret!");
                } catch (IOException e) {
                    Dialogs.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
                }
            }
        }
    }
    public static void openSelectedFile(Stage stage, String type) {
        File selectedFile = getFile(DialogMode.Open, stage);

        if (selectedFile != null) {
            String fileExt = getFileExt(selectedFile);
            Reader reader = null;
            System.out.println(type);
            if(type == "Car"){
                switch (fileExt) {
                    case ".txt" : reader = new fileReaderTxt(); break;
                    case ".jobj" : reader = new fileReaderJobj(); break;
                    default : Dialogs.showErrorDialog("Du kan bare åpne txt eller jobj filer.");
                }
            }
            else if(type =="Component"){
                System.out.println("Yes");
                switch (fileExt) {
                    case ".txt" : reader = new javaCode.ReaderWriter.Component.fileReaderTxt(); break;
                    case ".jobj" : reader = new javaCode.ReaderWriter.Component.fileReaderJobj(); break;
                    default : Dialogs.showErrorDialog("Du kan bare åpne txt eller jobj filer.");
                }
            }
            else if(type == "Adjustment"){
                switch (fileExt) {
                    case ".txt" : reader = new javaCode.ReaderWriter.Adjustment.fileReaderTxt(); break;
                    case ".jobj" : reader = new javaCode.ReaderWriter.Adjustment.fileReaderJobj(); break;
                    default : Dialogs.showErrorDialog("Du kan bare åpne txt eller jobj filer.");
                }
            }

            if(reader != null) {
                try {
                    reader.read(selectedFile.toPath());
                } catch (IOException e) {
                    Dialogs.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
                }
            }
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
