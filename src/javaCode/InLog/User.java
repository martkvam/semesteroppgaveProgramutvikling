package javaCode.InLog;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;

    public void setId(int id) {
        this.id.set(id);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setSuperUser(boolean superUser) {
        this.superUser.set(superUser);
    }

    private SimpleStringProperty lastName;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleBooleanProperty superUser;

    public int getId() {
        return id.getValue();
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public String getPhone() {
        return phone.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public Boolean getSuperUser() {
        return superUser.getValue();
    }

    public User(int id, String firstName, String lastName, String phone, String email, String password, boolean superUser){
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.superUser = new SimpleBooleanProperty(superUser);
    }

    @Override
    public String toString(){
        String DELIMITER = ";";
        return String.format("%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s"+DELIMITER+"%s",
                id.getValue(), firstName.getValue(), lastName.getValue(), phone.getValue(), email.getValue(), password.getValue(), superUser.getValue());
    }
}
