package javaCode.InLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Formatter {

    public static void addToFile(String person) throws IOException {
        String path = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Users.txt");
        Files.write(Paths.get(path), person.getBytes());
    }

    private static int assignID() {
        //Read through database of users and check if user is already registered (check with email?)
        return 0;
    }

    private static String DELIMITER = ";";

    public static String person(String firstName, String lastName, String email, String phone) throws IOException {
        return (assignID() + DELIMITER + firstName + DELIMITER+ lastName + DELIMITER + email + DELIMITER + phone + DELIMITER);
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
