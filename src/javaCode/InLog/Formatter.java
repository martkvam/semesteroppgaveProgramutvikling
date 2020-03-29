package javaCode.InLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Formatter {
    public static String path = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Users.txt");
    public static void addToFile(User user) throws IOException {

        Files.write(Paths.get(path), (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    private static int assignID() {
        //Read through database of users and check if user is already registered (check with email?)

        return 0;
    }

    /*public static String people(List<String> plist) {
        StringBuffer str = new StringBuffer();
        for (String p : plist) {
            str.append(p);
            str.append("\n");
        }
        return str.toString();
    }*/
}
