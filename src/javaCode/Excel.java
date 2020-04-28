package javaCode;


import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.collections4.ListValuedMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;

public class Excel {
    public static <E> void writeExcel(ObservableList<E> list) throws FileNotFoundException {
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


        if (new File(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks.xlsx").exists()){
            int version = 1;
            while (new File(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks"+ "(" + version + ")" +".xlsx").exists()){
                version++;
            }
            try (FileOutputStream outputStream = new FileOutputStream(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks"+ "(" + version + ")" +".xlsx")) {
                workbook.write(outputStream);
            } catch (IOException e) {
                Dialogs.showErrorDialog("Couldnt save file");
            }
        } else{
            try (FileOutputStream outputStream = new FileOutputStream(FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks.xlsx")) {
                workbook.write(outputStream);
            } catch (IOException e) {
                Dialogs.showErrorDialog("Couldnt save file");
            }
        }


    }
}