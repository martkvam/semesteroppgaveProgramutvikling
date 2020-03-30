package javaCode.InLog;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public User(int id, String firstName, String lastName, String phone, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString(){
        String DELIMITER = ";";
        return String.format("%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s",
                id, firstName, lastName, phone, email);
    }
}
