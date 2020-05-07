package javaCode;

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
        public static class BooleanStringConverter extends javafx.util.converter.BooleanStringConverter {
            private boolean conversionSuccessful;

            @Override
            public Boolean fromString(String s) {

                try {
                    Boolean result = super.fromString(s);
                    conversionSuccessful = true;
                    return result;
                } catch (NumberFormatException e) {
                    Dialogs.showErrorDialog("Number input is unvalid");
                    conversionSuccessful = false;
                    return null;
                }
            }

            public boolean wasSuccessful() {
                return conversionSuccessful;
            }
        }
}
