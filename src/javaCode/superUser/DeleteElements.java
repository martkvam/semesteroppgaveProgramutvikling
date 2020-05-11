package javaCode.superUser;

import javaCode.Dialogs;
import javaCode.objects.Adjustment;
import javaCode.objects.Component;
import javafx.collections.ObservableList;

import java.io.IOException;


public class DeleteElements {
    public boolean deleteCars(ObservableList selectedElements) throws IOException {
        boolean deleteSelectedItems = false;

        //Makes sure that a table row has been selected
        if (selectedElements.size() > 0) {
            //If one row is selected
            if (selectedElements.size() == 1) {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected car?");
            }
            //If multiple rows are selected
            else {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected cars?");
            }
            //Makes sure that the superuser wants to delete the selected node(s).
            if (!deleteSelectedItems) {
                return false;
            }
        } else {
            throw new IOException("You have to choose minimum one car to delete");
        }
        return true;
    }

    public boolean deleteComponents(ObservableList<Component> selectedElements) throws IOException {
        boolean deleteSelectedItems = false;

        //Makes sure that a table row has been selected
        if (selectedElements.size() > 0) {
            //If one row is selected
            if (selectedElements.size() == 1) {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected component?");
            }
            //If multiple rows are selected
            else {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected components?");
            }
            //Makes sure that the superuser wants to delete the selected node(s).
            if (!deleteSelectedItems) {
                return false;
            }
        } else {
            throw new IOException("You have to choose minimum one component to delete");
        }
        return true;
    }

    public boolean deleteAdjustments(ObservableList<Adjustment> selectedElements) throws IOException {
        boolean deleteSelectedItems = false;
        //Makes sure that a table row has been selected
        if (selectedElements.size() > 0) {
            //If one row is selected
            if (selectedElements.size() == 1) {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected adjustment?");
            }
            //If multiple rows are selected
            else {
                deleteSelectedItems = Dialogs.showChooseDialog("Delete the selected adjustments?");
            }
            //Makes sure that the superuser wants to delete the selected node(s).
            if (!deleteSelectedItems) {
                return false;
            }
        } else {
            throw new IOException("You have to choose minimum one adjustment to delete");
        }
        return true;
    }

}

