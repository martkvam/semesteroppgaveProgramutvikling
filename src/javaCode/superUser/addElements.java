package javaCode.superUser;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

public class addElements {
    private int amountOfCars = 4;

    //Code for a new dialog to add new cars.
    public ArrayList<Object> openAddCarDialog() {

        //Sets up a new dialog
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("New car dialog");
        dialog.setHeaderText("Add new car");

        // Set the button types.
        ButtonType addButton = new ButtonType("Add car", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the input labels and fields in a grid
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
        carPrice.textProperty().addListener((observable, oldValue, newValue) -> {
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
        amountOfCars += 1;
        ArrayList<Object> outArray = new ArrayList<>();
        result.ifPresent(newCarEntry -> {
            outArray.add(newCarEntry.get(1) + amountOfCars);
        });
        return outArray;
    }


    public ArrayList<Object> openAddComponentsDialog(){
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("New Component dialog");
        dialog.setHeaderText("Add new Component");

        // Set the button types.
        ButtonType addButton = new ButtonType("Add component", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the input labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox chooseCar = new ComboBox();
        chooseCar.getItems().addAll("Gasoline", "Diesel", "Hybrid", "Electric");
        chooseCar.setPromptText("Choose car type");

        ComboBox chooseComponentType = new ComboBox();
        chooseComponentType.getItems().addAll("Engine", "osv", "New component type");
        chooseComponentType.setPromptText("Choose component type");


        TextField newComponentType = new TextField();
        newComponentType.setPromptText("Add new component type");
        newComponentType.setVisible(false);

        TextField componentName = new TextField();
        componentName.setPromptText("New component name");

        TextArea componentDescription = new TextArea();
        componentDescription.setPromptText("New component description");

        TextField componentPrice = new TextField();
        componentPrice.setPromptText("New component price");



        grid.add(new Label("Choose car type for your new component"), 0, 0);
        grid.add(chooseCar, 1, 0);
        grid.add(new Label("Choose component type"), 0, 1);
        grid.add(chooseComponentType, 1, 1);

        grid.add(new Label("Component description"), 0, 2);
        grid.add(componentDescription, 1, 2);
        grid.add(new Label("Component price"), 0, 3);
        grid.add(componentPrice, 1, 3);

        boolean newType = false;
        chooseComponentType.valueProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.toString().equals("New component type")){
                grid.getChildren().clear();
                grid.add(new Label("Choose car type for your new component"), 0, 0);
                grid.add(chooseCar, 1, 0);
                grid.add(new Label("Choose component type"), 0, 1);
                grid.add(chooseComponentType, 1, 1);
                grid.add(new Label("Component type"), 0, 2);
                grid.add(newComponentType, 1, 2);
                grid.add(new Label("Component description"), 0, 3);
                grid.add(componentDescription, 1, 3);
                grid.add(new Label("Car Price"), 0, 4);
                grid.add(componentPrice, 1, 4);
                newComponentType.setVisible(true);
            }
            else{
                grid.getChildren().clear();
                grid.add(new Label("Choose car type for your new component"), 0, 0);
                grid.add(chooseCar, 1, 0);
                grid.add(new Label("Choose component type"), 0, 1);
                grid.add(chooseComponentType, 1, 1);

                grid.add(new Label("Component description"), 0, 2);
                grid.add(componentDescription, 1, 2);
                grid.add(new Label("Component price"), 0, 3);
                grid.add(componentPrice, 1, 3);
            }
        });
        // Enable/Disable add button depending on whether the input fields are filled.
        Node addDisableButton = dialog.getDialogPane().lookupButton(addButton);
        addDisableButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).  OBS! Legge til støtte for at alle feltene må være fyllt ut.
        componentPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            addDisableButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> chooseCar.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {

            if (dialogButton == addButton) {
                ArrayList<String> newComponent = new ArrayList<>();
                newComponent.add((String) chooseCar.getValue());
                if(chooseComponentType.getValue().toString().equals("New component type")){
                    newComponent.add(newComponentType.getText());
                }
                else{
                    newComponent.add((String) chooseComponentType.getValue());
                }

                newComponent.add(componentDescription.getText());
                newComponent.add(componentPrice.getText());
                return newComponent;
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();

        /*result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });

         */
        //Handles the input values and sets new car id
        ArrayList<Object> outArray = new ArrayList<>();
        result.ifPresent(newComponentEntry -> {
            for(int i = 0; i < newComponentEntry.size(); i++){
                outArray.add(newComponentEntry.get(i));
            }
        });
        return outArray;
    }

}
