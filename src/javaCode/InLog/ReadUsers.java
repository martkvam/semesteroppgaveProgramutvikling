package javaCode.InLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ReadUsers {

public static void readFile() throws FileNotFoundException {
    File myObj = new File(Formatter.path);
    Scanner myReader = new Scanner(myObj);

    for (; myReader.hasNext(); ) {
        String u = myReader.next();
        String[] strings = u.split(";");
        System.out.println(Arrays.toString(strings));
    }

    myReader.close();
    }
}
