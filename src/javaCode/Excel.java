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

    public static <E> void writeExcel(ObservableList<E> list, String type) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Register");
        String filePath = new FileChooser().showSaveDialog(Main.getPrimaryStage()).getAbsolutePath();
        String fileEnding = ".xlsx";

       /* String[] header=null;
        switch (type){
            case "User":
                header = new String[]{"Id", "Firstname", "Lastname", "Email", "Phone", "Password", "Superuser"};
                //String[] header = new String[]{"Id", "Firstname", "Lastname", "Email", "Phone", "Password", "Superuser"};
                break;
            case "Order":
                break;
        }*/

        for(int rowCount = 0; rowCount < list.size(); rowCount++){
            String[] header = new String[]{"Id", "Firstname", "Lastname", "Email", "Phone", "Password", "Superuser"};

            Row row = sheet.createRow(rowCount);
            String [] user = list.get(rowCount).toString().split(";");
            user[5] = "Unavailable";


            for(int columnCount = 0; columnCount<user.length; columnCount++) {
                Cell headers = row.createCell(columnCount);
                if (rowCount==0){
                    assert header != null;
                    headers.setCellValue(header[columnCount]);
                } else {
                    headers.setCellValue(user[columnCount]);
                }
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
        XSSFSheet firstSheet = workbook.getSheetAt(1);

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
                //Do something with NullPointerException
            }
        }
        workbook.close();
        inputStream.close();
    }
}