package javaCode.objects;

import javaCode.Exception.UserAlreadyExistException;
import javaCode.Validator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.FileNotFoundException;

//Class for object user
public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleBooleanProperty superUser;

    //Validating input when creating new object
    public User(int id, String firstName, String lastName, String email, String phone, String password, boolean superUser) throws FileNotFoundException, UserAlreadyExistException {
        if (!Validator.name(firstName)) {
            throw new IllegalArgumentException("Invalid firstname");
        }
        if (!Validator.name(lastName)) {
            throw new IllegalArgumentException("Invalid lastname");
        }
        if (!Validator.phone(phone)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!Validator.email(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Id cant be 0 or less");
        }
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.superUser = new SimpleBooleanProperty(superUser);
    }

    public int getId() {
        return id.getValue();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.getValue();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.getValue();
    }

    public Boolean getSuperUser() {
        return superUser.getValue();
    }

    public void setSuperUser(boolean superUser) {
        this.superUser.set(superUser);
    }

    @Override
    public String toString() {
        String DELIMITER = ";";
        return String.format("%s" + DELIMITER + "%s" + DELIMITER + "%s" + DELIMITER + "%s" + DELIMITER + "%s" + DELIMITER + "%s" + DELIMITER + "%s",
                id.getValue(), firstName.getValue(), lastName.getValue(), email.getValue(), phone.getValue(), password.getValue(), superUser.getValue());
    }
}
