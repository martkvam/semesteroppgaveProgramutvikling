package javaCode;

import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.Formatter;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Iterator;

public class Excel {

    //With version controll so files dont get overwritten
    private static String filePathVersion (String filepath, int version, String fileEnding){
        return filepath + "(" + version + ")" + fileEnding;
    }

    private static String[] header (String type){
        switch (type){
            case "User":
                return new String[]{"Id", "Firstname", "Lastname", "Email", "Phone", "Password", "Superuser"};
            case "Order":
                return new String[]{"Order nr.", "Person nr.", "Car id", "Order Started", "Order finished",
                        "Component list", "Adjustment list", "Total price", "Car color", "Order status"};
            default:
                return null;
        }
    }

    public static <E> void writeExcel(ObservableList<E> list, String type) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Register");
        String filePath = new FileChooser().showSaveDialog(Main.getPrimaryStage()).getAbsolutePath();
        String fileEnding = ".xlsx";

        for(int rowCount = 0; rowCount < list.size(); rowCount++){
            String[] header = header(type);
            if (rowCount==0){
                Row headerRow = sheet.createRow(rowCount);
                for(int headerCount = 0; headerCount<header.length; headerCount++) {
                    Cell headers = headerRow.createCell(headerCount);
                    //assert header != null;
                    headers.setCellValue(header[headerCount]);
                }
            }

            Row row = sheet.createRow(rowCount+1);
            String [] user = list.get(rowCount).toString().split(";");

            switch (type) {
                case("User"):
                    user[5] = "Unavailable";
                    //header = header(type);
                case("Order"):
                    break;
            }

            for(int columnCount = 0; columnCount<user.length; columnCount++) {
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(user[columnCount]);
            }
        }

        if (new File(filePath + fileEnding).exists()){
            int version = 1;
            while (new File(filePathVersion(filePath, version, fileEnding)).exists()){
                version++;
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePathVersion(filePath, version, fileEnding))) {
                workbook.write(outputStream);
                Desktop.getDesktop().open(new File(filePathVersion(filePath, version, fileEnding)));
            } catch (IOException e) {
                Dialogs.showErrorDialog("Couldnt save file");
            }
        } else{
            try (FileOutputStream outputStream = new FileOutputStream(filePath + fileEnding)) {
                workbook.write(outputStream);
                Desktop.getDesktop().open(new File(filePath + fileEnding));
            } catch (IOException e) {
                Dialogs.showErrorDialog("Couldnt save file");
            }
        }
        workbook.close();
    }

    public static void readExcel(String type) throws IOException {

        FileChooser openFile = new FileChooser();
        File chosenFile = openFile.showOpenDialog(null);
        ConverterErrorHandler.IntegerStringConverter intStrConv = new ConverterErrorHandler.IntegerStringConverter();

        FileInputStream inputStream = new FileInputStream(chosenFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);

        String[] list = new String[0];
        DataFormatter formatter = new DataFormatter();

        for (Row nextRow : firstSheet) {
            System.out.println(nextRow);
            switch (type) {
                case "Finished":
                    list = new String[10];
                    break;
                case "Ongoing":
                    list = new String[10];
                    break;
                case "User":
                    list = new String[7];
                    break;
            }

            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                list[cell.getColumnIndex()] = formatter.formatCellValue(cell);
            }
            try{
                switch (type) {
                    case "Finished":
                        break;
                    case "Ongoing":
                        break;
                    case "User":
                        ReadUsers.checkIfUserExists(list[3], list[4]);
                        Formatter.addToFile(new User(intStrConv.fromString(list[0]),
                                list[1], list[2], list[3], list[4],
                                list[5], Boolean.parseBoolean(list[6])));
                        break;
                }
            }catch (UserAlreadyExistException e){
                Dialogs.showErrorDialog(e.getMessage());
                break;
            }catch (NullPointerException e){
                Dialogs.showErrorDialog(e.getMessage());
                //Do something with NullPointerException
            }
        }
        workbook.close();
        inputStream.close();
    }
}