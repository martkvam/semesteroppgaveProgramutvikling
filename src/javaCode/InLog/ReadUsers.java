package javaCode.InLog;

import javaCode.Dialogs;
import java.io.*;
import java.util.*;

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

    public static String getInfo(String id, String type) throws FileNotFoundException {
        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);

        for (; myReader.hasNext(); ) {
            String u = myReader.next();
            String[] strings = u.split(";");

            if(strings[0].equals(id)) {
                switch (type) {
                    case "FirstName":
                        return strings[1];
                    case "LastName":
                        return strings[2];
                    case "Email":
                        return strings[3];
                    case "Phone":
                        return strings[4];
                    case "Password":
                        return strings[5];
                    case "SuperUser":
                        return strings[6];
                    case "User":
                        return Arrays.toString(strings);
                }
            }
        }
        Dialogs.showErrorDialog("User not found");
        return null;
    }

    public static void changeInfo(String id, String type, String change) throws IOException {
        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);
        StringBuilder newRegister = new StringBuilder();

        for (; myReader.hasNext(); ) {
            String line = myReader.next();
            String[] strings = line.split(";");

            if(strings[0].equals(id)) {
                switch (type) {
                    case "FirstName":
                         line = line.replace(strings[1], Validator.name(change));
                         break;
                    case "LastName":
                        line = line.replace(strings[2], Validator.name(change));
                        break;
                    case "Email":
                        line = line.replace(strings[3], Validator.email(change));
                        break;
                    case "Phone":
                        line = line.replace(strings[4], Validator.phone(change));
                        break;
                    case "Password":
                        line = line.replace(strings[5], change);
                        break;
                    case "SuperUser":
                        line = line.replace(strings[6], change);
                        break;
                }
            }
            newRegister.append(line).append(System.lineSeparator());
        }

        FileWriter myWriter = new FileWriter(myObj);
        myWriter.write(String.valueOf(newRegister));
        myReader.close();
        myWriter.close();
    }

    public static  ArrayList<String> getUserId(String ... str) throws FileNotFoundException {
        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);
        ArrayList<String> userId = new ArrayList<>();

        for (; myReader.hasNext(); ) {
            String u = myReader.next();
            String[] strings = u.split(";");

            for (String inStr: str){
                for(String usrStr : strings){
                    if (inStr.equals(usrStr)){
                        userId.add(strings[0]);
                    }
                }
            }
        }

        if(userId.size()==0){
            return null;
        } else if (userId.size()==1){
            return userId;
        }else {
            return findId(userId);
        }
    }

    private static ArrayList<String> findId(ArrayList <String> foundUsers){
        Map<String, Integer> idAndCount = new HashMap<>();
        int count = 0;

        for (int i = 0; i < foundUsers.size(); i++) {
            idAndCount.putIfAbsent(foundUsers.get(i), 0);
            for (int j = 0 ; j < foundUsers.size(); j++) {
                if (foundUsers.get(i).equals(foundUsers.get(j))) {
                    idAndCount.put(foundUsers.get(i), idAndCount.get(foundUsers.get(i))+1);
                }
            }
        }


        ArrayList<String> foundIds = new ArrayList<>();
        int idCount = 0;

        for (Map.Entry<String, Integer> entry : idAndCount.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value > idCount){
                idCount=value;
                foundIds.clear();
                foundIds.add(key);
            } else if (value == idCount){
                foundIds.add(key);
            }
        }
        return foundIds;
    }
}