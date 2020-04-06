package javaCode;

import javaCode.superUser.ControllerSuperUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenScene {
    @FXML
    public static void newScene(String title, Parent root, int width, int height, ActionEvent event) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }
}
