package javaCode.superUser;

import javaCode.Car;
import javaCode.Component;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.stream.Collectors;

public class addElements {

    //Code for a new dialog to add new cars.
    public void openAddCarDialog(ObservableList<Car> carTypeList) {

        //Sets up a new dialog
        Dialog<Car> dialog = new Dialog<>();
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
                int lastCarIndex = 0;
                for(int i = 0; i < carTypeList.size(); i++){
                    if(Integer.parseInt(carTypeList.get(i).getCarID()) > lastCarIndex){
                        lastCarIndex = Integer.parseInt(carTypeList.get(i).getCarID());
                    }
                }
                String newCarType = carType.getText();
                String newCarDescription = carDescription.getText();
                int newCarPrice = Integer.parseInt(carPrice.getText());
                Car newCar = new Car(Integer.toString(lastCarIndex+1),newCarType, newCarDescription, newCarPrice);
                return newCar;
            }
            return null;
        });

        Optional<Car> result = dialog.showAndWait();

        //Handles the input values and sets new car id

        result.ifPresent(newCarEntry -> {
            carTypeList.add(newCarEntry);
        });
    }


    public void openAddComponentsDialog(ObservableList<Car> carType, ObservableList<Component> componentType){
        Dialog<Component> dialog = new Dialog<>();
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
        for(Car i : carType){
            chooseCar.getItems().add(i.getCarType());
        }
        chooseCar.setPromptText("Choose car type");

        ComboBox chooseComponentType = new ComboBox();
        ArrayList<String> convertElementsToString = new ArrayList<>();
        for(Component k : componentType){
            convertElementsToString.add(k.getComponentType());
        }

        ArrayList distinctElements = (ArrayList)convertElementsToString.stream().distinct().collect(Collectors.toList());

        chooseComponentType.getItems().addAll(distinctElements);


        chooseComponentType.getItems().add("New component type");
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

        // Convert the input from the textareas and comboboxes to a Component and returns it.
        dialog.setResultConverter(dialogButton -> {

            if (dialogButton == addButton) {
                String componentId = "";
                String outComponentType = "";
                int highestComponentID = 1;
                //Loop to set new componentID
                for (int i = 0; i < componentType.size(); i++) {
                    if (chooseComponentType.getValue().toString().equals("New component type")) {
                        //Loop to find highest ComponentTypeID(first number in componentID)
                        String lastComponentID = "";
                        int lastComponentIDchecked = 0;
                        for (int j = 0; j < componentType.size(); j++) {
                            String line = componentType.get(j).getComponentID();
                            String[] split = line.split("-");

                            if(Integer.parseInt(split[0]) >= lastComponentIDchecked){
                                lastComponentIDchecked = Integer.parseInt(split[0])+1;
                            }
                        }
                        lastComponentID=Integer.toString(lastComponentIDchecked+1);
                        outComponentType = newComponentType.getText();
                        componentId=lastComponentID;
                        componentId += "-";
                        componentId += 1;
                        break;

                    }
                    else if (componentType.get(i).getComponentType().equals(chooseComponentType.getValue().toString())) {
                        int lastComponentIDchecked=0;
                        String line = componentType.get(i).getComponentID();
                        String[] split = line.split("-");
                        if(Integer.parseInt(split[1]) >= highestComponentID) {
                            highestComponentID = Integer.parseInt(split[1]);
                            String line2 = componentType.get(i).getComponentID();
                            String[] split2 = line2.split("-");
                            componentId = split2[0];
                            componentId += "-";
                            componentId += Integer.toString(highestComponentID+1);
                        }
                            outComponentType = chooseComponentType.getValue().toString();
                        }
                    }

                String outComponentDescription = componentDescription.getText();
                int  outComponentPrice = Integer.parseInt(componentPrice.getText());
                String outCarType = chooseCar.getValue().toString();
                String carID ="";
                for(int i = 0; i < carType.size(); i++){
                    if(outCarType.equals(carType.get(i).getCarType())){
                        carID=carType.get(i).getCarID();
                    }
                }
                Component newComponent = new Component(carID, componentId, outComponentType, outComponentDescription, outComponentPrice);
                return newComponent;
            }
            else{
                return null;
            }
    });

        Optional<Component> result = dialog.showAndWait();

        //Handles the input values and sets new car id
        result.ifPresent(newComponentEntry -> {
            componentType.add(newComponentEntry);
        });
    }

}
