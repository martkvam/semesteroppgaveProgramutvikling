package javaCode.superUser;

import javaCode.ConverterErrorHandler;
import javaCode.Dialogs;
import javaCode.Excel;
import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javaCode.OpenScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public void initialize() throws FileNotFoundException, UserAlreadyExistException {
        id.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.IntegerStringConverter()));
        superUser.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        tvUserRegister.setItems(ReadUsers.getUserList());
        tvUserRegister.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void searchFilter(KeyEvent keyEvent) throws FileNotFoundException, UserAlreadyExistException {
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
            Dialogs.showErrorDialog(e.getMessage());
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
            Dialogs.showErrorDialog(e.getMessage());
        }
        tvUserRegister.refresh();
    }

    public void phoneEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Lastname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newPhone = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "Phone", newPhone);
            u.setPhone(newPhone);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
            Dialogs.showErrorDialog(e.getMessage());
        }
        tvUserRegister.refresh();
    }

    public void emailEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) throws IOException { //Initializes and validates the edited text in the "Lastname-field"
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        String newEmail = cellEditEvent.getNewValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "Email", newEmail);
            u.setEmail(newEmail);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
            Dialogs.showErrorDialog(e.getMessage());
        }
        tvUserRegister.refresh();
    }

    public void superUserEdited(TableColumn.CellEditEvent<TableView<User>, Boolean> tableViewBooleanCellEditEvent) {
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        boolean newSuperUser = !tableViewBooleanCellEditEvent.getOldValue();
        try {
            ReadUsers.changeInfo(String.valueOf(u.getId()), "SuperUser", String.valueOf(newSuperUser));
            u.setSuperUser(newSuperUser);
        } catch (Exception e){
            tvUserRegister.getSelectionModel().clearSelection();
            Dialogs.showErrorDialog(e.getMessage());
        }
        tvUserRegister.refresh();
    }

    public void btnGoBackOnClick(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.newScene("Superuser",  root, 470, 300, actionEvent);
    }

    public void btnExportToExcelOnClick(ActionEvent actionEvent) throws IOException {
        Excel.writeExcel(ReadUsers.getUserList());
    }

    public void btnImportFromExcelOnClick(ActionEvent actionEvent) throws IOException {
        Excel.readExcel("User");
        initialize();
        tvUserRegister.refresh();
    }
}

