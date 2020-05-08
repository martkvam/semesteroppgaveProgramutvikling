package javaCode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Inlog.fxml"));
        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root,600,450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}