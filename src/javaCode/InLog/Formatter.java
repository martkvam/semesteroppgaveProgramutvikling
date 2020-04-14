package javaCode.InLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class Formatter {

    public static String path = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Users.txt");

    public static void addToFile(User user) throws IOException {
        Files.write(Paths.get(path), (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    public static int assignID() throws FileNotFoundException {
        //Read through database of users and check if user is already registered

        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);
        int id = 1;

        for (; myReader.hasNext(); ) {
            String u = myReader.nextLine();
            String[] strings = u.split(";");

            if (Integer.parseInt(strings[0])>=id){
                id = Integer.parseInt(strings[0])+1;
            }
        }
        myReader.close();
        return id;
    }
}
