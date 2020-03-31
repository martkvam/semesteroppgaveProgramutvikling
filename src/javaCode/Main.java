package javaCode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/sample.fxml"));
        primaryStage.setTitle("Kjøp av bil");
        primaryStage.setScene(new Scene(root,900,800));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);


    }

    public static void superUser(Stage superUser1) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource("../resources/superUser.fxml"));
        superUser1.setTitle("Funker");
        superUser1.setScene(new Scene(root,483 ,296));
        superUser1.show();
    }

    public static void superUserComponents(Stage superUser2) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource("../resources/superUserComponents.fxml"));
        superUser2.setTitle("Funker");
        superUser2.setScene(new Scene(root,704  ,548));
        superUser2.show();
    }


}
