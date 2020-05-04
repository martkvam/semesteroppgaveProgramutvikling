package javaCode;

import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.Formatter;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
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

    //Variables for paths to save Excel file
    private static String filePath (){
        return FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/UserRegister.xlsx";
    }

    //With version controll so files dont get overwritten
    private static String filePathVersion (int version){
        return FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/UserRegister"+ "(" + version + ")" +".xlsx";
    }


    public static <E> void writeExcel(ObservableList<E> list) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Register");

        for(int rowCount = 0; rowCount < list.size(); rowCount++){
            Row row = sheet.createRow(rowCount);
            String [] user = list.get(rowCount).toString().split(";");

            for(int columnCount = 0; columnCount<user.length; columnCount++) {
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(user[columnCount]);
            }
        }

        if (new File(filePath()).exists()){
            int version = 1;
            while (new File(filePathVersion(version)).exists()){
                version++;
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePathVersion(version))) {
                workbook.write(outputStream);
                Desktop.getDesktop().open(new File(filePathVersion(version)));
            } catch (IOException e) {
                Dialogs.showErrorDialog("Couldnt save file");
            }
        } else{
            try (FileOutputStream outputStream = new FileOutputStream(filePath())) {
                workbook.write(outputStream);
                Desktop.getDesktop().open(new File(filePath()));
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

            switch (type) {
                case "Finished":
                    list = new String[10];
                    break;
                case "Ongoing":
                    list = new String[10];
                    break;
                case "User":
                    list = new String[7];
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
                //Do something with NullPointerException
            }
        }
        workbook.close();
        inputStream.close();
    }
}