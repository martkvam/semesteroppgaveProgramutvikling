package javaCode.InLog;

import javaCode.objects.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Formatter {

    //Paths of files for user register
    public static String registerPath = (FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/dataBase/Users.txt");

    //Write user object to file
    public static void addToFile(User user) throws IOException {
        Files.write(Paths.get(registerPath), (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    //Read through database of users and check if user is already registered
    public static int assignID() throws IOException {
        int highest = 0;
        ObservableList<User> list = ReadUsers.getUserList();
        for (User user : list) {
            if (user.getId() > highest) {
                highest = user.getId();
            }
        }
        return highest + 1;
    }
}
