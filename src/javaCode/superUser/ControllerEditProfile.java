package javaCode.superUser;

import javaCode.ConverterErrorHandler;
import javaCode.Dialogs;
import javaCode.InLog.ReadUsers;
import javaCode.OpenScene;
import javaCode.objects.User;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ControllerEditProfile {

    @FXML
    private TableView<User> tvUserRegister;

    @FXML
    private TableColumn<TableView<User>, Integer> id;

    @FXML
    private TableColumn<TableView<User>, Boolean> superUser;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClick;

    @FXML
    private TextField txtSearch;

    //Initializes conversion in table cells and tableviews
    public void initialize() throws FileNotFoundException {
        id.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.IntegerStringConverter()));
        superUser.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.BooleanStringConverter()));
        tvUserRegister.setItems(ReadUsers.getUserList());
        tvUserRegister.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        newThread delayThread = new newThread();
        delayThread.setOnSucceeded(this::threadDone);
        delayThread.setOnFailed(this::threadFailed);
        Thread th = new Thread(delayThread);
        th.setDaemon(true);
        tvUserRegister.setDisable(true);
        btnDelete.setDisable(true);
        txtSearch.setDisable(true);
        btnClick.setDisable(true);
        th.start();
    }


    private void threadFailed(WorkerStateEvent workerStateEvent) {
        Exception e = (Exception) workerStateEvent.getSource().getException();
        Dialogs.showErrorDialog(e.getMessage());
    }

    private void threadDone(WorkerStateEvent workerStateEvent) {
        tvUserRegister.setDisable(false);
        btnDelete.setDisable(false);
        txtSearch.setDisable(false);
        btnClick.setDisable(false);
    }

    //Method for searching in the tableview
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
                    }else return user.getPassword().toLowerCase().contains(lowerCase);
                });

                SortedList<User> sorted = new SortedList<>(filtered);
                sorted.comparatorProperty().bind(tvUserRegister.comparatorProperty());
                tvUserRegister.setItems(sorted);
            });
    }

    //Initializes and validates the edited text in the "Firstname-field"
    public void firstNameEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) {
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

    //Initializes and validates the edited text in the "Lastname-field"
    public void lastNameEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) {
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

    //Initializes and validates the edited text in the "Phone-field"
    public void phoneEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) {
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

    //Initializes and validates the edited text in the "Email-field"
    public void emailEdited(TableColumn.CellEditEvent<TableView<User>, String> cellEditEvent) {
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

    //Changes the superUser value to the opposite of the original one when clicked
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

    //Opens super user scene when "go back" button is clicked
    public void btnGoBackOnClick(ActionEvent actionEvent) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
        OpenScene.newScene("Superuser", root, 470, 300, actionEvent);
    }

    public void btnDeleteOnClick(ActionEvent actionEvent) {
        User u = tvUserRegister.getSelectionModel().getSelectedItem();
        if (Dialogs.showChooseDialog("Are you sure you want to delete user nr: " + u.getId() + "?")) {
            try {
                ReadUsers.changeInfo(String.valueOf(u.getId()), "Delete", null);
                tvUserRegister.getItems().remove(u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

