package javaCode;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialogs {
    public static void showErrorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText("Invalid data");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static void showSuccessDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Operation succeeded");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static boolean showChooseDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
