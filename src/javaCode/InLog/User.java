package javaCode.InLog;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public User(String firstName, String lastName, String phone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }


    @Override
    public String toString(){
        String DELIMITER = ";";
        return String.format("%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s",
                firstName, lastName, phone, email);
    }
}
