package javaCode.InLog;

import javaCode.ConverterErrorHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Formatter {

    //Paths of files for user register and next available id
    public static String registerPath = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Users.txt");

    private static String nextIdPath = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/NextUserNr.txt");

    //Write user object to file
    public static void addToFile(User user) throws IOException {
        Files.write(Paths.get(registerPath), (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    //Read through database of users and check if user is already registered
    public static int assignID() throws IOException {
        ConverterErrorHandler.IntegerStringConverter intStrConv = new ConverterErrorHandler.IntegerStringConverter();
        File ids = new File(Formatter.nextIdPath);
        Scanner scanIds = new Scanner(ids);
        int newId = intStrConv.fromString(scanIds.next());
        scanIds.close();

        File myObj = new File(Formatter.registerPath);
        Scanner myReader = new Scanner(myObj);

        for (; myReader.hasNext(); ) {
            String u = myReader.nextLine();
            String[] strings = u.split(";");

            if (Integer.parseInt(strings[0]) == newId ){
                newId++;
            }
        }
        myReader.close();

        Files.write(Paths.get(Formatter.nextIdPath), String.valueOf(newId+1).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        return newId;
    }
}
