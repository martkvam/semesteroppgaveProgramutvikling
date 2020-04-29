package javaCode;


import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.Iterator;

public class Excel {

    //Variables for paths to save Excel file
    private static String filePath (){
        return FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks.xlsx";
    }

    //With version controll so files dont get overwritten
    private static String filePathVersion (int version){
        return FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Excel/JavaBooks"+ "(" + version + ")" +".xlsx";
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

    public static void readExcel() throws IOException {

        FileInputStream inputStream = new FileInputStream(new File(filePath()));

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);

        for (Row nextRow : firstSheet) {
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(";");
            }
            System.out.println();
        }
        workbook.close();
        inputStream.close();
    }
}