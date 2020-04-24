package javaCode.superUser;

import javaCode.ConverterErrorHandler;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BooleanStringConverter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ControllerEditProfile {

    private static ObservableList<User> userRegisterList = FXCollections.observableArrayList();

    @FXML
    private TableView<User> tvUserRegister;

    @FXML
    private TableColumn<TableView<User>, Integer> id;

    @FXML
    private TableColumn<TableView<User>, String> firstName;

    @FXML
    private TableColumn<TableView<User>, String> lastName;

    @FXML
    private TableColumn<TableView<User>, String> phone;

    @FXML
    private TableColumn<TableView<User>, String> email;

    @FXML
    private TableColumn<TableView<User>, Boolean> superUser;

    @FXML
    private Button btnClick;

    @FXML
    private TextField txtSearch;

    public void initialize(){
        id.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.IntegerStringConverter()));
        superUser.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        try {
            tvUserRegister.setItems(ReadUsers.getUserList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tvUserRegister.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void btnClickOnClick(javafx.event.ActionEvent actionEvent) throws FileNotFoundException {
        //User test = new User(66, "Test", "Testen", "99999999", "test.testen@test.no", "lolol", false);
        //userRegisterList.add(test);
        //tvUserRegister.getItems().add();
        //tvUserRegister.setItems(userRegisterList);
        //tvUserRegister.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        /*userRegisterList.setAll(ReadUsers.getUserList());
        id.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.IntegerStringConverter()));
        superUser.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        tvUserRegister.setItems(userRegisterList);
    */}

    public void searchFilter(KeyEvent keyEvent) throws FileNotFoundException {
            FilteredList<User> filtered = new FilteredList<>(ReadUsers.getUserList(), b -> true);

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filtered.setPredicate(user -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }

                    String lowerCase = newValue.toLowerCase();

                    if(String.valueOf(user.getId()).toLowerCase().equals(lowerCase)){
                        return true;
                    } else if (user.getFirstName().toLowerCase().contains(lowerCase)){
                        return true;
                    } else if (user.getLastName().toLowerCase().contains(lowerCase)){
                        return true;
                    } else if (user.getPhone().toLowerCase().contains(lowerCase)) {
                        return true;
                    } else if (user.getEmail().toLowerCase().contains(lowerCase)){
                        return true;
                    }else if (user.getPassword().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    return false;
                });

                SortedList<User> sorted = new SortedList<>(filtered);
                sorted.comparatorProperty().bind(tvUserRegister.comparatorProperty());
                tvUserRegister.setItems(sorted);
            });
    }

    public void firstNameEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Firstname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newFirstName = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "FirstName", newFirstName);
            u.setFirstName(newFirstName);
        } catch (Exception e){
           tvUserRegister.getSelectionModel().clearSelection();
        }
        tvUserRegister.refresh();
    }

    public void lastNameEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Lastname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newLastName = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "LastName", newLastName);
            u.setLastName(newLastName);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
        }
        tvUserRegister.refresh();
    }

    public void phoneEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Lastname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newPhone = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "Phone", newPhone);
            u.setLastName(newPhone);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
        }
        tvUserRegister.refresh();
    }

    public void emailEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Lastname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newEmail = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "Email", newEmail);
            u.setLastName(newEmail);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
        }
        tvUserRegister.refresh();
    }

    public void superUserEdited(TableColumn.CellEditEvent<TableView<User>, Boolean> tableViewBooleanCellEditEvent) {
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        boolean newSuperUser = !tableViewBooleanCellEditEvent.getOldValue();//tableViewBooleanCellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "SuperUser", String.valueOf(newSuperUser));
            u.setSuperUser(newSuperUser);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
        }
        tvUserRegister.refresh();
    }
}

