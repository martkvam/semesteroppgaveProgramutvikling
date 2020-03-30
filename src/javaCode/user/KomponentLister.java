package javaCode.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Observable;

public class KomponentLister {

    ObservableList<ArrayList<Component>> componentLists = FXCollections.observableArrayList();
    ArrayList<Component> motorList = new ArrayList<>();
    ArrayList<Component> wheelList = new ArrayList<>();
    ArrayList<Component> rimList = new ArrayList<>();
    ArrayList<Component> setetrekkList = new ArrayList<>();
}
