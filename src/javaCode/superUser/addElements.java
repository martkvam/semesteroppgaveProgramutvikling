package javaCode.superUser;

import javaCode.Dialogs;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class addElements {

    //Code for a new dialog to add new components.
    public static boolean openAddComponentsDialog(ObservableList<Car> carList, ObservableList<Component> componentList, Object selectedCarType, Object componentType, String description, int price) {
        Dialog<Component> dialog = new Dialog<>();
        dialog.setTitle("New Component dialog");
        dialog.setHeaderText("Add new Component");

        // Set the button types.
        ButtonType addButton = new ButtonType("Add component", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the input labels, fields, comboboxes and gridpane.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox chooseCar = new ComboBox();
        for (Car i : carList) {
            chooseCar.getItems().add(i.getCarType());
        }
        chooseCar.setPromptText("Choose car type");

        ComboBox chooseComponentType = new ComboBox();
        ArrayList<String> convertElementsToString = new ArrayList<>();
        for (Component k : componentList) {
            convertElementsToString.add(k.getComponentType());
        }

        ArrayList distinctElements = (ArrayList) convertElementsToString.stream().distinct().collect(Collectors.toList());

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

        //Sets comboboxes and text field if validation was no good last time
        if (selectedCarType.toString().length() != 0 || componentType.toString().length() != 0 || description.length() != 0) {
            chooseCar.getSelectionModel().select(selectedCarType);
            chooseComponentType.getSelectionModel().select(componentType);
            componentDescription.setText(description);
        }

        //Makes shure that componenttype is selected
        if (!chooseComponentType.getSelectionModel().isEmpty()) {
            //Sets special grid for input of new component type
            if (chooseComponentType.getSelectionModel().getSelectedItem().toString().equals("New component type")) {
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
            } else {
                grid.add(new Label("Choose car type for your new component"), 0, 0);
                grid.add(chooseCar, 1, 0);
                grid.add(new Label("Choose component type"), 0, 1);
                grid.add(chooseComponentType, 1, 1);

                grid.add(new Label("Component description"), 0, 2);
                grid.add(componentDescription, 1, 2);
                grid.add(new Label("Component price"), 0, 3);
                grid.add(componentPrice, 1, 3);
            }
        } else {
            grid.add(new Label("Choose car type for your new component"), 0, 0);
            grid.add(chooseCar, 1, 0);
            grid.add(new Label("Choose component type"), 0, 1);
            grid.add(chooseComponentType, 1, 1);

            grid.add(new Label("Component description"), 0, 2);
            grid.add(componentDescription, 1, 2);
            grid.add(new Label("Component price"), 0, 3);
            grid.add(componentPrice, 1, 3);
        }


        //If chooseComponenttype is choosen, the viewed grid is altered
        chooseComponentType.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.toString().equals("New component type")) {
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
            } else {
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

        //Validates price input with a input listener
        componentPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            addDisableButton.setDisable(true);
            if (newValue.length() != 0) {
                try {
                    int testComponentPrice = Integer.parseInt(newValue);
                    addDisableButton.setDisable(newValue.trim().isEmpty());
                } catch (NumberFormatException e) {
                    Dialogs.showErrorDialog(e.getMessage());
                    componentPrice.clear();
                } catch (IllegalArgumentException ignored) {

                }
            }
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(chooseCar::requestFocus);

        // Validates an converts the input from the textareas and comboboxes to a Component and returns a new component.
        dialog.setResultConverter(dialogButton -> {

            if (dialogButton == addButton) {
                String componentId = "";
                String outComponentType = "";
                int highestComponentID = 1;
                int outComponentPrice;
                String outComponentDescription = "";
                //Loop to set new componentID
                if (!chooseComponentType.getSelectionModel().isEmpty() && !chooseCar.getSelectionModel().isEmpty()) {
                    if (chooseComponentType.getValue().toString().equals("New component type")) {
                        //Loop to find highest ComponentTypeID(first number in componentID)
                        String lastComponentID;
                        int lastComponentIDchecked = 0;
                        for (Component component : componentList) {
                            String line = component.getComponentID();
                            String[] split = line.split("-");

                            if (Integer.parseInt(split[0]) >= lastComponentIDchecked) {
                                lastComponentIDchecked = Integer.parseInt(split[0]);
                            }
                        }

                        lastComponentID = Integer.toString(lastComponentIDchecked + 1);
                        outComponentType = newComponentType.getText();
                        componentId = lastComponentID;
                        componentId += "-";
                        componentId += 1;
                    }
                    for (int i = 0; i < componentList.size(); i++) {
                        if (componentList.get(i).getComponentType().equals(chooseComponentType.getValue().toString())) {
                            int lastComponentIDchecked = 0;
                            String line = componentList.get(i).getComponentID();
                            String[] split = line.split("-");
                            if (Integer.parseInt(split[1]) >= highestComponentID) {
                                highestComponentID = Integer.parseInt(split[1]);
                                String line2 = componentList.get(i).getComponentID();
                                String[] split2 = line2.split("-");
                                componentId = split2[0];
                                componentId += "-";
                                componentId += Integer.toString(highestComponentID + 1);
                            }
                            outComponentType = chooseComponentType.getValue().toString();
                        }
                    }

                    try {
                        outComponentDescription = componentDescription.getText();
                        outComponentPrice = Integer.parseInt(componentPrice.getText());
                        String outCarType = chooseCar.getValue().toString();
                        String carID = "";
                        for (int i = 0; i < carList.size(); i++) {
                            if (outCarType.equals(carList.get(i).getCarType())) {
                                carID = carList.get(i).getCarID();
                            }
                        }
                        return new Component(carID, componentId, outComponentType, outComponentDescription, outComponentPrice);
                    } catch (NumberFormatException e) {
                        Dialogs.showErrorDialog("The price has to be a number");
                        openAddComponentsDialog(carList, componentList, chooseCar.getSelectionModel().getSelectedItem(), chooseComponentType.getSelectionModel().getSelectedItem(), outComponentDescription, 1);
                        return null;
                    } catch (IllegalArgumentException e) {
                        Dialogs.showErrorDialog(e.getMessage());
                        openAddComponentsDialog(carList, componentList, chooseCar.getSelectionModel().getSelectedItem(), chooseComponentType.getSelectionModel().getSelectedItem(), outComponentDescription, 1);
                        return null;
                    }

                } else {
                    Dialogs.showErrorDialog("You have to choose both a car and component type");
                    openAddComponentsDialog(carList, componentList, "", "", outComponentDescription, 1);
                    return null;
                }
            } else {
                return null;
            }
        });

        Optional<Component> result = dialog.showAndWait();

        //Handles the input values and sets new car id
        result.ifPresent(componentList::add);
        return result.isPresent();
    }

    public static void openAddAdjustmentDialog(ObservableList<Adjustment> adjustmentList, Object adjustmentType, String description, int price) {
        Dialog<Adjustment> dialog = new Dialog<>();
        dialog.setTitle("New adjustment dialog");
        dialog.setHeaderText("Add new adjustment");

        // Set the button types.
        ButtonType addButton = new ButtonType("Add adjustment", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the input labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox chooseAdjustmentType = new ComboBox();
        ArrayList<String> convertElementsToString = new ArrayList<>();
        for (Adjustment k : adjustmentList) {
            convertElementsToString.add(k.getAdjustmentType());
        }

        ArrayList distinctElements = (ArrayList) convertElementsToString.stream().distinct().collect(Collectors.toList());

        chooseAdjustmentType.getItems().addAll(distinctElements);


        chooseAdjustmentType.getItems().add("New adjustment type");
        chooseAdjustmentType.setPromptText("Choose adjustment type");


        TextField newAdjustmentType = new TextField();
        newAdjustmentType.setPromptText("Add new adjustment type");
        newAdjustmentType.setVisible(false);

        TextField adjustmentTypeName = new TextField();
        adjustmentTypeName.setPromptText("New adjustment name");

        TextArea adjustmentDescription = new TextArea();
        adjustmentDescription.setPromptText("New adjustment description");

        TextField adjustmentPrice = new TextField();
        adjustmentPrice.setPromptText("New adjustment price");

        if (adjustmentType.toString().length() != 0 || description.length() != 0 || price == 1) {
            chooseAdjustmentType.getSelectionModel().select(adjustmentType);
            adjustmentDescription.setText(description);
        }

        if (!chooseAdjustmentType.getSelectionModel().isEmpty()) {
            if (chooseAdjustmentType.getSelectionModel().getSelectedItem().toString().equals("New adjustment type")) {
                grid.getChildren().clear();
                grid.add(new Label("Choose adjustment type for your new adjustment"), 0, 0);
                grid.add(chooseAdjustmentType, 1, 0);
                grid.add(new Label("New adjustment type"), 0, 1);
                grid.add(newAdjustmentType, 1, 1);
                grid.add(new Label("Component description"), 0, 2);
                grid.add(adjustmentDescription, 1, 2);
                grid.add(new Label("Car Price"), 0, 3);
                grid.add(adjustmentPrice, 1, 3);
                newAdjustmentType.setVisible(true);
            } else {
                grid.add(new Label("Choose adjustment type for your new adjustment"), 0, 0);
                grid.add(chooseAdjustmentType, 1, 0);

                grid.add(new Label("Adjustment description"), 0, 1);
                grid.add(adjustmentDescription, 1, 1);
                grid.add(new Label("Adjustment price"), 0, 2);
                grid.add(adjustmentPrice, 1, 2);
            }

        } else {
            grid.add(new Label("Choose adjustment type for your new adjustment"), 0, 0);
            grid.add(chooseAdjustmentType, 1, 0);

            grid.add(new Label("Adjustment description"), 0, 1);
            grid.add(adjustmentDescription, 1, 1);
            grid.add(new Label("Adjustment price"), 0, 2);
            grid.add(adjustmentPrice, 1, 2);
        }


        boolean newType = false;
        chooseAdjustmentType.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.toString().equals("New adjustment type")) {
                grid.getChildren().clear();
                grid.add(new Label("Choose adjustment type for your new adjustment"), 0, 0);
                grid.add(chooseAdjustmentType, 1, 0);
                grid.add(new Label("New adjustment type"), 0, 1);
                grid.add(newAdjustmentType, 1, 1);
                grid.add(new Label("Component description"), 0, 2);
                grid.add(adjustmentDescription, 1, 2);
                grid.add(new Label("Car Price"), 0, 3);
                grid.add(adjustmentPrice, 1, 3);
                newAdjustmentType.setVisible(true);
            } else {
                grid.getChildren().clear();
                grid.add(new Label("Choose adjustment type for your new adjustment"), 0, 0);
                grid.add(chooseAdjustmentType, 1, 0);

                grid.add(new Label("Adjustment description"), 0, 1);
                grid.add(adjustmentDescription, 1, 1);
                grid.add(new Label("Adjustment price"), 0, 2);
                grid.add(adjustmentPrice, 1, 2);
            }
        });
        // Enable/Disable add button depending on whether the input fields are filled.
        Node addDisableButton = dialog.getDialogPane().lookupButton(addButton);
        addDisableButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).  OBS! Legge til støtte for at alle feltene må være fyllt ut.
        adjustmentPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            addDisableButton.setDisable(true);
            if (newValue.length() != 0) {
                try {
                    int testAdjsutment = Integer.parseInt(newValue);
                    addDisableButton.setDisable(newValue.trim().isEmpty());
                } catch (NumberFormatException e) {
                    Dialogs.showErrorDialog(e.getMessage());
                    adjustmentPrice.clear();
                }
            }
        });

        dialog.getDialogPane().setContent(grid);

        // Convert the input from the textareas and comboboxes to an adjustment and returns it.
        dialog.setResultConverter(dialogButton -> {

            if (dialogButton == addButton) {
                if (!chooseAdjustmentType.getSelectionModel().isEmpty()) {
                    String adjustmentId = "";
                    String outAdjustmentType = "";
                    //Loop to set new componentID
                    for (int i = 0; i < adjustmentList.size(); i++) {

                        //Loop to find highest ComponentTypeID(first number in componentID)
                        String lastAdjustmentId;
                        int lastAdjustmentIdChecked = 0;
                        for (Adjustment adjustment : adjustmentList) {
                            if (lastAdjustmentIdChecked < Integer.parseInt(adjustment.getAdjustmentID())) {
                                lastAdjustmentIdChecked = Integer.parseInt(adjustment.getAdjustmentID());
                            }
                        }
                        lastAdjustmentId = Integer.toString(lastAdjustmentIdChecked + 1);
                        if (chooseAdjustmentType.getSelectionModel().getSelectedItem().equals("New adjustment type")) {
                            outAdjustmentType = newAdjustmentType.getText();
                        } else {
                            outAdjustmentType = chooseAdjustmentType.getSelectionModel().getSelectedItem().toString();
                        }

                        adjustmentId = lastAdjustmentId;
                    }
                    try {
                        String outAdjustmentDescription = adjustmentDescription.getText();
                        int outAdjustmentPrice = Integer.parseInt(adjustmentPrice.getText());

                        return new Adjustment(adjustmentId, outAdjustmentType, outAdjustmentDescription, outAdjustmentPrice);
                    } catch (NumberFormatException e) {
                        Dialogs.showErrorDialog("Adjustment price must be a number");
                        openAddAdjustmentDialog(adjustmentList, adjustmentType, description, price);
                        return null;
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                } else {
                    Dialogs.showErrorDialog("You have to choose a adjustment type");
                    openAddAdjustmentDialog(adjustmentList, adjustmentType, description, price);
                    return null;
                }

            } else {
                return null;
            }
        });

        Optional<Adjustment> result = dialog.showAndWait();

        //Handles the input values and sets new car id
        result.ifPresent(adjustmentList::add);
    }

    //Code for a new dialog to add new cars.
    public void openAddCarDialog(ObservableList<Car> carTypeList, String type, String description, int price) {

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
        if (type.length() != 0 || description.length() != 0 || price != 0) {
            carType.setText(type);
            carDescription.setText(description);
        }

        grid.add(new Label("Car type"), 0, 0);
        grid.add(carType, 1, 0);
        grid.add(new Label("Car description"), 0, 1);
        grid.add(carDescription, 1, 1);
        grid.add(new Label("Car Price"), 0, 2);
        grid.add(carPrice, 1, 2);

        // Enable/Disable add button depending on whether the input fields are filled.
        Node addDisableButton = dialog.getDialogPane().lookupButton(addButton);
        addDisableButton.setDisable(true);

        //The price field has to be filled before the new car can be added
        carPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            addDisableButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        //Validates all input when add button is pushed. Returns new car and adds to list
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                int lastCarIndex = 0;
                for (int i = 0; i < carTypeList.size(); i++) {
                    if (Integer.parseInt(carTypeList.get(i).getCarID()) > lastCarIndex) {
                        lastCarIndex = Integer.parseInt(carTypeList.get(i).getCarID());
                    }
                }
                String newCarType = "";
                String newCarDescription = "";
                int newCarPrice = 0;
                //Must give the car type a name
                if (carType.getText().length() != 0) {
                    newCarType = carType.getText();
                } else {
                    Dialogs.showErrorDialog("You must give the car type a name");
                }

                if (carDescription.getText().length() != 0) {
                    newCarDescription = carDescription.getText();
                } else {
                    Dialogs.showErrorDialog("You must give the car a description");
                }

                try {
                    newCarPrice = Integer.parseInt(carPrice.getText());
                    return new Car(Integer.toString(lastCarIndex + 1), newCarType, newCarDescription, newCarPrice);
                } catch (NumberFormatException e) {
                    Dialogs.showErrorDialog("The price must be a number");
                    openAddCarDialog(carTypeList, newCarType, newCarDescription, newCarPrice);
                } catch (IllegalArgumentException e) {
                    openAddCarDialog(carTypeList, newCarType, newCarDescription, newCarPrice);
                }
            }
            return null;
        });

        //Starts dialog window, and waits for return.

        Optional<Car> result = dialog.showAndWait();

        //Handles the input values and sets new car id

        result.ifPresent(newCarEntry -> {
            carTypeList.add(newCarEntry);
        });
    }
}
