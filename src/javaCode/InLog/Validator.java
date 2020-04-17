package javaCode.InLog;

import javaCode.Dialogs;

import java.util.IllegalFormatException;
//If new user is incorrect, e.g. name is numbers the Exception will be thrown but the user still registers.
//Need to stop the user from registering and "try again"
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
            return null;
        }
    }

    public static String phone(String phone){
        try{
            if (!phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")){
                throw new IllegalArgumentException("Invalid phone number");
            } else{
                return phone;
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
            return null;
        }
    }

    public static String email(String email){
        try{
            if(!email.matches(".[\\S]+@.[\\S]+[.].[\\S]+")){
                throw new IllegalArgumentException("Invalid email");
            } else {
                return email;
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
            return null;
        }
    }
}
