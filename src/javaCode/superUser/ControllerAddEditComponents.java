package javaCode.superUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class ControllerAddEditComponents {

    private int amountOfCars = 4;

    @FXML
    private Label lblUt;

    @FXML
    private ChoiceBox<?> chooseFilterCarType;

    @FXML
    private ChoiceBox<?> chooseFilterComponent;

    @FXML
    private ChoiceBox<?> chooseFilterAdjustment;

    @FXML
    void btnAddFromFile(ActionEvent event) {

    }

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnEditAdjustments(ActionEvent event) {

    }

    @FXML
    void btnEditCars(ActionEvent event) {

    }

    @FXML
    void btnEditComponents(ActionEvent event) {

    }

    @FXML
    void btnNewAdjustment(ActionEvent event) {

    }

    @FXML
    void btnNewCar(ActionEvent event) {
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("New car dialog");
        dialog.setHeaderText("Add new car");

        // Set the button types.
        ButtonType addButton = new ButtonType("Add car", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the input labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField carType = new TextField();
        carType.setPromptText("New car type");
        TextArea carDescription = new TextArea();
        carDescription.setPromptText("New car description");
        TextField carPrice = new TextField();
        carPrice.setPromptText("New car price");

        grid.add(new Label("Car type"), 0, 0);
        grid.add(carType, 1, 0);
        grid.add(new Label("Car description"), 0, 1);
        grid.add(carDescription, 1, 1);
        grid.add(new Label("Car Price"), 0, 2);
        grid.add(carPrice, 1, 2);

    // Enable/Disable add button depending on whether the input fields are filled.
        Node addDisableButton = dialog.getDialogPane().lookupButton(addButton);
        addDisableButton.setDisable(true);

    // Do some validation (using the Java 8 lambda syntax).  OBS! Legge til støtte for at alle feltene må være fyllt ut.
        carType.textProperty().addListener((observable, oldValue, newValue) -> {
            addDisableButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

    // Request focus on the username field by default.
        Platform.runLater(() -> carType.requestFocus());

    // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                ArrayList<String> newCar = new ArrayList<>();
                newCar.add(carType.getText());
                newCar.add(carDescription.getText());
                newCar.add(carPrice.getText());
                return newCar;
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();

        /*result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });

         */
    //Handles the input values and sets new car id
        amountOfCars+=1;
        result.ifPresent(newCarEntry -> {
            lblUt.setText(newCarEntry.get(1) + amountOfCars);
        });

    }

    @FXML
    void btnNewComponent(ActionEvent event) {

    }

}





