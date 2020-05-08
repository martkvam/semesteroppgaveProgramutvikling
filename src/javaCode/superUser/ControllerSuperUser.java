package javaCode.superUser;

import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

//Scene where superuser choose between two operations.

public class ControllerSuperUser {

    @FXML
    private Button Components;

    @FXML
    private Label lblOut;

    //Navigates to scene where superuser are able to add and edit components and information
    @FXML
    void btnEditComponents(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        //Sets new controller
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerAddEditComponents");

        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserComponents.fxml"));
        OpenScene.
                newScene("Edit/delete components", root, 800,702, event);
    }

    //Navigates to scene where superuser are able to edit/delete orders.
    @FXML
    void btnEditOrders(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController("../superUser/ControllerOrders");

        // Swap screen
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserOrders.fxml"));
        OpenScene.
                newScene("Edit orders", root, 1100 ,730, event);
    }

    //Navigates to scene where superuser are able to edit/delete profiles.
    @FXML
    void btnEditProfiles(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUserProfile.fxml"));
        OpenScene.newScene("Edit profiles", root, 600, 350, event);
    }

    //Logging out superUser
    @FXML
    void btnGoBack(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Inlog.fxml"));
        OpenScene.newScene("Log in", root, 600,450, event);
    }

    //Info about components scene on hover
    @FXML
    void btnInfoComp(MouseEvent event) {

        lblOut.setText("The components scene are where you as a Superuser can add new cars, components and adjustments in addition to edit existing components.");
    }

    //Info about orders scene on hover
    @FXML
    void btnInfoOrders(MouseEvent event) {
        lblOut.setText("The Orders scene are where you as a Superuser can view, edit and delete existing orders.");
    }

    //Removes info on remove hover
    @FXML
    void btnRemoveInfoComp(MouseEvent event) {
        lblOut.setText("");
    }

    //Removes info on remove hover
    @FXML
    void btnRemoveInfoOrders(MouseEvent event) {
        lblOut.setText("");
    }

    public void btnInfoProfile(MouseEvent mouseEvent) {
        lblOut.setText("The Profile scene is where you as a Superuser can view, edit and delete users from the register. " +
                "This is also where users are declared as superusers");
    }
}

