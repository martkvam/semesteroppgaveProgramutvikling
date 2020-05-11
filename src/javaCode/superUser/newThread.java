package javaCode.superUser;

import javaCode.Dialogs;
import javafx.concurrent.Task;


public class newThread extends Task<Integer> {

    @Override
    protected Integer call() {
        //Uses sleep to simulate loading of bigger data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Dialogs.showErrorDialog("The thread failed because of: " + e.getMessage());
        }

        return 0;
    }

}