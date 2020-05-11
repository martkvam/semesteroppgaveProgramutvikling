package javaCode.superUser;

import javaCode.Dialogs;
import javaCode.Lists;
import javaCode.objects.Adjustment;
import javaCode.objects.Car;
import javaCode.objects.Component;
import javaCode.objects.Order;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class EditOrders {
    public static int editOrderPrice(int originalPrice) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("New Price");
        dialog.setHeaderText("Give discount on total price");

        // Set the button types.
        ButtonType addButton = new ButtonType("Confirm new price", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);


        Button prosentButton = new Button("%");
        Button newPriceButton = new Button("Full price");
        Button discountButton = new Button("Discount");

        prosentButton.setDisable(true);
        newPriceButton.setDisable(true);
        discountButton.setDisable(true);

        // Create the input labels and fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Label txtOldPrice = new Label();
        txtOldPrice.setText("Original price " + originalPrice + " kr");

        TextField txtPrice = new TextField();
        txtPrice.setPromptText("New total price");


        Label newPriceLabel = new Label();

        grid.add(txtOldPrice, 0, 0);
        grid.add(txtPrice, 0, 1);
        grid.add(prosentButton, 1, 1);
        grid.add(newPriceButton, 2, 1);
        grid.add(discountButton, 3, 1);
        grid.add(newPriceLabel, 0, 2);

        // Enable/Disable add button depending on whether the input fields are filled.
        Node addDisableButton = dialog.getDialogPane().lookupButton(addButton);
        addDisableButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).  OBS! Legge til støtte for at alle feltene må være fyllt ut.
        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            newPriceLabel.setText("");
            addDisableButton.setDisable(true);
            if (newValue.length() == 0) {
                prosentButton.setDisable(true);
                newPriceButton.setDisable(true);
                discountButton.setDisable(true);
            } else {
                try {
                    Integer.parseInt(newValue);
                    prosentButton.setDisable(false);
                    newPriceButton.setDisable(false);
                    discountButton.setDisable(false);
                } catch (NumberFormatException e) {
                    txtPrice.clear();
                    newPriceLabel.setText("The input must be a number");
                    prosentButton.setDisable(true);
                    newPriceButton.setDisable(true);
                    discountButton.setDisable(true);
                }
            }

        });

        dialog.getDialogPane().setContent(grid);

        prosentButton.setOnAction(event -> {
            newPriceLabel.setText(Integer.toString(originalPrice - (originalPrice * (Integer.parseInt(txtPrice.getText())) / 100)));
            addDisableButton.setDisable(false);
        });

        newPriceButton.setOnAction(event -> {
            newPriceLabel.setText(txtPrice.getText());
            addDisableButton.setDisable(false);
        });

        discountButton.setOnAction(event -> {
            newPriceLabel.setText(Integer.toString(originalPrice - Integer.parseInt(txtPrice.getText())));
            addDisableButton.setDisable(false);
        });
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return Integer.parseInt(newPriceLabel.getText());
            }
            return originalPrice;
        });

        Optional<Integer> result = dialog.showAndWait();

        return result.get();

    }

    public static boolean deleteOrder(ObservableList selectedOrders) throws IOException {
        boolean deleteSelectedItems = false;

        //Makes sure that a table row has been selected
        if (selectedOrders.size() > 0) {
            //If one row is selected
            if (selectedOrders.size() == 1) {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected order?");
            }
            //If multiple rows are selected
            else {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected orders?");
            }
            //Makes sure that the superuser wants to delete the selected node(s).
            return deleteSelectedItems;
        } else {
            throw new IOException("You have to choose minimum one car to delete");
        }
    }

    public static int recalculateTotalPrice(Order order) {
        int totPrice = 0;
        for (Component i : order.getComponentList()) {
            totPrice += i.getComponentPrice();
        }
        for (Adjustment i : order.getAdjustmentList()) {
            totPrice += i.getAdjustmentPrice();
        }
        for (Car i : Lists.getCars()) {
            if (i.getCarID().equals(order.getCarId())) {
                totPrice += i.getPrice();
            }
        }
        return totPrice;
    }
}
