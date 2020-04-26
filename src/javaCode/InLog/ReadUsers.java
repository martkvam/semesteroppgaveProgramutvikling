package javaCode.InLog;

import javaCode.ConverterErrorHandler;
import javaCode.Dialogs;
import javaCode.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

public class ReadUsers {

    public static String getInfo(String id, String type) throws FileNotFoundException {
        File myObj = new File(Formatter.path);
        try (Scanner myReader = new Scanner(myObj)) {
            for (; myReader.hasNext(); ) {
                String u = myReader.next();
                String[] strings = u.split(";");

                if (strings[0].equals(id)) {
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
                         line = line.replace(strings[1], Objects.requireNonNull(Validator.name(change)));
                         break;
                    case "LastName":
                        line = line.replace(strings[2], Objects.requireNonNull(Validator.name(change)));
                        break;
                    case "Email":
                        line = line.replace(strings[3], Objects.requireNonNull(Validator.email(change)));
                        break;
                    case "Phone":
                        line = line.replace(strings[4], Objects.requireNonNull(Validator.phone(change)));
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

    public static ArrayList<String> getUserId(String ... str) throws FileNotFoundException {
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
        myReader.close();

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

        for (int i = 0; i < foundUsers.size(); i++) {
            idAndCount.putIfAbsent(foundUsers.get(i), 0);
            for (String foundUser : foundUsers) {
                if (foundUsers.get(i).equals(foundUser)) {
                    idAndCount.put(foundUsers.get(i), idAndCount.get(foundUsers.get(i)) + 1);
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

    public static ObservableList<User> getUserList() throws FileNotFoundException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        File myObj = new File(Formatter.path);
        Scanner myReader = new Scanner(myObj);
        ConverterErrorHandler.IntegerStringConverter intStrConv = new ConverterErrorHandler.IntegerStringConverter();

        for (; myReader.hasNext(); ) {
            String line = myReader.next();
            String[] user = line.split(";");
            userList.add(new User(intStrConv.fromString(user[0]), user[1], user[2], user[4], user[3], user[5], Boolean.parseBoolean(user[6])));
        }
        return userList;
    }
}