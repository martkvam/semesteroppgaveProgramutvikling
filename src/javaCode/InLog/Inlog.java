package javaCode.InLog;

import javaCode.Dialogs;
import javaCode.FileHandler;
import javaCode.Lists;
import javaCode.OpenScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Inlog implements Initializable {

    @FXML
    public TextField txtVisiblePassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private CheckBox chkBoxPassword;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnNewUser;
    Stage stage = new Stage();
    Lists lists = new Lists();
    FileHandler handler = new FileHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler.readAllFiles(stage);
       /*Car bensin = new Car("1", "Bensin", "Bensinbil", 150000);
        Car diesel = new Car("2", "Diesel", "Dieselbil", 150000);
        Car elektrisk = new Car("3", "Elektrisk", "Elektrisk bil", 150000);
        Car hybrid = new Car("4", "Hybrid", "Hybridbil", 150000);

        lists.addCar(bensin);
        lists.addCar(diesel);
        lists.addCar(elektrisk);
        lists.addCar(hybrid);

        Component motor1 = new Component("1", "1-1","Motor", "Rask jævel", 20000);
        Component wheel1 = new Component("2", "2-1", "Ratt" ,"Billig", 2000);
        Component rim1 = new Component("3", "3-1", "Felg" ,"Dyreste", 2000);
        Component setetrekk = new Component("4", "4-1", "Setetrekk" ,"Skinn", 2000);
        Component motor2 = new Component("3", "1-2", "Motor" ,"Effektiv", 35000);
        Component wheel2 = new Component("1", "2-2", "Ratt", "Sport", 5000);
        Component exhaust = new Component("1", "5-1", "Eksospotte", "Bråkete", 4000);

        Adjustment hitch = new Adjustment("1", "Hengerfeste", "Universell hengerfeste", 2000);
        Adjustment sunroof = new Adjustment("2", "Soltak", "Soltak med UV-filter", 7000);
        Adjustment gps = new Adjustment("3", "Integrert GPS", "Integrert GPS", 6000);
        Adjustment airCondition = new Adjustment("4", "Air Condition", "Air Condition", 8000);

        ObservableList<Component> testList = FXCollections.observableArrayList();
        testList.add(motor1);
        testList.add(rim1);
        ObservableList<Component> testListComp = FXCollections.observableArrayList();
        testListComp.add(wheel2);
        ObservableList<Adjustment> testListAdjustment = FXCollections.observableArrayList();
        testListAdjustment.add(hitch);
        ObservableList<Adjustment> testlistAdjustment2 = FXCollections.observableArrayList();
        testlistAdjustment2.addAll(sunroof, gps);

        Date date1 = new Date(2/2/2019);

        Order order1 = new Order("1", 1, "1",date1, date1, testList, testListAdjustment, 1000, "Blue", true );
        Order order2 = new Order("2", 2, "1", date1, date1, testListComp, testlistAdjustment2, 2000, "Red", false);

        lists.addComponent(motor1);
        lists.addComponent(wheel1);
        lists.addComponent(rim1);
        lists.addComponent(setetrekk);
        lists.addComponent(motor2);
        lists.addComponent(wheel2);
        lists.addComponent(exhaust);

        lists.addAdjustment(hitch);
        lists.addAdjustment(sunroof);
        lists.addAdjustment(gps);
        lists.addAdjustment(airCondition);


        lists.addOrder(order1);
        lists.addOrder(order2);
        lists.addOngoingOrder(order2);

         */
    }

    @FXML
    void btnLogInOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        boolean correct = false;
        boolean superUsr = false;
        String id = "";
        String[] values = new String[7];

        try {
            id = Objects.requireNonNull(ReadUsers.getUserId(txtUserName.getText())).get(0);
            int length = ReadUsers.getInfo(id, "User").length();
            String info = ReadUsers.getInfo(id, "User").substring(1, length - 1);
            values = info.replaceAll("\\s+", "").split(",");
        }catch (Exception e){
            //if(txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty())
            Dialogs.showErrorDialog("User don't exist");
            txtUserName.clear();
            txtPassword.clear();
        }

        if((values[3].equals(txtUserName.getText()) || values[4].equals(txtUserName.getText()))){

            if(!isShowPasswordFieldActive() && values[5].equals(txtPassword.getText())){
                LoggedIn.setId(id);
                correct = true;
                superUsr = Boolean.parseBoolean(values[6]);
            } else if (isShowPasswordFieldActive() && values[5].equals(txtVisiblePassword.getText())){
                LoggedIn.setId(id);
                correct = true;
                superUsr = Boolean.parseBoolean(values[6]);
            }
        }

        if(correct){
            if(superUsr) {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/superUser.fxml"));
                OpenScene.newScene("Superuser",  root, 470, 300, event);
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/user.fxml"));
                OpenScene.newScene("User", root, 700, 700, event);
            }
        }else{
            Dialogs.showErrorDialog("Username and password incorrect");
        }
    }

    @FXML //Open new scene for registering user
    void btnNewUserOnClick(ActionEvent event) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/newUser.fxml"));
        OpenScene.newScene("Register User",  root, 300, 500, event);
    }

    public void enterKeyPressed(KeyEvent kEvent) {
        if(kEvent.getCode()== KeyCode.ENTER) {
            btnLogIn.fire();
        }
    }

    public void checkBoxShowPasswordClicked(ActionEvent actionEvent) {
        boolean checked = chkBoxPassword.isSelected();
        txtVisiblePassword.setEditable(checked);
        txtVisiblePassword.setDisable(!checked);
        txtPassword.setEditable(!checked);
        txtPassword.setDisable(checked);

        if(checked){
            txtVisiblePassword.setText(txtPassword.getText());
            txtVisiblePassword.toFront();
            txtVisiblePassword.setOpacity(1);
            txtPassword.toBack();
            txtPassword.setOpacity(0);
            setShowPasswordFieldActive(true);
        } else{
            txtPassword.setText(txtVisiblePassword.getText());
            txtPassword.toFront();
            txtPassword.setOpacity(1);
            txtVisiblePassword.toBack();
            txtVisiblePassword.setOpacity(0);
            setShowPasswordFieldActive(false);
        }
    }

    private static boolean isShowPasswordFieldActive() {
        return showPasswordFieldActive;
    }

    private static void setShowPasswordFieldActive(boolean showPasswordFieldActive) {
        Inlog.showPasswordFieldActive = showPasswordFieldActive;
    }

    private static boolean showPasswordFieldActive;

}