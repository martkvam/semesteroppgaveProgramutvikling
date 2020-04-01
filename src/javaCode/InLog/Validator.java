package javaCode.InLog;

public class Validator {
    public static String name(String name) {
        if (name.matches("([a-zA-ZæøåÆØÅ]+['\\-,. ]?)+")){
            return name;
        }else{
            throw new IllegalArgumentException("Invalid name");
        }
    }
    public static String phone(String phone){
        if (phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")){
            return phone;
        } else{
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
    public static String email(String email){
        if(email.matches(".[\\S]+@.[\\S]+[.].[\\S]+")){
            return email;
        }else{
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
