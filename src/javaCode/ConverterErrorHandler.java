package javaCode;

import java.util.Date;

public class ConverterErrorHandler {

        public static class IntegerStringConverter extends javafx.util.converter.IntegerStringConverter {
            private boolean conversionSuccessful;

            @Override
            public Integer fromString(String s) {
                try {
                    Integer result = super.fromString(s);
                    conversionSuccessful = true;
                    return result;
                } catch (NumberFormatException e) {
                    Dialogs.showErrorDialog("Number input is unvalid");
                    conversionSuccessful = false;
                    return 0;
                }
            }

            public boolean wasSuccessful() {
                return conversionSuccessful;
            }
        }
}
