package javaCode.InLog;

import javaCode.ConverterErrorHandler;

public class LoggedIn {

    private static ConverterErrorHandler.IntegerStringConverter intStrConverter =
            new ConverterErrorHandler.IntegerStringConverter();

    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(String InId) {
        id = intStrConverter.fromString(InId);
    }

}
