package javaCode.InLog;

import javaCode.Dialogs;

import java.util.IllegalFormatException;

public class Validator {
    public static String name(String name) {
        try{
            if (!name.matches("([a-zA-ZæøåÆØÅ]+['\\-,. ]?)+")){
                throw new IllegalArgumentException("Invalid name");
            } else {
                return name;
            }
        } catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        return name;
    }

    public static String phone(String phone){
        try{
            if (!phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")){
                throw new IllegalArgumentException("Invalid phone number");
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
            return phone;
    }

    public static String email(String email){
        try{
            if(!email.matches(".[\\S]+@.[\\S]+[.].[\\S]+")){
                throw new IllegalArgumentException("Invalid email");
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
        }
        return email;
    }
}
