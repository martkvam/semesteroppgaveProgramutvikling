package javaCode;

import javaCode.superUser.ControllerSuperUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenScene {
    @FXML
    public static void newScene(String title, Parent root, int width, int height) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }
}
