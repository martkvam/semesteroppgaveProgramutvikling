package javaCode.superUser;

import javaCode.ConverterErrorHandler;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BooleanStringConverter;

import java.io.FileNotFoundException;

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

    public void btnClickOnClick(javafx.event.ActionEvent actionEvent) throws FileNotFoundException {
        //User test = new User(66, "Test", "Testen", "99999999", "test.testen@test.no", "lolol", false);
        //userRegisterList.add(test);
        //tvUserRegister.getItems().add();
        //tvUserRegister.setItems(userRegisterList);
        //tvUserRegister.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id.setCellFactory(TextFieldTableCell.forTableColumn(new ConverterErrorHandler.IntegerStringConverter()));
        superUser.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        tvUserRegister.setItems(ReadUsers.getUserList());
    }

    public void searchFilter(KeyEvent keyEvent) {
            FilteredList<User> filtered = new FilteredList<>(userRegisterList, b -> true);

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
}

