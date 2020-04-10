package javaCode.InLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReadUsers {

public static boolean readFile(String email, String phone) throws FileNotFoundException, IOException {
    File myObj = new File(Formatter.path);
    Scanner myReader = new Scanner(myObj);

    for (; myReader.hasNext(); ) {
        String u = myReader.next();
        String[] strings = u.split(";");
        if(strings[3].equals(email) || strings[4].equals(phone)){
            return true;
        }
    }

    myReader.close();
    return false;
    }

    public static String read(String type, String value) throws FileNotFoundException {

        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);


        for (; myReader.hasNext(); ) {
            String u = myReader.next();
            String[] strings = u.split(";");

            switch(value) {
                case "Id":
                    // code block
                    break;
                case "FirstName":
                    // code block
                    break;
                case "LastName":
                    // code block
                    break;
                case "Phone":
                    // code block
                    break;
                case "Email":
                    // code block
                    break;

                default:
                    // code block
            }
        }
        myReader.close();
        return null;
    }
}
