package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class KomponentLister {

    ObservableList<Component> list = FXCollections.observableArrayList();

    public void attachTableView (TableView<Component> tv){
        tv.setItems(list);
    }

    public void addComponent(Component component){
        list.add(component);
    }

}
