package javaCode.ReaderWriter.Order;

import javaCode.Dialogs;
import javaCode.Main;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileWriterExcel {

    //With version controll so files dont get overwritten
    private static String filePathVersion (String filepath, int version, String fileEnding){
        return filepath + "(" + version + ")" + fileEnding;
    }

    //String for header in excel file
    private static String[] header(){
        return new String[]{"Order nr.", "Person nr.", "Car id", "Order Started", "Order finished",
                        "Component list", "Adjustment list", "Total price", "Car color", "Order status"};
    }

    //Method for writing excel file
    public static <E> void writeExcel(ObservableList<E> list) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Register");
        String filePath = new FileChooser().showSaveDialog(Main.getPrimaryStage()).getAbsolutePath();
        String fileEnding = ".xlsx";

        for(int rowCount = 0; rowCount < list.size(); rowCount++){
            String[] header = header();
            if (rowCount==0){
                Row headerRow = sheet.createRow(rowCount);
                for(int headerCount = 0; headerCount<header.length; headerCount++) {
                    Cell headers = headerRow.createCell(headerCount);
                    headers.setCellValue(header[headerCount]);
                }
            }

            Row row = sheet.createRow(rowCount+1);
            String [] user = list.get(rowCount).toString().split(";");

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
}