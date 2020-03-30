package javaCode.InLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReadUsers {

public static void readFile(String email, String phone) throws FileNotFoundException, IOException {
    File myObj = new File(Formatter.path);
    Scanner myReader = new Scanner(myObj);

    for (; myReader.hasNext(); ) {
        String u = myReader.next();
        String[] strings = u.split(";");
        if(strings[3].equals(email) || strings[4].equals(phone)){
            throw new IOException("Bruker finnes fra før");
        }
    }

    myReader.close();
    }
}
